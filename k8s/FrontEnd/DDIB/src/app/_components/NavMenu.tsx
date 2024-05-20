"use client";

import styles from "./navMenu.module.scss";
import { useSelectedLayoutSegment } from "next/navigation";
import Link from "next/link";
import React, { useState } from "react";
import { userStore } from "../_store/user";
import { IoSearch } from "react-icons/io5";
import { IoSearchOutline } from "react-icons/io5";
import { GoBell } from "react-icons/go";
import { GoBellFill } from "react-icons/go";
import { GoPerson } from "react-icons/go";
import { GoPersonFill } from "react-icons/go";
import Alarm from "./Alarm";
import Cookies from "js-cookie";
import { useMutation } from "@tanstack/react-query";
import { postUser } from "@/app/_api/user";
import { useRouter } from "next/navigation";

export default function NavMenu() {
  const segment = useSelectedLayoutSegment();
  console.log(segment);

  const { jwt } = userStore();
  const router = useRouter();

  const [bellOn, setBellOn] = useState(false);

  const logOutUser = useMutation({
    mutationFn: async () => {
      return postUser();
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
    //window.location.href = "https://k10c102.p.ssafy.io/api/oauth2/ddib/kakao";
    window.location.href = "https://ddib.kro.kr/api/oauth2/ddib/kakao";
  };

  const logOut = () => {
    logOutUser.mutate();
  };

  return (
    <>
      <div className={styles.main}>
        <li className={styles.title}>
          <Link href="/">DDIB</Link>
        </li>
        <li>
          <Link href="/products">
            {segment === "products" ? (
              <>
                <div className={styles.subTitle} style={{ fontWeight: "bold" }}>
                  TimeDeal
                </div>
              </>
            ) : (
              <>
                <div className={styles.subTitle}>TimeDeal</div>
              </>
            )}
          </Link>
        </li>
        <li>
          <Link href="https://bidd.kro.kr">
            <div className={styles.subTitle} style={{ paddingRight: "1.5vw" }}>
              <div>BIDD</div>
              {/* <div className={styles.goIcon}>
                <HiArrowTopRightOnSquare />
              </div> */}
            </div>
          </Link>
        </li>
        <li className={styles.search}>
          <Link href="/search">
            {segment === "search" ? (
              <>
                <IoSearch className={styles.icons} />
              </>
            ) : (
              <>
                <IoSearchOutline className={styles.icons} />
              </>
            )}
          </Link>
        </li>
        {Cookies.get("Authorization") && (
          <li>
            <div className={styles.alarm} onClick={() => setBellOn((prev) => !prev)}>
              {bellOn ? <GoBellFill className={styles.icons} /> : <GoBell className={styles.icons} />}
            </div>
            {bellOn && (
              <div className={styles.alarmModal}>
                <Alarm />
              </div>
            )}
          </li>
        )}
        <li>
          {!Cookies.get("Authorization") ? (
            <>
              <div onClick={kakaoLogin} className={styles.beforeLogin}>
                <GoPerson className={styles.icons} />
                <div className={styles.logBtn}>Login</div>
              </div>
            </>
          ) : segment === "mypage" ? (
            <>
              <Link href="/mypage">
                <div className={styles.afterLogin}>
                  <GoPersonFill className={styles.icons} />
                  <div className={styles.logBtn} onClick={logOut}>
                    Logout
                  </div>
                </div>
              </Link>
            </>
          ) : (
            <>
              <Link href="/mypage">
                <div className={styles.afterLogin}>
                  <GoPerson className={styles.icons} />
                  <div className={styles.logBtn} onClick={logOut}>
                    Logout
                  </div>
                </div>
              </Link>
            </>
          )}
        </li>
      </div>
    </>
  );
}
