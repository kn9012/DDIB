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

export const userStore = create(
  persist<UserInfo>(
    (set) => ({
      jwt: "",
      userPk: 0,
      user: {
        name: "",
        phone: "",
        email: "",
        zipCode: "",
        roadAddress: "",
        detailAddress: "",
      },
      setJwt: (token) => set(() => ({ jwt: token })),
      setUserPk: (pk) => set(() => ({ userPk: pk })),
      setUserInfo: (info) => set(() => ({ user: info })),
    }),
    {
      name: "UserStorage",
    }
  )
);
