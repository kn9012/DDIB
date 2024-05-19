"use client";

import styles from "./navMenu.module.scss";
import { useSelectedLayoutSegment } from "next/navigation";
import Link from "next/link";
import React, { useState } from "react";
import { userStore } from "../_store/user";

import { GoPerson } from "react-icons/go";
import { GoPersonFill } from "react-icons/go";

import { useMutation } from "@tanstack/react-query";
import Cookies from "js-cookie";
import { useRouter } from "next/navigation";
import { postUser } from "@/app/_api/user";

export default function NavMenu() {
  const segment = useSelectedLayoutSegment();
  console.log(segment);

  const router = useRouter();

  const logOutUser = useMutation({
    mutationFn: async () => {
      return await postUser();
    },
    async onSuccess(response) {
      console.log("로그아웃완료");
      localStorage.clear();
      Cookies.remove("Authorization");
      Cookies.remove("num");
      Cookies.remove("refresh");
      router.replace("/");
    },
    onError(error) {
      console.error(error);
    },
  });

  const kakaoLogin = () => {
    //window.location.href = "http://localhost:8081/api/oauth2/ddib/kakao";
    window.location.href = "https://bidd.kro.kr/api/oauth2/bidd/kakao";
  };

  const logOut = () => {
    logOutUser.mutate();
  };

  return (
    <>
      <div className={styles.main}>
        <li className={styles.title}>
          <Link href="/">BIDD</Link>
        </li>
        {Cookies.get("Authorization") && (
          <>
            <li>
              <Link href="/apply">
                {segment === "apply" ? (
                  <>
                    <div className={styles.subTitle} style={{ fontWeight: "bold" }}>
                      기업신청
                    </div>
                  </>
                ) : (
                  <>
                    <div className={styles.subTitle}>기업신청</div>
                  </>
                )}
              </Link>
            </li>
            <li>
              <Link href="/register">
                {segment === "register" ? (
                  <>
                    <div className={styles.subTitle} style={{ fontWeight: "bold" }}>
                      상품등록
                    </div>
                  </>
                ) : (
                  <>
                    <div className={styles.subTitle}>상품등록</div>
                  </>
                )}
              </Link>
            </li>
          </>
        )}
        <li>
          <Link href="https://ddib.kro.kr/">
            <div className={styles.subTitle} style={{ paddingRight: "1.5vw" }}>
              <div>DDIB</div>
              {/* <div className={styles.goIcon}>
                <HiArrowTopRightOnSquare />
              </div> */}
            </div>
          </Link>
        </li>

        <li className={styles.login}>
          {!Cookies.get("Authorization") ? (
            <>
              <div onClick={kakaoLogin} className={styles.beforeLogin}>
                <GoPerson className={styles.icons} />
                <div className={styles.logBtn}>Login</div>
              </div>
            </>
          ) : segment === "mypage" ? (
            <>
              <div className={styles.afterLogin}>
                <GoPersonFill className={styles.icons} />
                <div className={styles.logBtn} onClick={logOut}>
                  Logout
                </div>
              </div>
            </>
          ) : (
            <>
              <div className={styles.afterLogin}>
                <Link href="/mypage">
                  <GoPerson className={styles.icons} />
                </Link>
                <div className={styles.logBtn} onClick={logOut}>
                  Logout
                </div>
              </div>
            </>
          )}
        </li>
      </div>
    </>
  );
}
