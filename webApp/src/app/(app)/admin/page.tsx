'use client';

export default function AdminDashboard() {
  const stats = [
    { label: 'Total Users', value: '—', icon: 'group', color: 'text-primary' },
    { label: 'Active Posts', value: '—', icon: 'dynamic_feed', color: 'text-secondary' },
    { label: 'Jobs Listed', value: '—', icon: 'work', color: 'text-tertiary' },
    { label: 'Events', value: '—', icon: 'event', color: 'text-primary' },
  ];

  return (
    <div>
      <h1 className="font-headline text-3xl font-bold text-white mb-8">Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        {stats.map((stat) => (
          <div key={stat.label} className="bg-surface-container rounded-2xl p-6">
            <div className="flex items-center gap-3 mb-4">
              <div className={`w-10 h-10 rounded-xl bg-surface-container-high flex items-center justify-center`}>
                <span className={`material-symbols-outlined ${stat.color}`}>{stat.icon}</span>
              </div>
              <span className="text-xs font-bold tracking-widest uppercase text-on-surface-variant">{stat.label}</span>
            </div>
            <p className="text-3xl font-headline font-bold text-white">{stat.value}</p>
          </div>
        ))}
      </div>

      <div className="bg-surface-container rounded-2xl p-8 text-center">
        <span className="material-symbols-outlined text-5xl text-on-surface-variant mb-4">construction</span>
        <h2 className="font-headline text-xl text-white mb-2">Admin Panel Coming Soon</h2>
        <p className="text-on-surface-variant text-sm">Analytics, user management, and moderation tools are under construction.</p>
      </div>
    </div>
  );
}
