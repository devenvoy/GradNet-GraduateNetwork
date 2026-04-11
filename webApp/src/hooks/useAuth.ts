'use client';
import { useMutation, useQuery } from '@tanstack/react-query';
import { authApi } from '@/lib/api/auth';
import { usersApi } from '@/lib/api/users';
import { useAuthStore } from '@/stores/authStore';
import { useRouter } from 'next/navigation';
import Cookies from 'js-cookie';
import { toast } from 'sonner';
import type { LoginRequest, SignupRequest } from '@/types';

export function useLogin() {
  const { setAuth } = useAuthStore();
  const router = useRouter();

  return useMutation({
    mutationFn: (data: LoginRequest) => authApi.login(data),
    onSuccess: async (res) => {
      if (!res.data) return;
      const { accessToken, refreshToken, user } = res.data;
      setAuth(accessToken, user);

      // Store refresh token in httpOnly cookie via BFF
      await fetch('/api/auth/refresh', {
        method: 'PUT',
        body: JSON.stringify({ refreshToken }),
        headers: { 'Content-Type': 'application/json' },
      });

      if (user.roles.includes('ADMIN') || user.roles.includes('ACCOUNT_OWNER')) {
        Cookies.set('gradnet_is_admin', '1', { sameSite: 'strict' });
      }

      toast.success('Welcome back!');
      router.push('/feed');
    },
    onError: () => {
      toast.error('Invalid credentials. Please try again.');
    },
  });
}

export function useSignup() {
  const router = useRouter();

  return useMutation({
    mutationFn: (data: SignupRequest) => authApi.signup(data),
    onSuccess: () => {
      toast.success('Account created! Please verify your email.');
      router.push('/verify-otp');
    },
    onError: () => {
      toast.error('Signup failed. Please try again.');
    },
  });
}

export function useLogout() {
  const { clearAuth } = useAuthStore();
  const router = useRouter();

  return useMutation({
    mutationFn: async () => {
      await fetch('/api/auth/refresh', { method: 'DELETE' });
      Cookies.remove('gradnet_is_admin');
    },
    onSuccess: () => {
      clearAuth();
      router.push('/login');
    },
  });
}

export function useCurrentUser() {
  const { isAuthenticated, setUser } = useAuthStore();

  return useQuery({
    queryKey: ['currentUser'],
    queryFn: async () => {
      const res = await usersApi.getMe();
      if (res.data) setUser(res.data);
      return res.data;
    },
    enabled: isAuthenticated,
    staleTime: 5 * 60 * 1000,
  });
}

export function useVerifyOtp() {
  const router = useRouter();

  return useMutation({
    mutationFn: ({ email, otp }: { email: string; otp: string }) =>
      authApi.verifyOtp({ email, otp }),
    onSuccess: () => {
      toast.success('Email verified successfully!');
      router.push('/login');
    },
    onError: () => {
      toast.error('Invalid OTP. Please try again.');
    },
  });
}

export function useForgotPassword() {
  return useMutation({
    mutationFn: (email: string) => authApi.forgotPassword({ email }),
    onSuccess: () => {
      toast.success('Password reset link sent to your email.');
    },
    onError: () => {
      toast.error('Failed to send reset link.');
    },
  });
}

export function useResetPassword() {
  const router = useRouter();

  return useMutation({
    mutationFn: ({ token, password }: { token: string; password: string }) =>
      authApi.resetPassword(token, { password }),
    onSuccess: () => {
      toast.success('Password reset successfully!');
      router.push('/login');
    },
    onError: () => {
      toast.error('Failed to reset password.');
    },
  });
}

export function useChangePassword() {
  return useMutation({
    mutationFn: authApi.changePassword,
    onSuccess: () => {
      toast.success('Password changed successfully!');
    },
    onError: () => {
      toast.error('Failed to change password.');
    },
  });
}

export function useSetPassword() {
  return useMutation({
    mutationFn: (password: string) => authApi.setPassword({ password }),
    onSuccess: () => {
      toast.success('Password set successfully!');
    },
    onError: () => {
      toast.error('Failed to set password.');
    },
  });
}

export function useDeleteAccount() {
  const { clearAuth } = useAuthStore();
  const router = useRouter();

  return useMutation({
    mutationFn: () => authApi.deleteUser(),
    onSuccess: async () => {
      await fetch('/api/auth/refresh', { method: 'DELETE' });
      Cookies.remove('gradnet_is_admin');
      clearAuth();
      toast.success('Account deleted.');
      router.push('/login');
    },
    onError: () => {
      toast.error('Failed to delete account.');
    },
  });
}
