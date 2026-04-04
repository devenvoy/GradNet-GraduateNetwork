import { z } from 'zod';

export const jobCreateSchema = z.object({
  jobTitle: z.string().min(1, 'Job title required'),
  companyName: z.string().min(1, 'Company name required'),
  workMode: z.enum(['REMOTE', 'HYBRID', 'ON_SITE']),
  jobLocation: z.string().optional(),
  jobOverview: z.string().optional(),
  salary: z.string().optional(),
  skills: z.array(z.string()).optional(),
  requirements: z.array(z.string()).optional(),
  benefits: z.array(z.string()).optional(),
  applyLink: z.string().url('Must be a valid URL').optional().or(z.literal('')),
  companyLogo: z.string().optional(),
});

export type JobCreateSchema = z.infer<typeof jobCreateSchema>;
