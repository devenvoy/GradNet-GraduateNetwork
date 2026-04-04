'use client';
import { getInitials } from '@/lib/utils/formatters';
import { cn } from '@/lib/utils';

interface AvatarProps {
  src?: string | null;
  name?: string | null;
  size?: 'sm' | 'md' | 'lg';
  className?: string;
}

const sizes = { sm: 'w-8 h-8 text-xs', md: 'w-10 h-10 text-sm', lg: 'w-16 h-16 text-lg' };

export default function Avatar({ src, name, size = 'md', className }: AvatarProps) {
  if (src) {
    return <img src={src} alt={name || 'Avatar'} className={cn(sizes[size], 'rounded-full object-cover ring-2 ring-primary/20', className)} />;
  }
  return (
    <div className={cn(sizes[size], 'rounded-full bg-primary-container flex items-center justify-center text-white font-bold', className)}>
      {getInitials(name)}
    </div>
  );
}
