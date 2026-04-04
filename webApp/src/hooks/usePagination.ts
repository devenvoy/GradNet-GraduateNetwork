'use client';
import { useCallback, useState } from 'react';

interface UsePaginationOptions {
  initialPage?: number;
  initialPerPage?: number;
}

export function usePagination(options: UsePaginationOptions = {}) {
  const [page, setPage] = useState(options.initialPage ?? 1);
  const [perPage, setPerPage] = useState(options.initialPerPage ?? 10);

  const nextPage = useCallback(() => setPage((p) => p + 1), []);
  const prevPage = useCallback(() => setPage((p) => Math.max(1, p - 1)), []);
  const goToPage = useCallback((p: number) => setPage(p), []);
  const changePerPage = useCallback((pp: number) => {
    setPerPage(pp);
    setPage(1);
  }, []);

  return { page, perPage, nextPage, prevPage, goToPage, changePerPage };
}
