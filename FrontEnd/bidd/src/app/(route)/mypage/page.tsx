"use client";

import { useRouter } from "next/router";

export default function MyPage() {
  const router = useRouter();
  router.replace("/mypage/myitems");
}
