export interface ProfileResponse {
  id: string;
  userId: string;
  bio: string | null;
  headline: string | null;
  location: string | null;
  website: string | null;
  phone: string | null;
  skills: string[] | null;
  education: EducationEntry[] | null;
  experience: ExperienceEntry[] | null;
  avatarUrl: string | null;
  coverUrl: string | null;
  socialLinks: SocialLinks | null;
  createdAt: string;
  updatedAt: string;
}

export interface EducationEntry {
  institution: string;
  degree: string;
  field: string;
  startYear: string;
  endYear: string | null;
  current: boolean;
}

export interface ExperienceEntry {
  company: string;
  title: string;
  location: string | null;
  startDate: string;
  endDate: string | null;
  current: boolean;
  description: string | null;
}

export interface SocialLinks {
  linkedin?: string;
  github?: string;
  twitter?: string;
  portfolio?: string;
}

export interface ProfileCreateUpdateRequest {
  bio?: string;
  headline?: string;
  location?: string;
  website?: string;
  phone?: string;
  skills?: string[];
  education?: EducationEntry[];
  experience?: ExperienceEntry[];
  socialLinks?: SocialLinks;
}
