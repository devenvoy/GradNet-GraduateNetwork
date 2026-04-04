import { api } from './client';
import type { ApiResponse, ProfileResponse, ProfileCreateUpdateRequest } from '@/types';

export const profileApi = {
  getMy: () =>
    api.get<ApiResponse<ProfileResponse>>('/api/v1/profile').then(r => r.data),

  createOrUpdate: (data: ProfileCreateUpdateRequest) =>
    api.post<ApiResponse<ProfileResponse>>('/api/v1/profile', data).then(r => r.data),

  getByUserId: (userId: string) =>
    api.get<ApiResponse<ProfileResponse>>(`/api/v1/profile/${userId}`).then(r => r.data),

  search: (query: string) =>
    api.get<ApiResponse<ProfileResponse[]>>('/api/v1/profile/search', { params: { q: query } }).then(r => r.data),
};
