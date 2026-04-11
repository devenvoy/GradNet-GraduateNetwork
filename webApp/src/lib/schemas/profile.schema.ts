import { z } from 'zod';

export const profileSchema = z.object({
  aboutSelf: z.string().max(2000).optional(),
  languages: z.array(z.string()).optional(),
  skills: z.array(z.string()).optional(),
  industryType: z.string().optional(),
  employee: z.string().optional(),
  website: z.string().url().optional().or(z.literal('')),
  department: z.string().optional(),
  designation: z.string().optional(),
  private: z.boolean().optional(),
});

export type ProfileSchema = z.infer<typeof profileSchema>;
