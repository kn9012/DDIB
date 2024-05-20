"use client";

import Link from "next/link";
import { useState, useEffect } from "react";
import { useSelectedLayoutSegment } from "next/navigation";
import styles from "./myPageMenu.module.scss";

export default function MyPageMenu() {
  const segment = useSelectedLayoutSegment();

  return (
    <>
      <div className={styles.myPageMenu}>
        <ul className={segment === "orderlist" ? styles.menuOrder : segment === "wishlist" ? styles.menuWish : styles.menuUser}>
          <div className={styles.horiselector}></div>
          <li className={styles.menuItemOne}>
            <Link href={"/mypage/orderlist"}>주문조회</Link>
          </li>
          <li className={styles.menuItemTwo}>
            <Link href={"/mypage/wishlist"}>위시리스트</Link>
          </li>
          <li className={styles.menuItemThree}>
            <Link href={"/mypage/userinfo"}>회원정보</Link>
          </li>
        </ul>
      </div>
    </>
  );
}
