'use client';
import Avatar from '@/components/shared/Avatar';
import RoleBadge from '@/components/shared/RoleBadge';
import { timeAgo } from '@/lib/utils/formatters';
import { useLikePost, useDeletePost } from '@/hooks/usePosts';
import { useAuthStore } from '@/stores/authStore';
import type { PostResponse } from '@/types';

export default function PostCard({ post }: { post: PostResponse }) {
  const likePost = useLikePost();
  const deletePost = useDeletePost();
  const { user } = useAuthStore();
  const isOwner = user?.userId === post.userId;

  return (
    <article className="bg-surface-container rounded-2xl overflow-hidden shadow-sm hover:bg-surface-container-high/20 transition-colors">
      <div className="p-6">
        <div className="flex justify-between items-start mb-4">
          <div className="flex items-center gap-3">
            <Avatar src={post.userAvatar} name={post.userName} />
            <div>
              <div className="flex items-center gap-2">
                <h3 className="font-bold text-white leading-none">{post.userName || 'Anonymous'}</h3>
                <RoleBadge role={post.userRole} />
              </div>
              <p className="text-xs text-on-surface-variant mt-1 italic">{timeAgo(post.createdAt)}</p>
            </div>
          </div>
          {isOwner && (
            <button onClick={() => deletePost.mutate(post.id)} className="text-on-surface-variant hover:text-error transition-colors">
              <span className="material-symbols-outlined">delete</span>
            </button>
          )}
        </div>

        {post.location && (
          <div className="inline-flex items-center gap-1 bg-surface-container-high px-3 py-1 rounded-full mb-3">
            <span className="material-symbols-outlined text-xs text-secondary">location_on</span>
            <span className="text-[10px] font-bold text-on-surface uppercase tracking-tighter">{post.location}</span>
          </div>
        )}

        {post.description && (
          <p className="text-sm text-on-surface leading-relaxed mb-4">{post.description}</p>
        )}
      </div>

      {post.images && post.images.length > 0 && (
        <div className={`grid gap-1 px-4 ${post.images.length > 1 ? 'grid-cols-2' : 'grid-cols-1'}`}>
          {post.images.slice(0, 4).map((img, i) => (
            <img key={i} src={img} alt="" className="w-full h-48 object-cover rounded-xl" />
          ))}
        </div>
      )}

      <div className="p-6 flex justify-between border-t border-white/5 mt-4">
        <div className="flex gap-6">
          <button
            onClick={() => likePost.mutate(post.id)}
            className={`flex items-center gap-1.5 transition-colors ${post.liked ? 'text-primary' : 'text-on-surface-variant hover:text-primary'}`}
          >
            <span className="material-symbols-outlined text-lg" style={post.liked ? { fontVariationSettings: "'FILL' 1" } : undefined}>
              {post.liked ? 'favorite' : 'thumb_up'}
            </span>
            <span className="text-xs font-semibold">{post.likeCount}</span>
          </button>
          <button className="flex items-center gap-1.5 text-on-surface-variant hover:text-primary transition-colors">
            <span className="material-symbols-outlined text-lg">chat_bubble</span>
          </button>
          <button className="flex items-center gap-1.5 text-on-surface-variant hover:text-primary transition-colors">
            <span className="material-symbols-outlined text-lg">share</span>
          </button>
        </div>
        <button className="text-on-surface-variant hover:text-primary transition-colors">
          <span className="material-symbols-outlined text-lg">bookmark</span>
        </button>
      </div>
    </article>
  );
}
