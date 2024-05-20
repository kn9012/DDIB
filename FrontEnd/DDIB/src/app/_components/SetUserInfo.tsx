"use client";

import { useEffect } from "react";
import { useQuery } from "@tanstack/react-query";
import { getUserInfo } from "../_api/user";
import { User } from "../_types/types";
import { userStore } from "../_store/user";
import Cookies from "js-cookie";

export default function SetUserInfo() {
  const { setUserInfo } = userStore();
  const pk = Cookies.get("num") as string;

  const { data } = useQuery<User>({
    queryKey: ["userInfo", pk],
    queryFn: () => getUserInfo(pk),
    staleTime: 86400000,
  });

  useEffect(() => {
    console.log(data);
    if (data) {
      const info = {
        name: data.name,
        phone: data.phone,
        email: data.email,
        zipcode: data.zipcode,
        roadAddress: data.roadAddress,
        detailAddress: data.detailAddress,
      };
      setUserInfo(info);
    }
  }, [data]);

  return null;
}
