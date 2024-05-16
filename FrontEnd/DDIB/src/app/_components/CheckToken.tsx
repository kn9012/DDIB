"use client";

import { useEffect } from "react";
import { userStore } from "../_store/user";
import Cookies from "js-cookie";

export default function CheckToken() {
  const { jwt, userPk, setJwt, setUserPk } = userStore();

  const update = () => {
    console.log(jwt);
    if (Cookies.get("Authorization")) {
      if (jwt === "no") {
        const token = Cookies.get("Authorization") as string;
        const pk = parseInt(Cookies.get("num") as string, 10);
        console.log(token);
        console.log(pk);
        setJwt(token);
        setUserPk(pk);
        alert("로그인 성공");
      }
    }

    console.log(jwt);
    console.log(userPk);
  };

  useEffect(() => {
    update();
  }, []);

  return null;
}
