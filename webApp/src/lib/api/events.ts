import { api } from './client';
import type { ApiResponse, EventResponse, EventCreateRequest, EventFilterRequest } from '@/types';

export const eventsApi = {
  create: (data: EventCreateRequest) =>
    api.post<ApiResponse<EventResponse>>('/api/v1/events', data).then(r => r.data),

  filter: (data: EventFilterRequest) =>
    api.post<ApiResponse<EventResponse[]>>('/api/v1/events/filter', data).then(r => r.data),

  getByDate: (data: { startDate: string; endDate: string }) =>
    api.post<ApiResponse<EventResponse[]>>('/api/v1/events/by-date', data).then(r => r.data),

  update: (eventId: string, data: Partial<EventCreateRequest>) =>
    api.put<ApiResponse<EventResponse>>(`/api/v1/events/${eventId}`, data).then(r => r.data),

  delete: (eventId: string) =>
    api.delete(`/api/v1/events/${eventId}`),
};
