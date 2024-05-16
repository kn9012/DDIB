"use client";

import styles from "./waitingModal.module.scss";
import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { useQuery } from "@tanstack/react-query";
import { getWaitingList } from "../_api/waiting";
import { userStore } from "@/app/_store/user";
import { Que } from "@/app/_types/types";
import Cookies from "js-cookie";

export default function WaitingModal() {
  const router = useRouter();
  const { user } = userStore();

  useEffect(() => {
    console.log("대기페이지에용");
    document.body.style.cssText = `
    position : fixed;
    top: -${window.scrollY}px;
    overflow-y: scroll;
    width: 100%;`;
    return () => {
      const scrollY = document.body.style.top;
      document.body.style.cssText = "";
      window.scrollTo(0, parseInt(scrollY || "0", 10) * -1);
    };
  }, []);

  const { data } = useQuery<Que>({
    queryKey: ["waiting", 1],
    queryFn: () => getWaitingList(1),
    refetchInterval: 3000,
  });

  useEffect(() => {
    if (data && data.rank <= 0) {
      console.log("change");
      var expiresAt = new Date();
      console.log(expiresAt);
      expiresAt.setMinutes(expiresAt.getMinutes() + 5);
      Cookies.set("state", "true", { expires: expiresAt });
      router.replace(`/order`);
    }
  }, [data]);

  return (
    <>
      <div className={styles.modalBackground}>
        <div className={styles.modalInfo}>
          <div>나의 대기순서</div>
          <div>{data?.rank && data.rank < 0 ? "0" : data?.rank}</div>
          <div>현재 접속량이 많아 대기 중입니다.</div>
          <div>잠시만 기다려 주시면 결제 페이지로 연결됩니다.</div>
          <div>
            <div className={styles.bang}>!</div> 새로고침 하거나 재접속 하시면
          </div>
          <div>대기순서가 초기화되어 대기시간이 더 길어집니다.</div>
        </div>
      </div>
    </>
  );
}
