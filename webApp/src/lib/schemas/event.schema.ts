import { z } from 'zod';

export const eventCreateSchema = z.object({
  eventTitle: z.string().min(1, 'Event title required'),
  eventName: z.string().min(1, 'Event name required'),
  date: z.string().min(1, 'Date required'),
  time: z.string().min(1, 'Time required'),
  description: z.string().min(1, 'Description required'),
  venue: z.string().min(1, 'Venue required'),
  registerLink: z.string().url().optional().or(z.literal('')),
  guestNames: z.string().optional(),
  forWhom: z.string().optional(),
  remarks: z.string().optional(),
  contactUs: z.string().min(1, 'Contact info required'),
  eventPic: z.string().optional(),
  eventType: z.string().min(1, 'Event type required'),
});

export type EventCreateSchema = z.infer<typeof eventCreateSchema>;
