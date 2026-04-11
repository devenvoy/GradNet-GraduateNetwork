export interface ProfileResponse {
  id: string;
  userId: string;
  firstName: string | null;
  lastName: string | null;
  displayName: string | null;
  email: string | null;
  avatarUrl: string | null;
  aboutSelf: string | null;
  languages: string[] | null;
  skills: string[] | null;
  industryType: string | null;
  employee: string | null;
  website: string | null;
  department: string | null;
  designation: string | null;
  education: Record<string, unknown>[] | null;
  experience: Record<string, unknown>[] | null;
  urls: Record<string, string>[] | null;
  createdAt: string;
  updatedAt: string;
  private: boolean;
}

export interface ProfileCreateUpdateRequest {
  aboutSelf?: string;
  languages?: string[];
  skills?: string[];
  industryType?: string;
  employee?: string;
  website?: string;
  department?: string;
  designation?: string;
  education?: Record<string, unknown>[];
  experience?: Record<string, unknown>[];
  urls?: Record<string, string>[];
  private?: boolean;
}
