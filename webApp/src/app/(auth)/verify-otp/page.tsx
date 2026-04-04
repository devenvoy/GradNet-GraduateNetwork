'use client';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { verifyOtpSchema, type VerifyOtpSchema } from '@/lib/schemas/auth.schema';
import { useVerifyOtp } from '@/hooks/useAuth';

export default function VerifyOtpPage() {
  const verifyOtp = useVerifyOtp();
  const { register, handleSubmit, formState: { errors } } = useForm<VerifyOtpSchema>({
    resolver: zodResolver(verifyOtpSchema),
  });

  return (
    <main className="min-h-screen flex items-center justify-center p-6 bg-background campus-mesh">
      <div className="w-full max-w-md bg-surface-container rounded-2xl p-8">
        <div className="text-center mb-8">
          <div className="w-16 h-16 mx-auto mb-4 rounded-2xl bg-primary-container/20 flex items-center justify-center">
            <span className="material-symbols-outlined text-3xl text-primary">verified_user</span>
          </div>
          <h1 className="font-headline text-2xl font-bold text-white">Verify Your Email</h1>
          <p className="text-on-surface-variant text-sm mt-2">Enter the code sent to your email.</p>
        </div>
        <form onSubmit={handleSubmit((d) => verifyOtp.mutate(d))} className="space-y-4">
          <div className="relative">
            <input {...register('email')} type="email" placeholder="Email" className="w-full bg-surface-container-lowest border-0 rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none" />
            {errors.email && <p className="text-error text-xs mt-1">{errors.email.message}</p>}
          </div>
          <div className="relative">
            <input {...register('otp')} type="text" placeholder="Enter OTP" className="w-full bg-surface-container-lowest border-0 rounded-xl px-4 py-3 text-on-surface focus:ring-1 focus:ring-primary outline-none text-center text-2xl tracking-[0.5em] font-bold" maxLength={6} />
            {errors.otp && <p className="text-error text-xs mt-1">{errors.otp.message}</p>}
          </div>
          <button type="submit" disabled={verifyOtp.isPending} className="w-full btn-gradient py-4 rounded-xl disabled:opacity-50">
            {verifyOtp.isPending ? 'Verifying...' : 'VERIFY'}
          </button>
        </form>
      </div>
    </main>
  );
}
