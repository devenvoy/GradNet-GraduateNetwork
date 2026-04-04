'use client';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { forgotPasswordSchema, type ForgotPasswordSchema } from '@/lib/schemas/auth.schema';
import { useForgotPassword } from '@/hooks/useAuth';
import Link from 'next/link';

export default function ForgotPasswordPage() {
  const forgotPassword = useForgotPassword();
  const { register, handleSubmit, formState: { errors } } = useForm<ForgotPasswordSchema>({
    resolver: zodResolver(forgotPasswordSchema),
  });

  return (
    <main className="min-h-screen flex items-center justify-center p-6 bg-background campus-mesh">
      <div className="w-full max-w-md bg-surface-container rounded-2xl p-8">
        <div className="text-center mb-8">
          <div className="w-16 h-16 mx-auto mb-4 rounded-2xl bg-secondary/10 flex items-center justify-center">
            <span className="material-symbols-outlined text-3xl text-secondary">lock_reset</span>
          </div>
          <h1 className="font-headline text-2xl font-bold text-white">Reset Access</h1>
          <p className="text-on-surface-variant text-sm mt-2">Enter your email and we&apos;ll send a reset link.</p>
        </div>
        <form onSubmit={handleSubmit((d) => forgotPassword.mutate(d.email))} className="space-y-4">
          <div className="relative">
            <input {...register('email')} type="email" placeholder="Email address" className="w-full bg-surface-container-lowest border-0 rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none" />
            {errors.email && <p className="text-error text-xs mt-1">{errors.email.message}</p>}
          </div>
          <button type="submit" disabled={forgotPassword.isPending} className="w-full btn-gradient py-4 rounded-xl disabled:opacity-50">
            {forgotPassword.isPending ? 'Sending...' : 'SEND RESET LINK'}
          </button>
        </form>
        <p className="mt-6 text-center text-on-surface-variant text-sm">
          <Link href="/login" className="text-primary font-bold hover:underline">Back to login</Link>
        </p>
      </div>
    </main>
  );
}
