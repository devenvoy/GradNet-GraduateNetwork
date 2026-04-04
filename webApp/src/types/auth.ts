export type AccountType = 'INDIVIDUAL' | 'ORGANIZATION' | 'INSTITUTION';

export interface UserResponse {
  userId: string;
  email: string;
  firstName: string | null;
  lastName: string | null;
  displayName: string | null;
  avatarUrl: string | null;
  accountId: string;
  accountType: AccountType;
  roles: string[];
  permissions: string[];
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  refreshExpiresIn: number;
  user: UserResponse;
}
