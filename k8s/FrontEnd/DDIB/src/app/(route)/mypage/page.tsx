"use client";

import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function MyPage() {
  const router = useRouter();

  useEffect(() => {
    router.replace("/mypage/orderlist");
  }, []);
  return null;
}
