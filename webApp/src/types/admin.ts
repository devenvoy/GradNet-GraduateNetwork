export interface DataEntryRequest {
  verifyId: string;
  collegeName?: string;
  verifyType?: string;
  data?: string;
}

export interface DataUpdateRequest {
  collegeName?: string;
  verifyType?: string;
  data?: string;
}

export interface FeedbackRequest {
  feedback: string;
}

export interface FeedbackResponse {
  id: string;
  feedback: string;
  userId: string;
  createdAt: string;
  updatedAt: string;
}

export interface LostFoundResponse {
  id: string;
  description: string | null;
  images: string[] | null;
  userId: string;
  createdAt: string;
  updatedAt: string;
}

export interface LostFoundCreateRequest {
  description?: string;
  images?: string[];
}

export interface MediaUploadResponse {
  id: string;
  fileName: string;
  fileUrl: string;
  fileType: string;
  imgType: string;
  createdAt: string;
}
