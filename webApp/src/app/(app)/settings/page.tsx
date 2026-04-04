'use client';

import { useLogout, useChangePassword } from '@/hooks/useAuth';
import { useAuthStore } from '@/stores/authStore';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { changePasswordSchema, type ChangePasswordSchema } from '@/lib/schemas/auth.schema';

export default function SettingsPage() {
  const { user } = useAuthStore();
  const logout = useLogout();
  const changePassword = useChangePassword();
  const { register, handleSubmit, formState: { errors }, reset } = useForm<ChangePasswordSchema>({
    resolver: zodResolver(changePasswordSchema),
  });

  return (
    <div className="max-w-2xl mx-auto space-y-8">
      <h1 className="font-headline text-3xl font-bold text-white">Settings</h1>

      {/* Account */}
      <section className="bg-surface-container rounded-2xl p-6">
        <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-4">Account</h2>
        <div className="space-y-3">
          <div className="flex justify-between items-center py-2">
            <span className="text-sm text-on-surface">Email</span>
            <span className="text-sm text-on-surface-variant">{user?.email}</span>
          </div>
          <div className="flex justify-between items-center py-2">
            <span className="text-sm text-on-surface">Account Type</span>
            <span className="text-sm text-on-surface-variant">{user?.accountType}</span>
          </div>
        </div>
      </section>

      {/* Change Password */}
      <section className="bg-surface-container rounded-2xl p-6">
        <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-4">Change Password</h2>
        <form onSubmit={handleSubmit((d) => { changePassword.mutate(d); reset(); })} className="space-y-4">
          <input {...register('currentPassword')} type="password" placeholder="Current password" className="w-full bg-surface-container-lowest rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none text-sm" />
          {errors.currentPassword && <p className="text-error text-xs">{errors.currentPassword.message}</p>}
          <input {...register('newPassword')} type="password" placeholder="New password" className="w-full bg-surface-container-lowest rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none text-sm" />
          {errors.newPassword && <p className="text-error text-xs">{errors.newPassword.message}</p>}
          <input {...register('confirmPassword')} type="password" placeholder="Confirm new password" className="w-full bg-surface-container-lowest rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none text-sm" />
          {errors.confirmPassword && <p className="text-error text-xs">{errors.confirmPassword.message}</p>}
          <button type="submit" disabled={changePassword.isPending} className="btn-gradient px-6 py-2 rounded-xl text-sm disabled:opacity-50">
            {changePassword.isPending ? 'Updating...' : 'Update Password'}
          </button>
        </form>
      </section>

      {/* Sign Out */}
      <section className="bg-surface-container rounded-2xl p-6">
        <h2 className="text-xs font-bold tracking-widest uppercase text-on-surface-variant mb-4">Session</h2>
        <button onClick={() => logout.mutate()} className="px-6 py-3 rounded-xl text-sm font-bold bg-error-container/20 text-error hover:bg-error-container/40 transition-colors">
          Sign Out
        </button>
      </section>
    </div>
  );
}
