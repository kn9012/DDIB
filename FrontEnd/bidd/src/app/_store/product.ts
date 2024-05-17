import { create } from "zustand";

interface ProductCreate {
  start: number;
  end: number;
  thumb: File | null;
  details: File[];
  setStart: (st: number) => void;
  setEnd: (en: number) => void;
  setThumb: (name: File) => void;
  setDetail: (files: File) => void;
  resetDetails: () => void;
}

export const productCreateStore = create<ProductCreate>((set) => ({
  start: 0,
  end: 0,
  thumb: null,
  details: [],
  setStart: (st) => set(() => ({ start: st })),
  setEnd: (en) => set(() => ({ end: en })),
  setThumb: (name: File) => set(() => ({ thumb: name })),
  setDetail: (file) => set((state) => ({ details: [...state.details, file] })),
  resetDetails: () => set(() => ({ details: [] })),
}));
