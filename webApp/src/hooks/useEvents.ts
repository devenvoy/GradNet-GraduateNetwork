'use client';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { eventsApi } from '@/lib/api/events';
import { toast } from 'sonner';
import type { EventCreateRequest, EventUpdateRequest, EventFilterRequest, EventDateFilterRequest } from '@/types';

export const EVENTS_QUERY_KEY = ['events'];

export function useEvents(filters: EventFilterRequest = {}, pagination?: { page?: number; perPage?: number }) {
  return useQuery({
    queryKey: [...EVENTS_QUERY_KEY, filters, pagination],
    queryFn: () => eventsApi.filter(filters, pagination),
    staleTime: 60_000,
  });
}

export function useEventsByDate(eventDate: string, pagination?: { page?: number; perPage?: number }) {
  return useQuery({
    queryKey: [...EVENTS_QUERY_KEY, 'byDate', eventDate, pagination],
    queryFn: () => eventsApi.getByDate({ eventDate } as EventDateFilterRequest, pagination),
    enabled: !!eventDate,
  });
}

export function useCreateEvent() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: EventCreateRequest) => eventsApi.create(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: EVENTS_QUERY_KEY });
      toast.success('Event created!');
    },
    onError: () => toast.error('Failed to create event.'),
  });
}

export function useUpdateEvent() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ eventId, data }: { eventId: string; data: EventUpdateRequest }) =>
      eventsApi.update(eventId, data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: EVENTS_QUERY_KEY });
      toast.success('Event updated!');
    },
    onError: () => toast.error('Failed to update event.'),
  });
}

export function useDeleteEvent() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: eventsApi.delete,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: EVENTS_QUERY_KEY });
      toast.success('Event deleted.');
    },
    onError: () => toast.error('Failed to delete event.'),
  });
}
