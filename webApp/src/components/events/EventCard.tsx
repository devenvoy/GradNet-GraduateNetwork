'use client';
import { formatDate } from '@/lib/utils/formatters';
import type { EventResponse } from '@/types';

export default function EventCard({ event }: { event: EventResponse }) {
  const dateStr = formatDate(event.date, 'MMM dd');
  const [month, day] = dateStr.split(' ');

  return (
    <article className="bg-surface-container rounded-2xl overflow-hidden hover:bg-surface-container-highest transition-colors group cursor-pointer">
      {event.eventPic && (
        <div className="relative h-40 overflow-hidden">
          <img src={event.eventPic} alt={event.eventTitle} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
          <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent" />
          <span className="absolute bottom-3 left-3 text-[10px] font-black bg-secondary text-on-secondary px-2 py-0.5 rounded uppercase">
            {event.eventType}
          </span>
        </div>
      )}

      <div className="p-6">
        <div className="flex items-start gap-4">
          <div className="bg-primary/10 text-primary w-14 h-14 rounded-lg flex flex-col items-center justify-center border border-primary/20 shrink-0">
            <span className="text-xs font-black uppercase">{month}</span>
            <span className="text-lg font-black">{day}</span>
          </div>
          <div className="min-w-0">
            <h3 className="font-bold text-white group-hover:text-primary transition-colors truncate">{event.eventTitle}</h3>
            <p className="text-xs text-on-surface-variant mt-1">{event.venue} • {event.time}</p>
            {event.description && (
              <p className="text-sm text-on-surface-variant mt-2 line-clamp-2">{event.description}</p>
            )}
          </div>
        </div>

        {event.registerLink && (
          <a
            href={event.registerLink}
            target="_blank"
            rel="noopener noreferrer"
            onClick={(e) => e.stopPropagation()}
            className="mt-4 inline-flex items-center gap-1 text-secondary text-xs font-bold hover:text-secondary-container transition-colors"
          >
            Register Now
            <span className="material-symbols-outlined text-sm">arrow_forward</span>
          </a>
        )}
      </div>
    </article>
  );
}
