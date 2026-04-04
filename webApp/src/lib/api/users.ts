import { api } from './client';
import type { ApiResponse, UserResponse } from '@/types';

export const usersApi = {
  getMe: () =>
    api.get<ApiResponse<UserResponse>>('/api/v1/users/me').then(r => r.data),
};
