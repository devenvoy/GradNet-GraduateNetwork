'use client';

import Avatar from '@/components/shared/Avatar';
import { useMyProfile } from '@/hooks/useProfile';
import { useAuthStore } from '@/stores/authStore';
import RoleBadge from '@/components/shared/RoleBadge';

export default function ProfilePage() {
  const { user } = useAuthStore();
  const { data: profileRes, isLoading } = useMyProfile();
  const profile = profileRes?.data;

  if (isLoading) {
    return (
      <div className="max-w-3xl mx-auto space-y-6">
        <div className="bg-surface-container rounded-2xl p-8 animate-pulse">
          <div className="flex items-center gap-6 mb-8">
            <div className="w-24 h-24 rounded-2xl bg-surface-container-high" />
            <div className="space-y-2 flex-1">
              <div className="h-6 w-48 bg-surface-container-high rounded" />
              <div className="h-4 w-32 bg-surface-container-high rounded" />
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto space-y-6">
      {/* Profile Header */}
      <div className="relative bg-surface-container rounded-2xl overflow-hidden">
        <div className="w-full h-40 campus-mesh bg-surface-container-lowest" />
        <div className="p-8 pt-0 -mt-12 relative z-10">
          <div className="flex items-end gap-6">
            <Avatar src={profile?.avatarUrl || user?.avatarUrl} name={`${user?.firstName} ${user?.lastName}`} size="lg" className="ring-4 ring-surface-container" />
            <div className="flex-1 mb-2">
              <h1 className="font-headline text-2xl font-bold text-white">
                {profile?.displayName || user?.displayName || `${user?.firstName ?? ''} ${user?.lastName ?? ''}`.trim() || 'User'}
              </h1>
              <div className="flex items-center gap-2 mt-1">
                <RoleBadge role={user?.roles?.[0] ?? null} />
                {profile?.designation && <span className="text-sm text-on-surface-variant">• {profile.designation}</span>}
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* About */}
      {profile?.aboutSelf && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-3">About</h2>
          <p className="text-sm text-on-surface leading-relaxed">{profile.aboutSelf}</p>
        </div>
      )}

      {/* Skills */}
      {profile?.skills && profile.skills.length > 0 && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-3">Skills</h2>
          <div className="flex flex-wrap gap-2">
            {profile.skills.map((skill) => (
              <span key={skill} className="px-3 py-1 rounded-full bg-primary-container/10 text-primary text-xs font-bold">{skill}</span>
            ))}
          </div>
        </div>
      )}

      {/* Languages */}
      {profile?.languages && profile.languages.length > 0 && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-3">Languages</h2>
          <div className="flex flex-wrap gap-2">
            {profile.languages.map((lang) => (
              <span key={lang} className="px-3 py-1 rounded-full bg-secondary-container/10 text-secondary text-xs font-bold">{lang}</span>
            ))}
          </div>
        </div>
      )}

      {/* Professional Info */}
      {(profile?.department || profile?.designation || profile?.industryType || profile?.employee) && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-4">Professional</h2>
          <div className="space-y-3">
            {profile?.designation && (
              <div className="flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-tertiary/10 flex items-center justify-center shrink-0">
                  <span className="material-symbols-outlined text-tertiary text-sm">work</span>
                </div>
                <div>
                  <p className="text-white font-bold text-sm">{profile.designation}</p>
                  {profile.employee && <p className="text-xs text-on-surface-variant">{profile.employee}</p>}
                </div>
              </div>
            )}
            {profile?.department && (
              <div className="flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-primary/10 flex items-center justify-center shrink-0">
                  <span className="material-symbols-outlined text-primary text-sm">apartment</span>
                </div>
                <p className="text-white font-bold text-sm">{profile.department}</p>
              </div>
            )}
            {profile?.industryType && (
              <div className="flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-secondary/10 flex items-center justify-center shrink-0">
                  <span className="material-symbols-outlined text-secondary text-sm">factory</span>
                </div>
                <p className="text-white font-bold text-sm">{profile.industryType}</p>
              </div>
            )}
          </div>
        </div>
      )}

      {/* Education */}
      {profile?.education && profile.education.length > 0 && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-4">Education</h2>
          <div className="space-y-4">
            {profile.education.map((edu, i) => (
              <div key={i} className="flex items-start gap-3">
                <div className="w-8 h-8 rounded-lg bg-secondary/10 flex items-center justify-center shrink-0 mt-1">
                  <span className="material-symbols-outlined text-secondary text-sm">school</span>
                </div>
                <div>
                  <p className="text-white font-bold text-sm">{String(edu.institution ?? edu.name ?? '')}</p>
                  <p className="text-xs text-on-surface-variant">{String(edu.degree ?? '')} {edu.field ? `• ${edu.field}` : ''}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Experience */}
      {profile?.experience && profile.experience.length > 0 && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-4">Experience</h2>
          <div className="space-y-4">
            {profile.experience.map((exp, i) => (
              <div key={i} className="flex items-start gap-3">
                <div className="w-8 h-8 rounded-lg bg-tertiary/10 flex items-center justify-center shrink-0 mt-1">
                  <span className="material-symbols-outlined text-tertiary text-sm">work</span>
                </div>
                <div>
                  <p className="text-white font-bold text-sm">{String(exp.title ?? '')}</p>
                  <p className="text-xs text-on-surface-variant">{String(exp.company ?? '')}{exp.location ? ` • ${exp.location}` : ''}</p>
                  {exp.description && <p className="text-xs text-on-surface-variant mt-1 line-clamp-2">{String(exp.description)}</p>}
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Website */}
      {profile?.website && (
        <div className="bg-surface-container rounded-2xl p-6">
          <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-3">Website</h2>
          <a href={profile.website} target="_blank" rel="noopener noreferrer" className="text-primary text-sm hover:underline flex items-center gap-1">
            <span className="material-symbols-outlined text-sm">link</span>
            {profile.website}
          </a>
        </div>
      )}
    </div>
  );
}
