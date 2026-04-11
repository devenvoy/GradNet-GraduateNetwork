import { api } from './client';
import type { ApiResponse, PagedResponse, LostFoundResponse, LostFoundCreateRequest } from '@/types';

export const lostFoundApi = {
  /** POST /api/v1/lostfound/create */
  create: (data: LostFoundCreateRequest) =>
    api.post<ApiResponse<LostFoundResponse>>('/api/v1/lostfound/create', data).then(r => r.data),

  /** GET /api/v1/lostfound?page=&perPage= */
  getAll: (params: { page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<LostFoundResponse>>>('/api/v1/lostfound', { params }).then(r => r.data),

  /** DELETE /api/v1/lostfound/{entryId} */
  delete: (entryId: string) =>
    api.delete<ApiResponse<void>>(`/api/v1/lostfound/${entryId}`).then(r => r.data),
};
