'use client';
import type { ReactNode } from 'react';

interface FeatureFlagProps {
  name: string;
  children: ReactNode;
  fallback?: ReactNode;
}

// Simple feature flag — expand to API-backed flags later
const enabledFlags = new Set<string>([]);

export default function FeatureFlag({ name, children, fallback = null }: FeatureFlagProps) {
  if (!enabledFlags.has(name)) return <>{fallback}</>;
  return <>{children}</>;
}
