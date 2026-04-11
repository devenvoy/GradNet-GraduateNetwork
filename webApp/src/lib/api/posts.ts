import { api } from './client';
import type { ApiResponse, PagedResponse, PostResponse, PostCreateRequest, LikePostRequest } from '@/types';

export const postsApi = {
  /** POST /api/v1/posts/create */
  create: (data: PostCreateRequest) =>
    api.post<ApiResponse<PostResponse>>('/api/v1/posts/create', data).then(r => r.data),

  /** GET /api/v1/posts?page=&perPage=&role[]= */
  getAll: (params: { page?: number; perPage?: number; role?: string[] }) =>
    api.get<ApiResponse<PagedResponse<PostResponse>>>('/api/v1/posts', { params }).then(r => r.data),

  /** GET /api/v1/posts/my */
  getMy: () =>
    api.get<ApiResponse<PostResponse[]>>('/api/v1/posts/my').then(r => r.data),

  /** GET /api/v1/posts/user?user_id= */
  getByUser: (userId: string) =>
    api.get<ApiResponse<PostResponse[]>>('/api/v1/posts/user', { params: { user_id: userId } }).then(r => r.data),

  /** GET /api/v1/post/{postId} */
  getById: (postId: string) =>
    api.get<ApiResponse<PostResponse>>(`/api/v1/post/${postId}`).then(r => r.data),

  /** GET /api/v1/post/web/{postId} — public share link */
  getPublic: (postId: string) =>
    api.get<ApiResponse<PostResponse>>(`/api/v1/post/web/${postId}`).then(r => r.data),

  /** DELETE /api/v1/posts/{postId} */
  delete: (postId: string) =>
    api.delete<ApiResponse<void>>(`/api/v1/posts/${postId}`).then(r => r.data),

  /** POST /api/v1/posts/like */
  like: (data: LikePostRequest) =>
    api.post<ApiResponse<unknown>>('/api/v1/posts/like', data).then(r => r.data),

  /** GET /api/v1/posts/liked_post?page=&perPage= */
  getLiked: (params: { page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<PostResponse>>>('/api/v1/posts/liked_post', { params }).then(r => r.data),
};
