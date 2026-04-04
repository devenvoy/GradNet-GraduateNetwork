export function PostSkeleton() {
  return (
    <div className="bg-surface-container rounded-2xl p-6 animate-pulse">
      <div className="flex items-center gap-3 mb-4">
        <div className="w-10 h-10 rounded-full bg-surface-container-high" />
        <div className="space-y-2">
          <div className="h-3 w-28 bg-surface-container-high rounded" />
          <div className="h-2 w-16 bg-surface-container-high rounded" />
        </div>
      </div>
      <div className="space-y-2 mb-4">
        <div className="h-3 w-full bg-surface-container-high rounded" />
        <div className="h-3 w-3/4 bg-surface-container-high rounded" />
      </div>
      <div className="h-48 w-full bg-surface-container-high rounded-xl" />
    </div>
  );
}

export function JobSkeleton() {
  return (
    <div className="bg-surface-container rounded-2xl p-6 animate-pulse">
      <div className="flex items-start gap-4 mb-4">
        <div className="w-12 h-12 rounded-xl bg-surface-container-high" />
        <div className="flex-1 space-y-2">
          <div className="h-4 w-40 bg-surface-container-high rounded" />
          <div className="h-3 w-24 bg-surface-container-high rounded" />
        </div>
      </div>
      <div className="flex gap-2 mt-4">
        <div className="h-6 w-16 bg-surface-container-high rounded-full" />
        <div className="h-6 w-20 bg-surface-container-high rounded-full" />
      </div>
    </div>
  );
}

export function CardSkeleton() {
  return (
    <div className="bg-surface-container rounded-2xl p-6 animate-pulse">
      <div className="h-40 w-full bg-surface-container-high rounded-xl mb-4" />
      <div className="space-y-2">
        <div className="h-4 w-3/4 bg-surface-container-high rounded" />
        <div className="h-3 w-1/2 bg-surface-container-high rounded" />
      </div>
    </div>
  );
}
