'use client';
import { useInfiniteQuery, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { jobsApi } from '@/lib/api/jobs';
import { toast } from 'sonner';
import type { JobCreateRequest, JobResponse, ApiResponse, PagedResponse } from '@/types';

export const JOBS_QUERY_KEY = ['jobs'];

type JobsPage = ApiResponse<PagedResponse<JobResponse>>;

export function useJobs(filters: { work_mode?: string[]; search?: string } = {}) {
  return useInfiniteQuery<JobsPage>({
    queryKey: [...JOBS_QUERY_KEY, filters],
    queryFn: ({ pageParam }) =>
      jobsApi.getAll({ page: pageParam as number, perPage: 12, ...filters }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages
        ? last.data.page + 1
        : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}

export function useJob(jobId: string) {
  return useQuery({
    queryKey: [...JOBS_QUERY_KEY, jobId],
    queryFn: () => jobsApi.getById(jobId),
    enabled: !!jobId,
  });
}

export function useCreateJob() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: JobCreateRequest) => jobsApi.create(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: JOBS_QUERY_KEY });
      toast.success('Job posted!');
    },
    onError: () => toast.error('Failed to post job.'),
  });
}

export function useUpdateJob() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ jobId, data }: { jobId: string; data: JobCreateRequest }) =>
      jobsApi.update(jobId, data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: JOBS_QUERY_KEY });
      toast.success('Job updated!');
    },
    onError: () => toast.error('Failed to update job.'),
  });
}

export function useSaveJob() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (jobId: string) => jobsApi.save({ jobId }),
    onSettled: () => qc.invalidateQueries({ queryKey: JOBS_QUERY_KEY }),
  });
}

export function useDeleteJob() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: jobsApi.delete,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: JOBS_QUERY_KEY });
      toast.success('Job deleted.');
    },
    onError: () => toast.error('Failed to delete job.'),
  });
}

export function useSavedJobs() {
  return useInfiniteQuery<JobsPage>({
    queryKey: [...JOBS_QUERY_KEY, 'saved'],
    queryFn: ({ pageParam }) =>
      jobsApi.getSaved({ page: pageParam as number, perPage: 12 }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages
        ? last.data.page + 1
        : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}
