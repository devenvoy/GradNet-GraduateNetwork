import { api } from './client';
import type { ApiResponse, PagedResponse, PostResponse, PostCreateRequest } from '@/types';

export const postsApi = {
  create: (data: PostCreateRequest) =>
    api.post<ApiResponse<PostResponse>>('/api/v1/posts/create', data).then(r => r.data),

  getAll: (params: { page?: number; perPage?: number; role?: string[] }) =>
    api.get<ApiResponse<PagedResponse<PostResponse>>>('/api/v1/posts', { params }).then(r => r.data),

  getMy: () =>
    api.get<ApiResponse<PostResponse[]>>('/api/v1/posts/my').then(r => r.data),

  getByUser: (userId: string) =>
    api.get<ApiResponse<PostResponse[]>>('/api/v1/posts/user', { params: { user_id: userId } }).then(r => r.data),

  getById: (postId: string) =>
    api.get<ApiResponse<PostResponse>>(`/api/v1/post/${postId}`).then(r => r.data),

  getPublic: (postId: string) =>
    api.get<ApiResponse<PostResponse>>(`/api/v1/post/web/${postId}`).then(r => r.data),

  delete: (postId: string) =>
    api.delete(`/api/v1/posts/${postId}`),

  like: (postId: string) =>
    api.post('/api/v1/posts/like', { postId }).then(r => r.data),

  getLiked: (params: { page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<PostResponse>>>('/api/v1/posts/liked_post', { params }).then(r => r.data),
};
