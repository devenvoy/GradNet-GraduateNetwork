'use client';

import JobCard from '@/components/jobs/JobCard';
import { JobSkeleton } from '@/components/shared/LoadingSkeletons';
import EmptyState from '@/components/shared/EmptyState';
import { useJobs } from '@/hooks/useJobs';
import { useState } from 'react';
import { cn } from '@/lib/utils';

const workModes = ['All', 'REMOTE', 'HYBRID', 'ON_SITE'] as const;

export default function JobsPage() {
  const [activeMode, setActiveMode] = useState<string>('All');
  const [search, setSearch] = useState('');
  const { data, isLoading } = useJobs({
    work_mode: activeMode === 'All' ? undefined : [activeMode],
    search: search || undefined,
  });

  const jobs = data?.pages.flatMap((p) => p.data?.items ?? []) ?? [];

  return (
    <div className="max-w-5xl mx-auto">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-8">
        <div>
          <h1 className="font-headline text-3xl font-bold text-white">Opportunities</h1>
          <p className="text-sm text-on-surface-variant mt-1">Discover roles matching your expertise.</p>
        </div>
      </div>

      <div className="flex flex-col sm:flex-row gap-4 mb-8">
        <div className="flex-1 relative">
          <span className="absolute left-4 top-1/2 -translate-y-1/2 material-symbols-outlined text-on-surface-variant">search</span>
          <input
            type="text"
            placeholder="Search jobs..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="w-full bg-surface-container-lowest rounded-xl pl-12 pr-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none text-sm"
          />
        </div>
        <div className="flex gap-2">
          {workModes.map((mode) => (
            <button
              key={mode}
              onClick={() => setActiveMode(mode)}
              className={cn(
                'px-4 py-2 rounded-xl text-xs font-bold uppercase tracking-wider transition-all',
                activeMode === mode
                  ? 'btn-gradient'
                  : 'bg-surface-container text-on-surface-variant hover:bg-surface-container-high'
              )}
            >
              {mode === 'ON_SITE' ? 'On-site' : mode === 'All' ? 'All' : mode.charAt(0) + mode.slice(1).toLowerCase()}
            </button>
          ))}
        </div>
      </div>

      {isLoading && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {Array.from({ length: 6 }).map((_, i) => <JobSkeleton key={i} />)}
        </div>
      )}

      {!isLoading && jobs.length === 0 && (
        <EmptyState icon="work_off" title="No jobs found" description="Try adjusting your filters or check back later." />
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {jobs.map((job) => <JobCard key={job.id} job={job} />)}
      </div>
    </div>
  );
}
