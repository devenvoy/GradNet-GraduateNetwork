import { api } from './client';
import type {
  ApiResponse,
  PagedResponse,
  EventResponse,
  EventCreateRequest,
  EventUpdateRequest,
  EventFilterRequest,
  EventDateFilterRequest,
} from '@/types';

export const eventsApi = {
  /** POST /api/v1/events */
  create: (data: EventCreateRequest) =>
    api.post<ApiResponse<EventResponse>>('/api/v1/events', data).then(r => r.data),

  /** POST /api/v1/events/filter?page=&perPage= */
  filter: (data: EventFilterRequest, params?: { page?: number; perPage?: number }) =>
    api.post<ApiResponse<PagedResponse<EventResponse>>>('/api/v1/events/filter', data, { params }).then(r => r.data),

  /** POST /api/v1/events/by-date?page=&perPage= */
  getByDate: (data: EventDateFilterRequest, params?: { page?: number; perPage?: number }) =>
    api.post<ApiResponse<PagedResponse<EventResponse>>>('/api/v1/events/by-date', data, { params }).then(r => r.data),

  /** PUT /api/v1/events/{eventId} */
  update: (eventId: string, data: EventUpdateRequest) =>
    api.put<ApiResponse<EventResponse>>(`/api/v1/events/${eventId}`, data).then(r => r.data),

  /** DELETE /api/v1/events/{eventId} */
  delete: (eventId: string) =>
    api.delete<ApiResponse<void>>(`/api/v1/events/${eventId}`).then(r => r.data),
};
