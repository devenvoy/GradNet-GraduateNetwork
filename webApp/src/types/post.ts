export interface PostResponse {
  id: string;
  description: string | null;
  location: string | null;
  images: string[] | null;
  userId: string;
  userName: string | null;
  userRole: string | null;
  userAvatar: string | null;
  likeCount: number;
  isLiked: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface PostCreateRequest {
  description?: string;
  location?: string;
  images?: string[];
}
