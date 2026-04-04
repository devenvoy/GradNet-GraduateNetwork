'use client';
import { useInfiniteQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { jobsApi } from '@/lib/api/jobs';
import { toast } from 'sonner';

export const JOBS_QUERY_KEY = ['jobs'];

export function useJobs(filters: { work_mode?: string[]; search?: string } = {}) {
  return useInfiniteQuery({
    queryKey: [...JOBS_QUERY_KEY, filters],
    queryFn: ({ pageParam = 1 }) =>
      jobsApi.getAll({ page: pageParam, perPage: 12, ...filters }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages ? last.data.page + 1 : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}

export function useCreateJob() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: jobsApi.create,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: JOBS_QUERY_KEY });
      toast.success('Job posted!');
    },
    onError: () => toast.error('Failed to post job.'),
  });
}

export function useSaveJob() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: jobsApi.save,
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
