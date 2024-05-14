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
import { BiLogIn } from "react-icons/bi";
import { TbLogin2 } from "react-icons/tb";
import { CiLogin } from "react-icons/ci";
import { ImEnter } from "react-icons/im";
import { TbDoorEnter } from "react-icons/tb";
import { useMutation } from "@tanstack/react-query";
import Cookies from "js-cookie";
import { useRouter } from "next/navigation";
import { postUser } from "@/app/_api/user";

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
          <Link href="/paypolicy">
            {segment === "paypolicy" ? (
              <>
                <div className={styles.subTitle} style={{ fontWeight: "bold" }}>
                  가격정책
                </div>
              </>
            ) : (
              <>
                <div className={styles.subTitle}>가격정책</div>
              </>
            )}
          </Link>
        </li>
        <li>
          <Link href="https://www.naver.com">
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
