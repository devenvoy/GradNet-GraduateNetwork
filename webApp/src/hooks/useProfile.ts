'use client';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { profileApi } from '@/lib/api/profile';
import { toast } from 'sonner';
import type { ProfileCreateUpdateRequest } from '@/types';

export const PROFILE_QUERY_KEY = ['profile'];

export function useMyProfile() {
  return useQuery({
    queryKey: [...PROFILE_QUERY_KEY, 'me'],
    queryFn: profileApi.getMy,
    staleTime: 5 * 60 * 1000,
  });
}

export function useProfileByUserId(userId: string) {
  return useQuery({
    queryKey: [...PROFILE_QUERY_KEY, userId],
    queryFn: () => profileApi.getByUserId(userId),
    enabled: !!userId,
  });
}

export function useUpdateProfile() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: ProfileCreateUpdateRequest) => profileApi.createOrUpdate(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: PROFILE_QUERY_KEY });
      toast.success('Profile updated!');
    },
    onError: () => toast.error('Failed to update profile.'),
  });
}

export function useSearchProfiles(query: string) {
  return useQuery({
    queryKey: [...PROFILE_QUERY_KEY, 'search', query],
    queryFn: () => profileApi.search(query),
    enabled: query.length >= 2,
    staleTime: 10_000,
  });
}
