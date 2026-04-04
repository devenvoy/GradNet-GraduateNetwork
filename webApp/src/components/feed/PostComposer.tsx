'use client';
import { useState } from 'react';
import Avatar from '@/components/shared/Avatar';
import ImageUpload from '@/components/shared/ImageUpload';
import { useAuthStore } from '@/stores/authStore';
import { useCreatePost } from '@/hooks/usePosts';

export default function PostComposer() {
  const { user } = useAuthStore();
  const createPost = useCreatePost();
  const [description, setDescription] = useState('');
  const [images, setImages] = useState<string[]>([]);
  const [isExpanded, setIsExpanded] = useState(false);

  const handleSubmit = () => {
    if (!description.trim() && images.length === 0) return;
    createPost.mutate({ description, images: images.length > 0 ? images : undefined }, {
      onSuccess: () => {
        setDescription('');
        setImages([]);
        setIsExpanded(false);
      },
    });
  };

  return (
    <section className="bg-surface-container rounded-2xl p-6 shadow-xl shadow-black/10">
      <div className="flex items-center gap-4">
        <Avatar src={user?.avatarUrl} name={`${user?.firstName} ${user?.lastName}`} />
        {!isExpanded ? (
          <button
            onClick={() => setIsExpanded(true)}
            className="flex-1 bg-surface-container-low rounded-full px-6 py-3 text-on-surface-variant/60 text-left hover:bg-surface-container-lowest transition-colors text-sm"
          >
            What&apos;s on your mind?
          </button>
        ) : (
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Share a thought, article, or discovery..."
            className="flex-1 bg-surface-container-lowest rounded-xl px-4 py-3 text-on-surface text-sm resize-none focus:ring-1 focus:ring-primary outline-none min-h-[80px]"
            autoFocus
          />
        )}
      </div>

      {images.length > 0 && (
        <div className="flex gap-2 mt-4 flex-wrap">
          {images.map((img, i) => (
            <div key={i} className="relative group">
              <img src={img} alt="" className="w-20 h-20 rounded-lg object-cover" />
              <button
                onClick={() => setImages(images.filter((_, idx) => idx !== i))}
                className="absolute -top-1 -right-1 w-5 h-5 bg-error rounded-full text-white text-xs flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
              >
                ×
              </button>
            </div>
          ))}
        </div>
      )}

      <div className="flex justify-between items-center mt-6 pt-4 border-t border-white/5">
        <div className="flex gap-4">
          <ImageUpload onUpload={(url) => setImages([...images, url])} />
        </div>
        <button
          onClick={handleSubmit}
          disabled={createPost.isPending || (!description.trim() && images.length === 0)}
          className="btn-gradient px-6 py-2 rounded-full text-sm disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {createPost.isPending ? 'Posting...' : 'POST'}
        </button>
      </div>
    </section>
  );
}
