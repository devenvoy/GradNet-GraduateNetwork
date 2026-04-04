import { cn } from '@/lib/utils';

const roleColors: Record<string, string> = {
  Faculty: 'bg-tertiary-container/30 text-tertiary',
  Alumni: 'bg-primary-container/20 text-primary',
  Student: 'bg-secondary-container/20 text-secondary',
  Admin: 'bg-error-container/20 text-error',
  ADMIN: 'bg-error-container/20 text-error',
  ACCOUNT_OWNER: 'bg-error-container/20 text-error',
};

export default function RoleBadge({ role, className }: { role: string | null; className?: string }) {
  if (!role) return null;
  const colors = roleColors[role] || 'bg-surface-container-high text-on-surface-variant';
  return (
    <span className={cn('px-2 py-0.5 rounded text-[10px] font-bold tracking-wider uppercase', colors, className)}>
      {role}
    </span>
  );
}
