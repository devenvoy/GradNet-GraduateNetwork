'use client';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { resetPasswordSchema, type ResetPasswordSchema } from '@/lib/schemas/auth.schema';
import { useResetPassword } from '@/hooks/useAuth';
import { useParams } from 'next/navigation';

export default function ResetPasswordPage() {
  const { token } = useParams<{ token: string }>();
  const resetPassword = useResetPassword();
  const { register, handleSubmit, formState: { errors } } = useForm<ResetPasswordSchema>({
    resolver: zodResolver(resetPasswordSchema),
  });

  return (
    <main className="min-h-screen flex items-center justify-center p-6 bg-background campus-mesh">
      <div className="w-full max-w-md bg-surface-container rounded-2xl p-8">
        <div className="text-center mb-8">
          <h1 className="font-headline text-2xl font-bold text-white">Set New Password</h1>
          <p className="text-on-surface-variant text-sm mt-2">Enter your new password below.</p>
        </div>
        <form onSubmit={handleSubmit((d) => resetPassword.mutate({ token, password: d.password }))} className="space-y-4">
          <input {...register('password')} type="password" placeholder="New password" className="w-full bg-surface-container-lowest border-0 rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none" />
          {errors.password && <p className="text-error text-xs mt-1">{errors.password.message}</p>}
          <input {...register('confirmPassword')} type="password" placeholder="Confirm password" className="w-full bg-surface-container-lowest border-0 rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none" />
          {errors.confirmPassword && <p className="text-error text-xs mt-1">{errors.confirmPassword.message}</p>}
          <button type="submit" disabled={resetPassword.isPending} className="w-full btn-gradient py-4 rounded-xl disabled:opacity-50">
            {resetPassword.isPending ? 'Resetting...' : 'RESET PASSWORD'}
          </button>
        </form>
      </div>
    </main>
  );
}
