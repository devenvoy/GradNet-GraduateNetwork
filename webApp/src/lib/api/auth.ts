import { api } from './client';
import type {
  ApiResponse,
  AuthResponse,
  SignupRequest,
  LoginRequest,
  ChangePasswordRequest,
  SetPasswordRequest,
  ResetPasswordRequest,
  RefreshTokenRequest,
  ForgotPasswordRequest,
  VerifyUserRequest,
  VerifyOtpRequest,
} from '@/types';

export const authApi = {
  /** POST /api/v1/auth/signup */
  signup: (data: SignupRequest) =>
    api.post<ApiResponse<AuthResponse>>('/api/v1/auth/signup', data).then(r => r.data),

  /** POST /api/v1/auth/login */
  login: (data: LoginRequest) =>
    api.post<ApiResponse<AuthResponse>>('/api/v1/auth/login', data).then(r => r.data),

  /** POST /api/v1/auth/logout */
  logout: (data: RefreshTokenRequest) =>
    api.post<ApiResponse<void>>('/api/v1/auth/logout', data).then(r => r.data),

  /** POST /api/v1/auth/refresh-token */
  refreshToken: (data: RefreshTokenRequest) =>
    api.post<ApiResponse<AuthResponse>>('/api/v1/auth/refresh-token', data).then(r => r.data),

  /** PUT /api/v1/auth/change-password */
  changePassword: (data: ChangePasswordRequest) =>
    api.put<ApiResponse<void>>('/api/v1/auth/change-password', data).then(r => r.data),

  /** POST /api/v1/auth/set-password */
  setPassword: (data: SetPasswordRequest) =>
    api.post<ApiResponse<void>>('/api/v1/auth/set-password', data).then(r => r.data),

  /** POST /api/v1/auth/forgot-password */
  forgotPassword: (data: ForgotPasswordRequest) =>
    api.post<ApiResponse<void>>('/api/v1/auth/forgot-password', data).then(r => r.data),

  /** POST /api/v1/auth/reset-password/{token} */
  resetPassword: (token: string, data: ResetPasswordRequest) =>
    api.post<ApiResponse<void>>(`/api/v1/auth/reset-password/${token}`, data).then(r => r.data),

  /** POST /api/v1/verify-user */
  verifyUser: (data: VerifyUserRequest) =>
    api.post<ApiResponse<unknown>>('/api/v1/verify-user', data).then(r => r.data),

  /** POST /api/v1/verify-otp */
  verifyOtp: (data: VerifyOtpRequest) =>
    api.post<ApiResponse<unknown>>('/api/v1/verify-otp', data).then(r => r.data),

  /** DELETE /api/v1/delete_user */
  deleteUser: () =>
    api.delete<ApiResponse<void>>('/api/v1/delete_user').then(r => r.data),
};
