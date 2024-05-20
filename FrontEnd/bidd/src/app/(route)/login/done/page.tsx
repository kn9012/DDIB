"use client";

import { useRouter } from "next/navigation";

export default function LoginDone() {
  const router = useRouter();
  router.replace("/");
}
