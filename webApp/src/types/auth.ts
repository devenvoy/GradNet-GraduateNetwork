export type AccountType = 'STUDENT' | 'FACULTY' | 'ORGANIZATION' | 'COMPANY' | 'INDIVIDUAL';

export interface DeviceInfo {
  deviceId?: string;
  deviceName?: string;
  os?: string;
  appVersion?: string;
  pushToken?: string;
}

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

export interface SignupRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  accountType?: AccountType;
  accountName?: string;
  deviceInfo?: DeviceInfo;
}

export interface LoginRequest {
  email: string;
  password: string;
  deviceInfo?: DeviceInfo;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
}

export interface SetPasswordRequest {
  password: string;
}

export interface ResetPasswordRequest {
  password: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface VerifyUserRequest {
  verifyId: string;
}

export interface VerifyOtpRequest {
  otp: string;
  email: string;
}
