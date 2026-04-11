'use client';
import { useState } from 'react';
import { storageApi } from '@/lib/api/storage';
import { toast } from 'sonner';

export function useUpload() {
  const [progress, setProgress] = useState(0);
  const [isUploading, setIsUploading] = useState(false);

  const upload = async (file: File, imgType: string = 'PROFILE'): Promise<string | null> => {
    setIsUploading(true);
    setProgress(0);
    try {
      const res = await storageApi.upload(file, imgType, setProgress);
      toast.success('File uploaded!');
      return res.data?.fileUrl ?? null;
    } catch {
      toast.error('Upload failed.');
      return null;
    } finally {
      setIsUploading(false);
    }
  };

  return { upload, progress, isUploading };
}
