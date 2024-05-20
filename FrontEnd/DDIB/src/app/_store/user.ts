import { create } from "zustand";
import { persist } from "zustand/middleware";
import { User } from "@/app/_types/types";

interface UserInfo {
  jwt: string;
  userPk: number;
  user: User;
  setJwt: (token: string) => void;
  setUserPk: (pk: number) => void;
  setUserInfo: (info: User) => void;
}

export const userStore = create<UserInfo>((set, get) => ({
  jwt: "",
  userPk: 0,
  user: {
    name: "",
    phone: "",
    email: "",
    zipcode: "",
    roadAddress: "",
    detailAddress: "",
  },
  setJwt: (token) => set(() => ({ jwt: token })),
  setUserPk: (pk) => set(() => ({ userPk: pk })),
  setUserInfo: (info) => set(() => ({ user: info })),
}));
