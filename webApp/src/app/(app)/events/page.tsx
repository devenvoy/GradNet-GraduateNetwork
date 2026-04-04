'use client';

import EventCard from '@/components/events/EventCard';
import { CardSkeleton } from '@/components/shared/LoadingSkeletons';
import EmptyState from '@/components/shared/EmptyState';
import { useEvents } from '@/hooks/useEvents';
import { useState } from 'react';
import { cn } from '@/lib/utils';

const tabs = ['All', 'workshop', 'seminar', 'social', 'cultural', 'sports', 'other'] as const;

export default function EventsPage() {
  const [activeTab, setActiveTab] = useState<string>('All');
  const { data, isLoading } = useEvents({ eventType: activeTab === 'All' ? undefined : activeTab });
  const events = data?.data ?? [];

  return (
    <div className="max-w-5xl mx-auto">
      <div className="mb-8">
        <h1 className="font-headline text-3xl font-bold text-white">Events</h1>
        <p className="text-sm text-on-surface-variant mt-1">Stay connected to campus life.</p>
      </div>

      <div className="flex gap-2 mb-8 overflow-x-auto pb-2 scrollbar-none">
        {tabs.map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={cn(
              'px-4 py-2 rounded-full text-xs font-bold uppercase tracking-wider whitespace-nowrap transition-all shrink-0',
              activeTab === tab
                ? 'btn-gradient'
                : 'bg-surface-container text-on-surface-variant hover:bg-surface-container-high'
            )}
          >
            {tab === 'All' ? 'All Events' : tab.charAt(0).toUpperCase() + tab.slice(1)}
          </button>
        ))}
      </div>

      {isLoading && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {Array.from({ length: 6 }).map((_, i) => <CardSkeleton key={i} />)}
        </div>
      )}

      {!isLoading && events.length === 0 && (
        <EmptyState icon="event_busy" title="No events" description="No upcoming events in this category." />
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {events.map((event) => <EventCard key={event.id} event={event} />)}
      </div>
    </div>
  );
}
