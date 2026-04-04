import { api } from './client';
import type { ApiResponse, AuthResponse } from '@/types';

export const authApi = {
  signup: (data: {
    email: string; password: string; firstName: string; lastName: string;
    accountType: string; accountName?: string;
  }) => api.post<ApiResponse<AuthResponse>>('/api/v1/auth/signup', data).then(r => r.data),

  login: (data: { email: string; password: string }) =>
    api.post<ApiResponse<AuthResponse>>('/api/v1/auth/login', data).then(r => r.data),

  logout: (refreshToken: string) =>
    api.post('/api/v1/auth/logout', { refreshToken }),

  changePassword: (data: { currentPassword: string; newPassword: string }) =>
    api.put('/api/v1/auth/change-password', data),

  forgotPassword: (email: string) =>
    api.post('/api/v1/auth/forgot-password', { email }),

  resetPassword: (token: string, password: string) =>
    api.post(`/api/v1/auth/reset-password/${token}`, { password }),

  verifyOtp: (email: string, otp: string) =>
    api.post('/api/v1/verify-otp', { email, otp }),

  verifyUser: (data: Record<string, unknown>) =>
    api.post('/api/v1/verify-user', data),

  setPassword: (data: { password: string }) =>
    api.post('/api/v1/auth/set-password', data),
};
