import { api } from './client';
import type { ApiResponse } from '@/types';

export const storageApi = {
  upload: (file: File, onProgress?: (progress: number) => void) => {
    const formData = new FormData();
    formData.append('file', file);

    return api.post<ApiResponse<{ url: string }>>('/api/v1/storage/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
      onUploadProgress: (event) => {
        if (onProgress && event.total) {
          onProgress(Math.round((event.loaded * 100) / event.total));
        }
      },
    }).then(r => r.data);
  },
};
