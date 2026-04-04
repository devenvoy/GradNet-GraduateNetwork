'use client';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { eventsApi } from '@/lib/api/events';
import { toast } from 'sonner';
import type { EventFilterRequest, EventCreateRequest } from '@/types';

export const EVENTS_QUERY_KEY = ['events'];

export function useEvents(filters: EventFilterRequest = {}) {
  return useQuery({
    queryKey: [...EVENTS_QUERY_KEY, filters],
    queryFn: () => eventsApi.filter(filters),
    staleTime: 60_000,
  });
}

export function useEventsByDate(startDate: string, endDate: string) {
  return useQuery({
    queryKey: [...EVENTS_QUERY_KEY, 'byDate', startDate, endDate],
    queryFn: () => eventsApi.getByDate({ startDate, endDate }),
    enabled: !!startDate && !!endDate,
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
