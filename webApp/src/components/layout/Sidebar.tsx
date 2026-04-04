'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { useAuthStore } from '@/stores/authStore';
import { cn } from '@/lib/utils';
import { getInitials } from '@/lib/utils/formatters';

const navItems = [
  { href: '/feed', icon: 'dynamic_feed', label: 'Feed' },
  { href: '/jobs', icon: 'work', label: 'Jobs' },
  { href: '/events', icon: 'event', label: 'Events' },
  { href: '/lost-found', icon: 'search_check', label: 'Lost & Found' },
  { href: '/profile', icon: 'person', label: 'Profile' },
  { href: '/settings', icon: 'settings', label: 'Settings' },
];

const adminItems = [
  { href: '/admin', icon: 'dashboard', label: 'Dashboard' },
  { href: '/admin/users', icon: 'group', label: 'Users' },
  { href: '/admin/data', icon: 'database', label: 'Data' },
  { href: '/admin/analytics', icon: 'analytics', label: 'Analytics' },
  { href: '/admin/feedback', icon: 'feedback', label: 'Feedback' },
];

export default function Sidebar() {
  const pathname = usePathname();
  const { user, isAdmin } = useAuthStore();

  return (
    <aside className="h-screen w-[240px] hidden md:flex flex-col bg-surface-container-low justify-between py-8 px-4 text-sm font-medium sticky top-0 overflow-y-auto">
      <div>
        <Link href="/feed" className="block mb-8">
          <h1 className="font-headline text-2xl font-black text-white tracking-tight">GradNet</h1>
        </Link>

        <nav className="space-y-1">
          {navItems.map((item) => {
            const isActive = pathname === item.href || pathname.startsWith(item.href + '/');
            return (
              <Link
                key={item.href}
                href={item.href}
                className={cn(
                  'flex items-center gap-4 px-4 py-3 rounded-lg transition-all duration-300',
                  isActive
                    ? 'bg-primary-container/10 text-primary border-l-4 border-primary-container'
                    : 'text-on-surface-variant hover:bg-surface-container-high/50 hover:text-on-surface'
                )}
              >
                <span className="material-symbols-outlined text-xl">{item.icon}</span>
                <span>{item.label}</span>
              </Link>
            );
          })}
        </nav>
      </div>

      <div className="mt-auto space-y-6">
        {/* User Card */}
        <div className="bg-surface-container-high/40 p-4 rounded-xl">
          <div className="flex items-center gap-3">
            {user?.avatarUrl ? (
              <img
                src={user.avatarUrl}
                alt="Avatar"
                className="w-10 h-10 rounded-full object-cover ring-2 ring-primary/20"
              />
            ) : (
              <div className="w-10 h-10 rounded-full bg-primary-container flex items-center justify-center text-on-primary text-sm font-bold">
                {getInitials(`${user?.firstName ?? ''} ${user?.lastName ?? ''}`)}
              </div>
            )}
            <div className="min-w-0">
              <p className="text-white font-bold text-sm truncate">
                {user?.displayName || `${user?.firstName ?? ''} ${user?.lastName ?? ''}`.trim() || 'User'}
              </p>
              <p className="text-[10px] text-on-surface-variant truncate">{user?.email}</p>
            </div>
          </div>
        </div>

        {/* Admin Links */}
        {isAdmin() && (
          <div className="pt-4 border-t border-white/5 space-y-1">
            {adminItems.map((item) => {
              const isActive = pathname === item.href;
              return (
                <Link
                  key={item.href}
                  href={item.href}
                  className={cn(
                    'flex items-center gap-3 px-4 py-2 text-xs uppercase tracking-widest transition-colors rounded-md',
                    isActive ? 'text-primary bg-primary/5' : 'text-on-surface-variant hover:text-primary'
                  )}
                >
                  <span className="material-symbols-outlined text-sm">{item.icon}</span>
                  {item.label}
                </Link>
              );
            })}
          </div>
        )}
      </div>
    </aside>
  );
}
