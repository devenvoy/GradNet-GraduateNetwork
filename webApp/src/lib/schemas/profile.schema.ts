import { z } from 'zod';

export const profileSchema = z.object({
  bio: z.string().max(500).optional(),
  headline: z.string().max(200).optional(),
  location: z.string().optional(),
  website: z.string().url().optional().or(z.literal('')),
  phone: z.string().optional(),
  skills: z.array(z.string()).optional(),
});

export type ProfileSchema = z.infer<typeof profileSchema>;
