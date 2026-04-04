export interface DataEntryRequest {
  type: string;
  name: string;
  email?: string;
  details?: Record<string, unknown>;
}

export interface DataUpdateRequest {
  name?: string;
  email?: string;
  details?: Record<string, unknown>;
}

export interface VerifyDataResponse {
  id: string;
  type: string;
  name: string;
  email: string | null;
  details: Record<string, unknown> | null;
  createdAt: string;
  updatedAt: string;
}

export interface FeedbackResponse {
  id: string;
  userId: string;
  userName: string | null;
  content: string;
  category: string | null;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface LostFoundResponse {
  id: string;
  title: string;
  description: string;
  category: string;
  location: string | null;
  images: string[] | null;
  status: string;
  userId: string;
  userName: string | null;
  userAvatar: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface LostFoundCreateRequest {
  title: string;
  description: string;
  category: string;
  location?: string;
  images?: string[];
}
