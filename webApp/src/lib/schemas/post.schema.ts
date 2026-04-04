import { z } from 'zod';

export const postCreateSchema = z.object({
  description: z.string().min(1, 'Write something...').max(5000),
  location: z.string().optional(),
  images: z.array(z.string()).optional(),
});

export type PostCreateSchema = z.infer<typeof postCreateSchema>;
