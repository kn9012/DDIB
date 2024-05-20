import { create } from "zustand";

interface ProductCreate {
  start: number;
  end: number;
  checkCnt: number;
  thumb: File | null;
  details: File[];
  setStart: (st: number) => void;
  setEnd: (en: number) => void;
  setCheckCnt: (cnt: number) => void;
  setThumb: (name: File) => void;
  setDetail: (files: File) => void;
  resetDetails: () => void;
  resetAll: () => void;
}

export const productCreateStore = create<ProductCreate>((set) => ({
  start: 0,
  end: 0,
  checkCnt: 0,
  thumb: null,
  details: [],
  setStart: (st) => set(() => ({ start: st })),
  setEnd: (en) => set(() => ({ end: en })),
  setCheckCnt: (cnt) => set(() => ({ checkCnt: cnt })),
  setThumb: (name: File) => set(() => ({ thumb: name })),
  setDetail: (file) => set((state) => ({ details: [...state.details, file] })),
  resetDetails: () => set(() => ({ details: [] })),
  resetAll: () =>
    set(() => ({
      start: 0,
      end: 0,
      checkCnt: 0,
      thumb: null,
      details: [],
    })),
}));
