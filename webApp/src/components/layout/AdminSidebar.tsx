'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { cn } from '@/lib/utils';

const adminItems = [
  { href: '/admin', icon: 'dashboard', label: 'Dashboard', exact: true },
  { href: '/admin/users', icon: 'group', label: 'Users' },
  { href: '/admin/data', icon: 'database', label: 'Verify Data' },
  { href: '/admin/analytics', icon: 'analytics', label: 'Analytics' },
  { href: '/admin/feedback', icon: 'feedback', label: 'Feedback' },
];

export default function AdminSidebar() {
  const pathname = usePathname();

  return (
    <aside className="h-screen w-[240px] hidden md:flex flex-col bg-surface-container-low justify-between py-8 px-4 text-sm font-medium sticky top-0">
      <div>
        <Link href="/admin" className="block mb-2">
          <h1 className="font-headline text-2xl font-black text-white tracking-tight">GradNet</h1>
        </Link>
        <p className="text-[10px] text-secondary font-bold uppercase tracking-widest mb-8 px-1">Admin Panel</p>

        <nav className="space-y-1">
          {adminItems.map((item) => {
            const isActive = item.exact
              ? pathname === item.href
              : pathname.startsWith(item.href);
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

      <div className="pt-4 border-t border-white/5">
        <Link
          href="/feed"
          className="flex items-center gap-3 px-4 py-2 text-on-surface-variant hover:text-primary text-xs uppercase tracking-widest transition-colors"
        >
          <span className="material-symbols-outlined text-sm">arrow_back</span>
          Back to App
        </Link>
      </div>
    </aside>
  );
}
