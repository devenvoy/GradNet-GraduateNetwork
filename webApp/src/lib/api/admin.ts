import { api } from './client';
import type { ApiResponse, DataEntryRequest, DataUpdateRequest, VerifyDataResponse, FeedbackResponse } from '@/types';

export const adminApi = {
  addData: (data: DataEntryRequest) =>
    api.post<ApiResponse<VerifyDataResponse>>('/api/v1/admin/add-data', data).then(r => r.data),

  updateData: (verifyId: string, data: DataUpdateRequest) =>
    api.put<ApiResponse<VerifyDataResponse>>(`/api/v1/admin/update-data/${verifyId}`, data).then(r => r.data),

  deleteData: (verifyId: string) =>
    api.delete(`/api/v1/admin/delete-data/${verifyId}`),

  blockUser: (userId: string) =>
    api.post(`/api/v1/admin/block/${userId}`),

  deleteUser: (userId: string) =>
    api.post(`/api/v1/admin/delete-user/${userId}`),
};

export const feedbackApi = {
  create: (data: { content: string; category?: string }) =>
    api.post<ApiResponse<FeedbackResponse>>('/api/v1/feedback', data).then(r => r.data),

  update: (feedbackId: string, data: { content?: string; status?: string }) =>
    api.put<ApiResponse<FeedbackResponse>>(`/api/v1/feedback/${feedbackId}`, data).then(r => r.data),

  delete: (feedbackId: string) =>
    api.delete(`/api/v1/feedback/${feedbackId}`),
};

export const verificationApi = {
  verify: (data: Record<string, unknown>) =>
    api.post('/api/v1/verify-user', data),
};
