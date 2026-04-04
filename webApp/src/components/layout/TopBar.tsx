'use client';

import Link from 'next/link';
import { useAuthStore } from '@/stores/authStore';
import { useLogout } from '@/hooks/useAuth';
import { getInitials } from '@/lib/utils/formatters';

export default function TopBar() {
  const { user } = useAuthStore();
  const logout = useLogout();

  return (
    <header className="sticky top-0 z-40 glass px-6 py-4 flex items-center justify-between md:justify-end gap-4">
      {/* Mobile brand */}
      <Link href="/feed" className="md:hidden">
        <span className="font-headline text-xl font-bold text-white">GradNet</span>
      </Link>

      <div className="flex items-center gap-4">
        {/* Search (desktop) */}
        <div className="hidden md:flex items-center bg-surface-container-lowest rounded-full px-4 py-2 min-w-[280px] cursor-pointer hover:bg-surface-container transition-colors">
          <span className="material-symbols-outlined text-on-surface-variant text-lg mr-2">search</span>
          <span className="text-on-surface-variant/60 text-sm">Search GradNet...</span>
          <kbd className="ml-auto text-[10px] text-on-surface-variant bg-surface-container px-2 py-0.5 rounded">⌘K</kbd>
        </div>

        {/* Notifications */}
        <button className="relative text-on-surface-variant hover:text-primary transition-colors p-2 rounded-lg hover:bg-surface-container">
          <span className="material-symbols-outlined">notifications</span>
          <span className="absolute top-1 right-1 w-2 h-2 bg-secondary rounded-full" />
        </button>

        {/* User avatar */}
        <button
          onClick={() => logout.mutate()}
          className="flex items-center gap-2 group"
        >
          {user?.avatarUrl ? (
            <img src={user.avatarUrl} alt="Avatar" className="w-8 h-8 rounded-full ring-1 ring-white/10" />
          ) : (
            <div className="w-8 h-8 rounded-full bg-primary-container flex items-center justify-center text-white text-xs font-bold">
              {getInitials(`${user?.firstName ?? ''} ${user?.lastName ?? ''}`)}
            </div>
          )}
        </button>
      </div>
    </header>
  );
}
