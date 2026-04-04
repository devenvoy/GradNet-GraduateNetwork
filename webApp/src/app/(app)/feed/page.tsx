'use client';

import PostComposer from '@/components/feed/PostComposer';
import PostCard from '@/components/feed/PostCard';
import { PostSkeleton } from '@/components/shared/LoadingSkeletons';
import EmptyState from '@/components/shared/EmptyState';
import { useFeed } from '@/hooks/usePosts';
import { useEffect, useRef, useCallback } from 'react';

export default function FeedPage() {
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isLoading, isError } = useFeed();
  const observerRef = useRef<HTMLDivElement>(null);

  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      const [entry] = entries;
      if (entry.isIntersecting && hasNextPage && !isFetchingNextPage) {
        fetchNextPage();
      }
    },
    [fetchNextPage, hasNextPage, isFetchingNextPage]
  );

  useEffect(() => {
    const el = observerRef.current;
    if (!el) return;
    const observer = new IntersectionObserver(handleObserver, { threshold: 0.1 });
    observer.observe(el);
    return () => observer.disconnect();
  }, [handleObserver]);

  const posts = data?.pages.flatMap((p) => p.data?.items ?? []) ?? [];

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <PostComposer />
      
      {isLoading && Array.from({ length: 3 }).map((_, i) => <PostSkeleton key={i} />)}
      
      {isError && (
        <div className="bg-error-container/20 text-error p-4 rounded-xl text-sm text-center">
          Failed to load feed. Please try again.
        </div>
      )}

      {!isLoading && posts.length === 0 && (
        <EmptyState icon="dynamic_feed" title="No posts yet" description="Be the first to share something with the community." />
      )}

      {posts.map((post) => (
        <PostCard key={post.id} post={post} />
      ))}

      {isFetchingNextPage && <PostSkeleton />}
      <div ref={observerRef} className="h-4" />
    </div>
  );
}
