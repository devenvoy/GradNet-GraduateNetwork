'use client';

import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { loginSchema, type LoginSchema } from '@/lib/schemas/auth.schema';
import { useLogin } from '@/hooks/useAuth';
import Link from 'next/link';

export default function LoginPage() {
  const login = useLogin();
  const { register, handleSubmit, formState: { errors } } = useForm<LoginSchema>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = (data: LoginSchema) => login.mutate(data);

  return (
    <main className="flex min-h-screen">
      {/* Left Side: Brand & Visual */}
      <section className="hidden lg:flex lg:w-1/2 relative bg-surface-container-lowest campus-mesh overflow-hidden items-center justify-center p-12">
        <div className="absolute inset-0 z-0 opacity-10">
          <div className="w-full h-full bg-gradient-to-br from-primary-container/20 to-transparent" />
        </div>
        <div className="relative z-10 w-full max-w-lg">
          <div className="inline-flex items-center gap-2 px-4 py-2 bg-primary-container/20 text-primary-fixed-dim rounded-full mb-8 border border-primary/20">
            <span className="material-symbols-outlined text-sm">school</span>
            <span className="text-xs font-bold tracking-widest uppercase">Academic Excellence</span>
          </div>
          <h1 className="font-headline text-6xl font-black text-white leading-tight mb-6">
            Where Scholars <br />
            <span className="text-secondary italic">Converge.</span>
          </h1>
          <div className="space-y-6 mt-12">
            <div className="glass p-5 rounded-xl shadow-ambient hover:-translate-y-1 transition-transform">
              <div className="flex items-center gap-3 mb-3">
                <div className="w-10 h-10 rounded-full bg-primary-container/40 flex items-center justify-center text-primary text-sm font-bold">AT</div>
                <div>
                  <p className="text-sm font-bold text-white">Dr. Aris Thorne</p>
                  <p className="text-[10px] text-on-surface-variant uppercase tracking-wider">Physics Department</p>
                </div>
                <span className="ml-auto material-symbols-outlined text-primary">verified</span>
              </div>
              <p className="text-sm leading-relaxed text-on-surface-variant">Just published my latest findings on quantum decoherence. The peer review process was rigorous but rewarding.</p>
            </div>
            <div className="glass p-5 rounded-xl shadow-ambient ml-12 border-l-4 border-secondary hover:-translate-y-1 transition-transform">
              <span className="bg-secondary-container/20 text-secondary px-2 py-1 rounded text-[10px] font-bold uppercase">New Opportunity</span>
              <h3 className="text-white font-bold mt-2">Senior Research Lead</h3>
              <p className="text-xs text-on-surface-variant">Oxford Global Initiative • Permanent</p>
            </div>
          </div>
        </div>
      </section>

      {/* Right Side: Auth Form */}
      <section className="w-full lg:w-1/2 flex items-center justify-center p-6 sm:p-12 bg-background">
        <div className="w-full max-w-md">
          <div className="mb-12 text-center lg:text-left">
            <div className="flex items-center justify-center lg:justify-start gap-3 mb-2">
              <div className="w-10 h-10 btn-gradient rounded-xl flex items-center justify-center text-white">
                <span className="material-symbols-outlined text-2xl" style={{ fontVariationSettings: "'FILL' 1" }}>auto_stories</span>
              </div>
              <span className="font-headline text-3xl font-black text-white tracking-tight">GradNet</span>
            </div>
            <p className="text-on-surface-variant font-medium">Welcome back to the excellence network.</p>
          </div>

          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div className="space-y-4">
              <div className="relative">
                <input
                  {...register('email')}
                  type="email"
                  id="login-email"
                  placeholder="Email address"
                  className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent transition-all outline-none"
                />
                <label htmlFor="login-email" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">
                  Institutional Email
                </label>
                {errors.email && <p className="text-error text-xs mt-1">{errors.email.message}</p>}
              </div>

              <div className="relative">
                <input
                  {...register('password')}
                  type="password"
                  id="login-password"
                  placeholder="Password"
                  className="peer w-full bg-surface-container-lowest border-0 rounded-xl px-4 pt-6 pb-2 text-on-surface focus:ring-1 focus:ring-primary placeholder-transparent transition-all outline-none"
                />
                <label htmlFor="login-password" className="absolute left-4 top-2 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant transition-all peer-placeholder-shown:top-4 peer-placeholder-shown:text-sm peer-placeholder-shown:normal-case peer-placeholder-shown:font-medium peer-focus:top-2 peer-focus:text-[10px] peer-focus:uppercase peer-focus:tracking-widest peer-focus:text-primary">
                  Security Password
                </label>
                {errors.password && <p className="text-error text-xs mt-1">{errors.password.message}</p>}
              </div>
            </div>

            <div className="flex items-center justify-between text-sm">
              <label className="flex items-center gap-2 cursor-pointer text-on-surface-variant">
                <input type="checkbox" className="rounded border-outline-variant" />
                <span>Stay logged in</span>
              </label>
              <Link href="/forgot-password" className="text-primary font-bold hover:underline decoration-2 underline-offset-4">Reset Access?</Link>
            </div>

            <button
              type="submit"
              disabled={login.isPending}
              className="w-full btn-gradient py-4 rounded-xl shadow-lg shadow-primary-container/20 flex items-center justify-center gap-2 disabled:opacity-50"
            >
              {login.isPending ? 'Authenticating...' : 'AUTHENTICATE'}
              <span className="material-symbols-outlined text-sm">login</span>
            </button>
          </form>

          <p className="mt-12 text-center text-on-surface-variant text-sm">
            New to the community?{' '}
            <Link href="/signup" className="text-secondary font-extrabold hover:text-secondary-container transition-colors ml-1 italic font-headline text-lg">
              Join the Registry
            </Link>
          </p>
        </div>
      </section>
    </main>
  );
}
