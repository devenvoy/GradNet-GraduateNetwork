import { NextRequest, NextResponse } from 'next/server';

const PUBLIC_PATHS = ['/login', '/signup', '/forgot-password', '/reset-password', '/verify-otp'];

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // Allow public paths
  if (PUBLIC_PATHS.some((p) => pathname.startsWith(p))) {
    return NextResponse.next();
  }

  // Allow API routes
  if (pathname.startsWith('/api/')) {
    return NextResponse.next();
  }

  // Allow static assets
  if (pathname.startsWith('/_next/') || pathname.includes('.')) {
    return NextResponse.next();
  }

  // Root path — redirect to login or feed
  if (pathname === '/') {
    const hasRefresh = request.cookies.get('__gradnet_rt');
    if (hasRefresh) {
      return NextResponse.redirect(new URL('/feed', request.url));
    }
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // Check refresh token for auth-required pages
  const hasRefresh = request.cookies.get('__gradnet_rt');
  if (!hasRefresh) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // Admin guard
  if (pathname.startsWith('/admin')) {
    const isAdmin = request.cookies.get('gradnet_is_admin');
    if (!isAdmin) {
      return NextResponse.redirect(new URL('/feed', request.url));
    }
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/((?!_next/static|_next/image|favicon.ico).*)'],
};
