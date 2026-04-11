'use client';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { adminApi, feedbackApi } from '@/lib/api/admin';
import { toast } from 'sonner';
import type { DataEntryRequest, DataUpdateRequest, FeedbackRequest } from '@/types';

export function useAdminAddData() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: DataEntryRequest) => adminApi.addData(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['admin'] });
      toast.success('Data added.');
    },
    onError: () => toast.error('Failed to add data.'),
  });
}

export function useAdminUpdateData() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ verifyId, data }: { verifyId: string; data: DataUpdateRequest }) =>
      adminApi.updateData(verifyId, data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['admin'] });
      toast.success('Data updated.');
    },
    onError: () => toast.error('Failed to update data.'),
  });
}

export function useAdminDeleteData() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: adminApi.deleteData,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['admin'] });
      toast.success('Data deleted.');
    },
    onError: () => toast.error('Failed to delete data.'),
  });
}

export function useAdminBlockUser() {
  return useMutation({
    mutationFn: adminApi.blockUser,
    onSuccess: () => toast.success('User blocked.'),
    onError: () => toast.error('Failed to block user.'),
  });
}

export function useAdminDeleteUser() {
  return useMutation({
    mutationFn: adminApi.deleteUser,
    onSuccess: () => toast.success('User deleted.'),
    onError: () => toast.error('Failed to delete user.'),
  });
}

// Feedback hooks
export function useCreateFeedback() {
  return useMutation({
    mutationFn: (data: FeedbackRequest) => feedbackApi.create(data),
    onSuccess: () => toast.success('Feedback submitted.'),
    onError: () => toast.error('Failed to submit feedback.'),
  });
}

export function useUpdateFeedback() {
  return useMutation({
    mutationFn: ({ feedbackId, data }: { feedbackId: string; data: FeedbackRequest }) =>
      feedbackApi.update(feedbackId, data),
    onSuccess: () => toast.success('Feedback updated.'),
    onError: () => toast.error('Failed to update feedback.'),
  });
}

export function useDeleteFeedback() {
  return useMutation({
    mutationFn: feedbackApi.delete,
    onSuccess: () => toast.success('Feedback deleted.'),
    onError: () => toast.error('Failed to delete feedback.'),
  });
}
