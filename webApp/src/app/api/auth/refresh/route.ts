import { cookies } from 'next/headers';
import { NextRequest, NextResponse } from 'next/server';

const BACKEND_URL = process.env.BACKEND_URL || 'http://localhost:8080';

// PUT — stores refreshToken from login
export async function PUT(request: NextRequest) {
  const { refreshToken } = await request.json();
  const res = NextResponse.json({ ok: true });
  res.cookies.set('__gradnet_rt', refreshToken, {
    httpOnly: true,
    secure: process.env.NODE_ENV === 'production',
    sameSite: 'lax',
    maxAge: 60 * 60 * 24 * 30, // 30 days
    path: '/',
  });
  return res;
}

// POST — uses stored refreshToken to get new accessToken
export async function POST() {
  const cookieStore = await cookies();
  const refreshToken = cookieStore.get('__gradnet_rt')?.value;
  if (!refreshToken) {
    return NextResponse.json({ error: 'No refresh token' }, { status: 401 });
  }

  try {
    const res = await fetch(`${BACKEND_URL}/api/v1/auth/refresh-token`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ refreshToken }),
    });

    if (!res.ok) {
      const response = NextResponse.json({ error: 'Refresh failed' }, { status: 401 });
      response.cookies.delete('__gradnet_rt');
      return response;
    }

    const data = await res.json();
    const newRes = NextResponse.json({ accessToken: data.data?.accessToken });
    
    if (data.data?.refreshToken) {
      newRes.cookies.set('__gradnet_rt', data.data.refreshToken, {
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'lax',
        maxAge: 60 * 60 * 24 * 30,
        path: '/',
      });
    }

    return newRes;
  } catch {
    return NextResponse.json({ error: 'Refresh failed' }, { status: 500 });
  }
}

// DELETE — clears refreshToken cookie
export async function DELETE() {
  const res = NextResponse.json({ ok: true });
  res.cookies.delete('__gradnet_rt');
  return res;
}
