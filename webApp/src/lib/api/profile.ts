import { api } from './client';
import type { ApiResponse, PagedResponse, ProfileResponse, ProfileCreateUpdateRequest } from '@/types';

export const profileApi = {
  /** GET /api/v1/profile — get my profile */
  getMy: () =>
    api.get<ApiResponse<ProfileResponse>>('/api/v1/profile').then(r => r.data),

  /** POST /api/v1/profile — create or update my profile */
  createOrUpdate: (data: ProfileCreateUpdateRequest) =>
    api.post<ApiResponse<ProfileResponse>>('/api/v1/profile', data).then(r => r.data),

  /** GET /api/v1/profile/{userId} — get profile by user ID */
  getByUserId: (userId: string) =>
    api.get<ApiResponse<ProfileResponse>>(`/api/v1/profile/${userId}`).then(r => r.data),

  /** GET /api/v1/profile/search?q=&page=&perPage= */
  search: (params: { q: string; page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<ProfileResponse>>>('/api/v1/profile/search', { params }).then(r => r.data),
};
