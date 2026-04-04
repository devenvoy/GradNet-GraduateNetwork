import { api } from './client';
import type { ApiResponse, PagedResponse, LostFoundResponse, LostFoundCreateRequest } from '@/types';

export const lostFoundApi = {
  create: (data: LostFoundCreateRequest) =>
    api.post<ApiResponse<LostFoundResponse>>('/api/v1/lostfound/create', data).then(r => r.data),

  getAll: (params: { page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<LostFoundResponse>>>('/api/v1/lostfound', { params }).then(r => r.data),

  delete: (entryId: string) =>
    api.delete(`/api/v1/lostfound/${entryId}`),
};
