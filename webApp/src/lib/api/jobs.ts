import { api } from './client';
import type { ApiResponse, PagedResponse, JobResponse, JobCreateRequest, SaveJobRequest } from '@/types';

export const jobsApi = {
  /** POST /api/v1/jobs/create */
  create: (data: JobCreateRequest) =>
    api.post<ApiResponse<JobResponse>>('/api/v1/jobs/create', data).then(r => r.data),

  /** GET /api/v1/jobs?page=&perPage=&work_mode[]=&search= */
  getAll: (params: { page?: number; perPage?: number; work_mode?: string[]; search?: string }) =>
    api.get<ApiResponse<PagedResponse<JobResponse>>>('/api/v1/jobs', { params }).then(r => r.data),

  /** GET /api/v1/jobs/{jobId} */
  getById: (jobId: string) =>
    api.get<ApiResponse<JobResponse>>(`/api/v1/jobs/${jobId}`).then(r => r.data),

  /** PUT /api/v1/jobs/{jobId} */
  update: (jobId: string, data: JobCreateRequest) =>
    api.put<ApiResponse<JobResponse>>(`/api/v1/jobs/${jobId}`, data).then(r => r.data),

  /** DELETE /api/v1/jobs/{jobId} */
  delete: (jobId: string) =>
    api.delete<ApiResponse<void>>(`/api/v1/jobs/${jobId}`).then(r => r.data),

  /** POST /api/v1/job/save */
  save: (data: SaveJobRequest) =>
    api.post<ApiResponse<unknown>>('/api/v1/job/save', data).then(r => r.data),

  /** GET /api/v1/job/saved?page=&perPage= */
  getSaved: (params: { page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<JobResponse>>>('/api/v1/job/saved', { params }).then(r => r.data),
};
