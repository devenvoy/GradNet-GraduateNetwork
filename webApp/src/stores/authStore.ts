import { create } from 'zustand';
import type { UserResponse } from '@/types';

interface AuthState {
  accessToken: string | null;
  user: UserResponse | null;
  isAuthenticated: boolean;
  setAuth: (token: string, user: UserResponse) => void;
  setAccessToken: (token: string) => void;
  setUser: (user: UserResponse) => void;
  clearAuth: () => void;
  hasRole: (role: string) => boolean;
  hasPermission: (permission: string) => boolean;
  isAdmin: () => boolean;
}

export const useAuthStore = create<AuthState>((set, get) => ({
  accessToken: null,
  user: null,
  isAuthenticated: false,

  setAuth: (token, user) => set({ accessToken: token, user, isAuthenticated: true }),
  setAccessToken: (token) => set({ accessToken: token }),
  setUser: (user) => set({ user }),
  clearAuth: () => set({ accessToken: null, user: null, isAuthenticated: false }),

  hasRole: (role) => get().user?.roles.includes(role) ?? false,
  hasPermission: (perm) => get().user?.permissions.includes(perm) ?? false,
  isAdmin: () => {
    const roles = get().user?.roles ?? [];
    return roles.includes('ADMIN') || roles.includes('ACCOUNT_OWNER');
  },
}));
