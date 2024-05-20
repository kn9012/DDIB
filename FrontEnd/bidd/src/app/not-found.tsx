"use client";

import Link from "next/link";
import { NextPage } from "next";

import not from "./_components/notfound.json";
import Lottie from "react-lottie-player";

const NotFound: NextPage = () => {
  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", marginTop: "10vw" }}>
      <div style={{ fontSize: "2vw", fontWeight: "bold" }}>존재하지 않는 페이지 입니다.</div>
      <div>
        <Lottie loop animationData={not} play style={{ width: 400, height: 400 }} />
      </div>
      <Link href="/" style={{ fontSize: "2vw", fontWeight: "bold" }}>
        BIDD으로 돌아가기
      </Link>
    </div>
  );
};

export default NotFound;
