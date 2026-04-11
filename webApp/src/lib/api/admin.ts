import { api } from './client';
import type {
  ApiResponse,
  DataEntryRequest,
  DataUpdateRequest,
  FeedbackRequest,
  FeedbackResponse,
} from '@/types';

export const adminApi = {
  /** POST /api/v1/admin/add-data */
  addData: (data: DataEntryRequest) =>
    api.post<ApiResponse<unknown>>('/api/v1/admin/add-data', data).then(r => r.data),

  /** PUT /api/v1/admin/update-data/{verifyId} */
  updateData: (verifyId: string, data: DataUpdateRequest) =>
    api.put<ApiResponse<unknown>>(`/api/v1/admin/update-data/${verifyId}`, data).then(r => r.data),

  /** DELETE /api/v1/admin/delete-data/{verifyId} */
  deleteData: (verifyId: string) =>
    api.delete<ApiResponse<void>>(`/api/v1/admin/delete-data/${verifyId}`).then(r => r.data),

  /** POST /api/v1/admin/block/{userId} */
  blockUser: (userId: string) =>
    api.post<ApiResponse<void>>(`/api/v1/admin/block/${userId}`).then(r => r.data),

  /** POST /api/v1/admin/delete-user/{userId} */
  deleteUser: (userId: string) =>
    api.post<ApiResponse<void>>(`/api/v1/admin/delete-user/${userId}`).then(r => r.data),
};

export const feedbackApi = {
  /** POST /api/v1/feedback */
  create: (data: FeedbackRequest) =>
    api.post<ApiResponse<FeedbackResponse>>('/api/v1/feedback', data).then(r => r.data),

  /** PUT /api/v1/feedback/{feedbackId} */
  update: (feedbackId: string, data: FeedbackRequest) =>
    api.put<ApiResponse<FeedbackResponse>>(`/api/v1/feedback/${feedbackId}`, data).then(r => r.data),

  /** DELETE /api/v1/feedback/{feedbackId} */
  delete: (feedbackId: string) =>
    api.delete<ApiResponse<void>>(`/api/v1/feedback/${feedbackId}`).then(r => r.data),
};

export const verificationApi = {
  /** POST /api/v1/verify-user */
  verify: (data: { verifyId: string }) =>
    api.post<ApiResponse<unknown>>('/api/v1/verify-user', data).then(r => r.data),
};
