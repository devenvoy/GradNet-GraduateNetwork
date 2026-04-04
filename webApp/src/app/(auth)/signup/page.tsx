'use client';

import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { signupSchema, type SignupSchema } from '@/lib/schemas/auth.schema';
import { useSignup } from '@/hooks/useAuth';
import Link from 'next/link';

export default function SignupPage() {
  const signup = useSignup();
  const { register, handleSubmit, formState: { errors }, watch } = useForm<SignupSchema>({
    resolver: zodResolver(signupSchema),
    defaultValues: { accountType: 'INDIVIDUAL' },
  });

  const accountType = watch('accountType');
  const onSubmit = (data: SignupSchema) => signup.mutate(data);

  return (
    <main className="min-h-screen flex items-center justify-center p-6 bg-background campus-mesh">
      <div className="w-full max-w-lg">
        <div className="text-center mb-10">
          <div className="inline-flex items-center gap-3 mb-4">
            <div className="w-10 h-10 btn-gradient rounded-xl flex items-center justify-center text-white">
              <span className="material-symbols-outlined text-2xl" style={{ fontVariationSettings: "'FILL' 1" }}>auto_stories</span>
            </div>
            <span className="font-headline text-3xl font-black text-white">GradNet</span>
          </div>
          <p className="text-on-surface-variant">Join the excellence network. Start your journey.</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 bg-surface-container rounded-2xl p-8">
          <div className="grid grid-cols-2 gap-4">
            <div className="relative">
              <input {...register('firstName')} type="text" id="signup-first" placeholder="First name" className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent outline-none" />
              <label htmlFor="signup-first" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">First Name</label>
              {errors.firstName && <p className="text-error text-xs mt-1">{errors.firstName.message}</p>}
            </div>
            <div className="relative">
              <input {...register('lastName')} type="text" id="signup-last" placeholder="Last name" className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent outline-none" />
              <label htmlFor="signup-last" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">Last Name</label>
              {errors.lastName && <p className="text-error text-xs mt-1">{errors.lastName.message}</p>}
            </div>
          </div>

          <div className="relative">
            <input {...register('email')} type="email" id="signup-email" placeholder="Email" className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent outline-none" />
            <label htmlFor="signup-email" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">Institutional Email</label>
            {errors.email && <p className="text-error text-xs mt-1">{errors.email.message}</p>}
          </div>

          <div className="relative">
            <input {...register('password')} type="password" id="signup-pass" placeholder="Password" className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent outline-none" />
            <label htmlFor="signup-pass" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">Security Password</label>
            {errors.password && <p className="text-error text-xs mt-1">{errors.password.message}</p>}
          </div>

          <div>
            <p className="text-[10px] font-bold uppercase tracking-widest text-on-surface-variant mb-2">Account Type</p>
            <div className="flex gap-2">
              {(['INDIVIDUAL', 'ORGANIZATION', 'INSTITUTION'] as const).map((type) => (
                <label key={type} className={`flex-1 text-center px-3 py-2 rounded-xl cursor-pointer text-xs font-bold transition-all ${accountType === type ? 'btn-gradient' : 'bg-surface-container-lowest text-on-surface-variant hover:bg-surface-container-high'}`}>
                  <input {...register('accountType')} type="radio" value={type} className="hidden" />
                  {type.charAt(0) + type.slice(1).toLowerCase()}
                </label>
              ))}
            </div>
          </div>

          {accountType !== 'INDIVIDUAL' && (
            <div className="relative">
              <input {...register('accountName')} type="text" id="signup-org" placeholder="Organization name" className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent outline-none" />
              <label htmlFor="signup-org" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">Organization Name</label>
            </div>
          )}

          <button type="submit" disabled={signup.isPending} className="w-full btn-gradient py-4 rounded-xl shadow-lg shadow-primary-container/20 disabled:opacity-50 mt-4">
            {signup.isPending ? 'Creating Account...' : 'JOIN THE REGISTRY'}
          </button>
        </form>

        <p className="mt-8 text-center text-on-surface-variant text-sm">
          Already have an account? <Link href="/login" className="text-primary font-bold hover:underline">Sign in</Link>
        </p>
      </div>
    </main>
  );
}
