"use client";

import styles from "./sellerInfo.module.scss";
import { useEffect, useRef } from "react";
import { getSellerInfo } from "@/app/_api/user";
import { BusinessInfos } from "@/app/_types/types";
import { useQuery } from "@tanstack/react-query";

export default function SellerInfo() {
  // const { user, userPk } = userStore();

  const pk = 2;

  const { data } = useQuery<BusinessInfos>({
    queryKey: ["businessinfo", pk],
    queryFn: () => getSellerInfo(pk),
  });

  const companyNameRef = useRef<HTMLInputElement>(null);
  const businessNumberRef = useRef<HTMLInputElement>(null);
  const ceoNameRef = useRef<HTMLInputElement>(null);
  const ceoEmailRef = useRef<HTMLInputElement>(null);
  const ceoPhoneRef = useRef<HTMLInputElement>(null);

  useEffect(() => {}, []);

  return (
    <div className={styles.container}>
      <div className={styles.title}>Company Info</div>
      <div className={styles.itemArea}>
        <div className={styles.item}>
          <div>기업명</div>
          <input type="text" ref={companyNameRef} />
        </div>
        <div className={styles.item}>
          <div>사업자번호</div>
          <input type="text" readOnly ref={businessNumberRef} />
        </div>
        <div className={styles.item}>
          <div>대표명</div>
          <input type="text" ref={ceoNameRef} />
        </div>
        <div className={styles.item}>
          <div>대표이메일</div>
          <input type="text" ref={ceoEmailRef} />
        </div>
        <div className={styles.item}>
          <div>대표번호</div>
          <input type="text" ref={ceoPhoneRef} />
        </div>
      </div>
    </div>
  );
}
