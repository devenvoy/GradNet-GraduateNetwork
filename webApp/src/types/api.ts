export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data?: T;
}

export interface PagedResponse<T> {
  items: T[];
  page: number;
  perPage: number;
  totalCount: number;
  totalPages: number;
}

export interface ErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path?: string;
  details?: Record<string, unknown>;
}
