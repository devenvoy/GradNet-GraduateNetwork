export interface EventResponse {
  id: string;
  eventTitle: string;
  eventName: string;
  date: string;
  time: string;
  description: string;
  venue: string;
  registerLink: string | null;
  guestNames: string | null;
  forWhom: string | null;
  remarks: string | null;
  contactUs: string;
  eventPic: string | null;
  eventType: string;
  userId: string;
  createdAt: string;
  updatedAt: string;
}

export interface EventCreateRequest {
  eventTitle: string;
  eventName: string;
  date: string;
  time: string;
  description: string;
  venue: string;
  registerLink?: string;
  guestNames?: string;
  forWhom?: string;
  remarks?: string;
  contactUs: string;
  eventPic?: string;
  eventType: string;
}

export interface EventFilterRequest {
  eventType?: string;
  page?: number;
  perPage?: number;
}
