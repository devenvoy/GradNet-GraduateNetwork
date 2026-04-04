'use client';
import { useInfiniteQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { postsApi } from '@/lib/api/posts';
import { toast } from 'sonner';

export const POSTS_QUERY_KEY = ['posts'];

export function useFeed(roleFilter: string[] = []) {
  return useInfiniteQuery({
    queryKey: [...POSTS_QUERY_KEY, 'feed', roleFilter],
    queryFn: ({ pageParam = 1 }) =>
      postsApi.getAll({ page: pageParam, perPage: 10, role: roleFilter }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages ? last.data.page + 1 : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}

export function useLikePost() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: postsApi.like,
    onMutate: async (postId) => {
      await qc.cancelQueries({ queryKey: POSTS_QUERY_KEY });
      const previousData = qc.getQueriesData({ queryKey: POSTS_QUERY_KEY });
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      qc.setQueriesData({ queryKey: POSTS_QUERY_KEY }, (old: any) => {
        if (!old?.pages) return old;
        return {
          ...old,
          // eslint-disable-next-line @typescript-eslint/no-explicit-any
          pages: old.pages.map((page: any) => ({
            ...page,
            data: {
              ...page.data,
              // eslint-disable-next-line @typescript-eslint/no-explicit-any
              items: page.data?.items?.map((post: any) =>
                post.id === postId
                  ? { ...post, isLiked: !post.isLiked, likeCount: post.likeCount + (post.isLiked ? -1 : 1) }
                  : post
              ),
            },
          })),
        };
      });
      return { previousData };
    },
    onError: (_, __, ctx) => {
      if (ctx?.previousData) {
        ctx.previousData.forEach(([key, data]) => qc.setQueryData(key, data));
      }
    },
    onSettled: () => qc.invalidateQueries({ queryKey: POSTS_QUERY_KEY }),
  });
}

export function useCreatePost() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: postsApi.create,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: POSTS_QUERY_KEY });
      toast.success('Post published!');
    },
    onError: () => toast.error('Failed to create post.'),
  });
}

export function useDeletePost() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: postsApi.delete,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: POSTS_QUERY_KEY });
      toast.success('Post deleted.');
    },
    onError: () => toast.error('Failed to delete post.'),
  });
}
