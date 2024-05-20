"use client";

import Lottie from "react-lottie-player";

import fail from "./fail.json";
import styles from "./fail.module.scss";
import Link from "next/link";

export default function Fail() {
  return (
    <div className={styles.container}>
      <div>
        <Lottie loop animationData={fail} play style={{ width: 400, height: 400, scale: 2 }} />
      </div>
      <div className={styles.alert}>결제 실패. 다시 주문해 주세요</div>
      <Link href="/" className={styles.home}>
        홈으로
      </Link>
    </div>
  );
}
