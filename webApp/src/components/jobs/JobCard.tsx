'use client';
import { cn } from '@/lib/utils';
import { timeAgo } from '@/lib/utils/formatters';
import { useSaveJob } from '@/hooks/useJobs';
import type { JobResponse } from '@/types';
import Link from 'next/link';

export default function JobCard({ job }: { job: JobResponse }) {
  const saveJob = useSaveJob();

  const workModeLabel: Record<string, string> = {
    REMOTE: 'Remote',
    HYBRID: 'Hybrid',
    ON_SITE: 'On-site',
  };

  return (
    <Link href={`/jobs/${job.id}`} className="block">
      <article className="bg-surface-container rounded-2xl p-6 hover:bg-surface-container-highest transition-colors group cursor-pointer">
        <div className="flex items-start justify-between mb-4">
          <div className="flex items-start gap-4">
            {job.companyLogo ? (
              <img src={job.companyLogo} alt={job.companyName || ''} className="w-12 h-12 rounded-xl object-cover" />
            ) : (
              <div className="w-12 h-12 rounded-xl bg-primary-container/20 flex items-center justify-center">
                <span className="material-symbols-outlined text-primary">work</span>
              </div>
            )}
            <div>
              <h3 className="font-bold text-white group-hover:text-primary transition-colors">{job.jobTitle}</h3>
              <p className="text-xs text-on-surface-variant mt-1">{job.companyName}</p>
            </div>
          </div>
          <button
            onClick={(e) => { e.preventDefault(); saveJob.mutate(job.id); }}
            className={cn('text-on-surface-variant hover:text-secondary transition-colors', job.saved && 'text-secondary')}
          >
            <span className="material-symbols-outlined" style={job.saved ? { fontVariationSettings: "'FILL' 1" } : undefined}>bookmark</span>
          </button>
        </div>

        {job.jobOverview && (
          <p className="text-sm text-on-surface-variant leading-relaxed mb-4 line-clamp-2">{job.jobOverview}</p>
        )}

        <div className="flex flex-wrap gap-2 mb-4">
          {job.workMode && (
            <span className="px-3 py-1 rounded-full bg-primary-container/10 text-primary text-[10px] font-bold uppercase tracking-wider">
              {workModeLabel[job.workMode] || job.workMode}
            </span>
          )}
          {job.jobLocation && (
            <span className="px-3 py-1 rounded-full bg-surface-container-high text-on-surface-variant text-[10px] font-bold uppercase tracking-wider">
              {job.jobLocation}
            </span>
          )}
          {job.salary && (
            <span className="px-3 py-1 rounded-full bg-secondary-container/10 text-secondary text-[10px] font-bold uppercase tracking-wider">
              {job.salary}
            </span>
          )}
        </div>

        {job.skills && job.skills.length > 0 && (
          <div className="flex flex-wrap gap-1.5">
            {job.skills.slice(0, 4).map((skill) => (
              <span key={skill} className="px-2 py-0.5 rounded bg-tertiary-container/20 text-tertiary text-[10px] font-bold">
                {skill}
              </span>
            ))}
            {job.skills.length > 4 && (
              <span className="text-[10px] text-on-surface-variant">+{job.skills.length - 4} more</span>
            )}
          </div>
        )}

        <p className="text-[10px] text-on-surface-variant mt-4 italic">{timeAgo(job.createdAt)}</p>
      </article>
    </Link>
  );
}
