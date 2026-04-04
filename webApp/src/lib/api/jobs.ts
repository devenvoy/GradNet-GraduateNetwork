import { api } from './client';
import type { ApiResponse, PagedResponse, JobResponse, JobCreateRequest } from '@/types';

export const jobsApi = {
  create: (data: JobCreateRequest) =>
    api.post<ApiResponse<JobResponse>>('/api/v1/jobs/create', data).then(r => r.data),

  getAll: (params: { page?: number; perPage?: number; work_mode?: string[]; search?: string }) =>
    api.get<ApiResponse<PagedResponse<JobResponse>>>('/api/v1/jobs', { params }).then(r => r.data),

  getById: (jobId: string) =>
    api.get<ApiResponse<JobResponse>>(`/api/v1/jobs/${jobId}`).then(r => r.data),

  update: (jobId: string, data: Partial<JobCreateRequest>) =>
    api.put<ApiResponse<JobResponse>>(`/api/v1/jobs/${jobId}`, data).then(r => r.data),

  delete: (jobId: string) =>
    api.delete(`/api/v1/jobs/${jobId}`),

  save: (jobId: string) =>
    api.post('/api/v1/job/save', { jobId }).then(r => r.data),

  getSaved: (params: { page?: number; perPage?: number }) =>
    api.get<ApiResponse<PagedResponse<JobResponse>>>('/api/v1/job/saved', { params }).then(r => r.data),
};
