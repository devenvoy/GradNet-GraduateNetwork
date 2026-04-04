import Link from 'next/link';

export default function Home() {
  return (
    <main className="min-h-screen flex flex-col items-center justify-center campus-mesh relative overflow-hidden">
      {/* Floating ambient shapes */}
      <div className="absolute top-20 left-20 w-72 h-72 bg-primary-container/5 rounded-full blur-3xl" />
      <div className="absolute bottom-20 right-20 w-96 h-96 bg-secondary/5 rounded-full blur-3xl" />

      <div className="relative z-10 text-center px-6">
        <div className="inline-flex items-center gap-2 px-4 py-2 bg-primary-container/10 text-primary-fixed-dim rounded-full mb-8 border border-primary/10">
          <span className="material-symbols-outlined text-sm">school</span>
          <span className="text-xs font-bold tracking-widest uppercase">Academic Excellence Network</span>
        </div>

        <h1 className="font-headline text-7xl md:text-8xl font-black text-white mb-6 leading-tight">
          Grad<span className="text-secondary italic">Net</span>
        </h1>

        <p className="text-on-surface-variant text-lg max-w-xl mx-auto mb-12 leading-relaxed">
          Where campus life meets your career. Connect with scholars, discover opportunities, and shape the future of academic networking.
        </p>

        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <Link
            href="/signup"
            className="btn-gradient px-10 py-4 rounded-xl text-sm shadow-lg shadow-primary-container/20 inline-flex items-center justify-center gap-2"
          >
            GET STARTED
            <span className="material-symbols-outlined text-sm">arrow_forward</span>
          </Link>
          <Link
            href="/login"
            className="glass px-10 py-4 rounded-xl text-sm text-on-surface-variant font-bold hover:text-white transition-colors inline-flex items-center justify-center"
          >
            SIGN IN
          </Link>
        </div>
      </div>
    </main>
  );
}
