'use client';
import { useUpload } from '@/hooks/useUpload';
import { useRef } from 'react';

interface ImageUploadProps {
  onUpload: (url: string) => void;
  className?: string;
}

export default function ImageUpload({ onUpload, className }: ImageUploadProps) {
  const { upload, progress, isUploading } = useUpload();
  const inputRef = useRef<HTMLInputElement>(null);

  const handleChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    const url = await upload(file);
    if (url) onUpload(url);
    if (inputRef.current) inputRef.current.value = '';
  };

  return (
    <div className={className}>
      <input ref={inputRef} type="file" accept="image/*" onChange={handleChange} className="hidden" id="image-upload" />
      <label
        htmlFor="image-upload"
        className="flex items-center gap-2 cursor-pointer text-on-surface-variant hover:text-primary transition-colors"
      >
        <span className="material-symbols-outlined text-xl">image</span>
        <span className="text-sm font-medium">{isUploading ? `${progress}%` : 'Media'}</span>
      </label>
      {isUploading && (
        <div className="mt-2 h-1 bg-surface-container-high rounded-full overflow-hidden">
          <div className="h-full bg-primary transition-all duration-300 rounded-full" style={{ width: `${progress}%` }} />
        </div>
      )}
    </div>
  );
}
