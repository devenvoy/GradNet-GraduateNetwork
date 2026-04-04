'use client';
import { useAuthStore } from '@/stores/authStore';
import type { ReactNode } from 'react';

interface PermissionGateProps {
  require?: string;
  requireRole?: string;
  children: ReactNode;
  fallback?: ReactNode;
}

export default function PermissionGate({ require, requireRole, children, fallback = null }: PermissionGateProps) {
  const { hasPermission, hasRole } = useAuthStore();

  if (require && !hasPermission(require)) return <>{fallback}</>;
  if (requireRole && !hasRole(requireRole)) return <>{fallback}</>;

  return <>{children}</>;
}
