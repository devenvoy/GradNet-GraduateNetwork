export interface JobResponse {
  id: string;
  jobTitle: string | null;
  companyName: string | null;
  workMode: string | null;
  jobLocation: string | null;
  jobOverview: string | null;
  salary: string | null;
  skills: string[] | null;
  requirements: string[] | null;
  benefits: string[] | null;
  applyLink: string | null;
  companyLogo: string | null;
  userId: string;
  createdAt: string;
  updatedAt: string;
  saved: boolean;
}

export interface JobCreateRequest {
  jobTitle?: string;
  companyName?: string;
  workMode?: string;
  jobLocation?: string;
  jobOverview?: string;
  salary?: string;
  skills?: string[];
  requirements?: string[];
  benefits?: string[];
  applyLink?: string;
  companyLogo?: string;
}

export interface SaveJobRequest {
  jobId: string;
}
