'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { cn } from '@/lib/utils';

const mobileNavItems = [
  { href: '/feed', icon: 'dynamic_feed', label: 'Feed' },
  { href: '/jobs', icon: 'work', label: 'Jobs' },
  { href: '/events', icon: 'event', label: 'Events' },
  { href: '/profile', icon: 'person', label: 'Profile' },
];

export default function MobileNav() {
  const pathname = usePathname();

  return (
    <nav className="fixed bottom-0 left-0 w-full z-50 flex justify-around items-center px-6 pb-6 pt-3 bg-surface-container-lowest/80 backdrop-blur-2xl rounded-t-3xl md:hidden shadow-[0_-10px_40px_rgba(0,0,0,0.4)] border-t border-white/5 text-[10px] uppercase tracking-widest">
      {mobileNavItems.map((item) => {
        const isActive = pathname === item.href || pathname.startsWith(item.href + '/');
        return (
          <Link
            key={item.href}
            href={item.href}
            className={cn(
              'flex flex-col items-center justify-center gap-1 px-4 py-1 rounded-xl transition-all',
              isActive
                ? 'bg-primary-container/20 text-primary'
                : 'text-on-surface-variant'
            )}
          >
            <span className="material-symbols-outlined">{item.icon}</span>
            <span className="mt-0.5">{item.label}</span>
          </Link>
        );
      })}
    </nav>
  );
}
