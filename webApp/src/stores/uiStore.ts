import { create } from 'zustand';

interface UIState {
  sidebarOpen: boolean;
  mobileNavOpen: boolean;
  commandPaletteOpen: boolean;
  toggleSidebar: () => void;
  toggleMobileNav: () => void;
  toggleCommandPalette: () => void;
  setSidebarOpen: (open: boolean) => void;
}

export const useUIStore = create<UIState>((set) => ({
  sidebarOpen: true,
  mobileNavOpen: false,
  commandPaletteOpen: false,

  toggleSidebar: () => set((s) => ({ sidebarOpen: !s.sidebarOpen })),
  toggleMobileNav: () => set((s) => ({ mobileNavOpen: !s.mobileNavOpen })),
  toggleCommandPalette: () => set((s) => ({ commandPaletteOpen: !s.commandPaletteOpen })),
  setSidebarOpen: (open) => set({ sidebarOpen: open }),
}));
