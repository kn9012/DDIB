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
        <ul
          className={
            segment === "myitems" ? styles.menuMyItem : styles.menuCompanyInfo
          }
        >
          <div className={styles.horiselector}></div>
          <li className={styles.menuItemOne}>
            <Link href={"/mypage/myitems"}>등록상품</Link>
          </li>
          <li className={styles.menuItemTwo}>
            <Link href={"/mypage/companyinfo"}>기업정보</Link>
          </li>
        </ul>
      </div>
    </>
  );
}
