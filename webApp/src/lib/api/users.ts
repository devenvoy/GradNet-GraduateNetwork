import { api } from './client';
import type { ApiResponse, UserResponse } from '@/types';

export const usersApi = {
  /** GET /api/v1/users/me */
  getMe: () =>
    api.get<ApiResponse<UserResponse>>('/api/v1/users/me').then(r => r.data),
};
