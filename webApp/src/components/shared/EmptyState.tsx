export default function EmptyState({ icon, title, description }: { icon: string; title: string; description: string }) {
  return (
    <div className="flex flex-col items-center justify-center py-20 text-center">
      <div className="w-20 h-20 rounded-2xl bg-surface-container flex items-center justify-center mb-6">
        <span className="material-symbols-outlined text-4xl text-on-surface-variant">{icon}</span>
      </div>
      <h3 className="font-headline text-xl text-white mb-2">{title}</h3>
      <p className="text-on-surface-variant text-sm max-w-md">{description}</p>
    </div>
  );
}
