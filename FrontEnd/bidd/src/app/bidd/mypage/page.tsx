"use client";

import { useRouter } from "next/navigation";

export default function MyPage() {
  const router = useRouter();
  router.replace("/mypage/myitems");
}
