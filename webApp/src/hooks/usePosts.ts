'use client';
import { useInfiniteQuery, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { postsApi } from '@/lib/api/posts';
import { toast } from 'sonner';
import type { PostCreateRequest, PostResponse, ApiResponse, PagedResponse } from '@/types';

export const POSTS_QUERY_KEY = ['posts'];

type PostsPage = ApiResponse<PagedResponse<PostResponse>>;

export function useFeed(roleFilter: string[] = []) {
  return useInfiniteQuery<PostsPage>({
    queryKey: [...POSTS_QUERY_KEY, 'feed', roleFilter],
    queryFn: ({ pageParam }) =>
      postsApi.getAll({ page: pageParam as number, perPage: 10, role: roleFilter }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages
        ? last.data.page + 1
        : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}

export function useLikePost() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (postId: string) => postsApi.like({ postId }),
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
                  ? { ...post, liked: !post.liked, likeCount: post.likeCount + (post.liked ? -1 : 1) }
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
    mutationFn: (data: PostCreateRequest) => postsApi.create(data),
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

export function useMyPosts() {
  return useQuery({
    queryKey: [...POSTS_QUERY_KEY, 'my'],
    queryFn: postsApi.getMy,
    staleTime: 30_000,
  });
}

export function useUserPosts(userId: string) {
  return useQuery({
    queryKey: [...POSTS_QUERY_KEY, 'user', userId],
    queryFn: () => postsApi.getByUser(userId),
    enabled: !!userId,
  });
}

export function usePost(postId: string) {
  return useQuery({
    queryKey: [...POSTS_QUERY_KEY, postId],
    queryFn: () => postsApi.getById(postId),
    enabled: !!postId,
  });
}

export function useSharePost(postId: string) {
  return useQuery({
    queryKey: [...POSTS_QUERY_KEY, 'web', postId],
    queryFn: () => postsApi.getPublic(postId),
    enabled: !!postId,
  });
}

export function useLikedPosts() {
  return useInfiniteQuery<PostsPage>({
    queryKey: [...POSTS_QUERY_KEY, 'liked'],
    queryFn: ({ pageParam }) =>
      postsApi.getLiked({ page: pageParam as number, perPage: 10 }),
    getNextPageParam: (last) =>
      last.data && last.data.page < last.data.totalPages
        ? last.data.page + 1
        : undefined,
    initialPageParam: 1,
    staleTime: 30_000,
  });
}
