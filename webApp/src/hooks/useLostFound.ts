'use client';
import { useInfiniteQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { lostFoundApi } from '@/lib/api/lostFound';
import { toast } from 'sonner';
import type { LostFoundCreateRequest, LostFoundResponse, ApiResponse, PagedResponse } from '@/types';

export const LOSTFOUND_QUERY_KEY = ['lostfound'];

type LostFoundPage = ApiResponse<PagedResponse<LostFoundResponse>>;

export function useLostFound() {
  return useInfiniteQuery<LostFoundPage>({
    queryKey: LOSTFOUND_QUERY_KEY,
    queryFn: ({ pageParam }) => lostFoundApi.getAll({ page: pageParam as number, perPage: 12 }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages
        ? last.data.page + 1
        : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}

export function useCreateLostFound() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: LostFoundCreateRequest) => lostFoundApi.create(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: LOSTFOUND_QUERY_KEY });
      toast.success('Item posted!');
    },
    onError: () => toast.error('Failed to post item.'),
  });
}

export function useDeleteLostFound() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: lostFoundApi.delete,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: LOSTFOUND_QUERY_KEY });
      toast.success('Item removed.');
    },
    onError: () => toast.error('Failed to remove item.'),
  });
}
