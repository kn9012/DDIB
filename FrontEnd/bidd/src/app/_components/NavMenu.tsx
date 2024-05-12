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

export default function NavMenu() {
  const segment = useSelectedLayoutSegment();
  console.log(segment);

  const { jwt } = userStore();

  const [bellOn, setBellOn] = useState(false);

  const kakaoLogin = () => {
    //window.location.href = "http://localhost:8081/api/oauth2/ddib/kakao";
    window.location.href = "https://k10c102.p.ssafy.io/api/oauth2/bidd/kakao";
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
          {jwt.length == 0 ? (
            <div>
              {/* <BiLogIn className={styles.icons} />
              <TbLogin2 className={styles.icons} />
              <CiLogin className={styles.icons} /> */}
              {/* <ImEnter className={styles.icons} /> */}
              <TbDoorEnter className={styles.icons} />
            </div>
          ) : (
            <div></div>
          )}
        </li>
        {/* {jwt.length != 0 && (
        )} */}
        <li>
          {jwt.length == 0 ? (
            <>
              <div onClick={kakaoLogin}>
                <GoPerson className={styles.icons} />
              </div>
            </>
          ) : segment === "mypage" ? (
            <>
              <Link href="/mypage">
                <div>
                  <GoPersonFill className={styles.icons} />
                </div>
              </Link>
            </>
          ) : (
            <Link href="/mypage">
              <div>
                <GoPerson className={styles.icons} />
              </div>
            </Link>
          )}
        </li>
      </div>
    </>
  );
}
