'use client';

import { CardSkeleton } from '@/components/shared/LoadingSkeletons';
import EmptyState from '@/components/shared/EmptyState';
import { useLostFound } from '@/hooks/useLostFound';
import { timeAgo } from '@/lib/utils/formatters';
import type { LostFoundResponse } from '@/types';

function LostFoundCard({ item }: { item: LostFoundResponse }) {
  return (
    <article className="bg-surface-container rounded-2xl overflow-hidden hover:bg-surface-container-highest transition-colors">
      {item.images && item.images.length > 0 && (
        <img src={item.images[0]} alt="Lost or Found item" className="w-full h-40 object-cover" />
      )}
      <div className="p-6">
        <div className="flex items-start justify-between mb-2">
          <span className="text-[10px] text-on-surface-variant italic">{timeAgo(item.createdAt)}</span>
        </div>
        {item.description && (
          <p className="text-sm text-on-surface-variant line-clamp-3 mb-4">{item.description}</p>
        )}
      </div>
    </article>
  );
}

export default function LostFoundPage() {
  const { data, isLoading } = useLostFound();
  const items = data?.pages.flatMap((p) => p.data?.items ?? []) ?? [];

  return (
    <div className="max-w-5xl mx-auto">
      <div className="mb-8">
        <h1 className="font-headline text-3xl font-bold text-white">Lost & Found</h1>
        <p className="text-sm text-on-surface-variant mt-1">Help reunite items with their owners.</p>
      </div>

      {isLoading && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {Array.from({ length: 6 }).map((_, i) => <CardSkeleton key={i} />)}
        </div>
      )}

      {!isLoading && items.length === 0 && (
        <EmptyState icon="search_off" title="Nothing here" description="No lost or found items posted yet." />
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {items.map((item) => <LostFoundCard key={item.id} item={item} />)}
      </div>
    </div>
  );
}
