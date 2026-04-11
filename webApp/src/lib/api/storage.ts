import { api } from './client';
import type { ApiResponse, MediaUploadResponse } from '@/types';

export const storageApi = {
  /** POST /api/v1/storage/upload?img_type= */
  upload: (file: File, imgType: string = 'PROFILE', onProgress?: (progress: number) => void) => {
    const formData = new FormData();
    formData.append('file', file);

    return api.post<ApiResponse<MediaUploadResponse>>('/api/v1/storage/upload', formData, {
      params: { img_type: imgType },
      headers: { 'Content-Type': 'multipart/form-data' },
      onUploadProgress: (event) => {
        if (onProgress && event.total) {
          onProgress(Math.round((event.loaded * 100) / event.total));
        }
      },
    }).then(r => r.data);
  },
};
