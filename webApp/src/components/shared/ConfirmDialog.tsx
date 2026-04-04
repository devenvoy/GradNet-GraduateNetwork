'use client';
import { useState, type ReactNode } from 'react';

interface ConfirmDialogProps {
  title: string;
  description: string;
  confirmLabel?: string;
  onConfirm: () => void;
  children: (open: () => void) => ReactNode;
  destructive?: boolean;
}

export default function ConfirmDialog({ title, description, confirmLabel = 'Confirm', onConfirm, children, destructive }: ConfirmDialogProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      {children(() => setIsOpen(true))}
      {isOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm" onClick={() => setIsOpen(false)}>
          <div className="bg-surface-container-high rounded-2xl p-8 max-w-md w-full mx-4 shadow-ambient" onClick={(e) => e.stopPropagation()}>
            <h3 className="font-headline text-xl text-white mb-2">{title}</h3>
            <p className="text-on-surface-variant text-sm mb-8">{description}</p>
            <div className="flex justify-end gap-3">
              <button
                onClick={() => setIsOpen(false)}
                className="px-6 py-2 rounded-xl text-sm font-medium text-on-surface-variant hover:bg-surface-container transition-colors"
              >
                Cancel
              </button>
              <button
                onClick={() => { onConfirm(); setIsOpen(false); }}
                className={`px-6 py-2 rounded-xl text-sm font-bold tracking-wide transition-all active:scale-95 ${
                  destructive
                    ? 'bg-error-container text-on-error-container hover:brightness-110'
                    : 'btn-gradient'
                }`}
              >
                {confirmLabel}
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
