"use client";

import { useRef, useState, ChangeEvent } from "react";
import styles from "./applyForm.module.scss";
import { useMutation } from "@tanstack/react-query";
import { BusinessInfo } from "@/app/_types/types";
import { putApplyBusiness, postBusinessCheck } from "@/app/_api/business";
import { useRouter } from "next/navigation";

export default function ApplyForm() {
  const router = useRouter();

  const businessNameRef = useRef<HTMLInputElement>(null);
  const numberRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);
  const phoneRef = useRef<HTMLInputElement>(null);
  const emailFormRef = useRef<HTMLInputElement>(null);

  const [isDrobBox, setIsDropbox] = useState(false);

  const emails = ["@naver.com", "@gmail.com", "@daum.net"];
  const [emailList, setEmailList] = useState(emails);
  const [email, setEmail] = useState("");

  const applyBusiness = useMutation({
    mutationFn: async (data: BusinessInfo) => {
      return await putApplyBusiness(1, data);
    },
    async onSuccess(response) {
      console.log(response);
      alert("기업신청이 완료되었습니다.");
      router.push("/");
    },
    onError(error) {
      alert("기업신청 실패");
      console.error(error);
    },
  });

  const sendApply = async () => {
    if (
      businessNameRef.current?.value &&
      businessNameRef.current.value.length !== 0 &&
      numberRef.current?.value &&
      numberRef.current.value.length !== 0 &&
      nameRef.current?.value &&
      nameRef.current.value.length !== 0 &&
      phoneRef.current?.value &&
      phoneRef.current.value.length !== 0
    ) {
      const result = await postBusinessCheck(numberRef.current.value);
      console.log(result);
      if (result != "계속사업자") {
        alert("사업자 번호를 확인해주세요");
      } else {
        const info = {
          companyName: businessNameRef.current.value,
          businessNumber: numberRef.current.value as unknown as number,
          ceoName: nameRef.current.value,
          ceoEmail: email,
          ceoPhone: phoneRef.current.value,
        };
        console.log(info);
        applyBusiness.mutate(info);
        console.log("send");
      }
    } else {
      alert("비어있는 칸이 있습니다.");
    }
  };

  const onChangeEmail = (e: ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
    if (e.target.value.includes("@")) {
      setIsDropbox(true);
      setEmailList(
        emails.filter((e1) => e1.includes(e.target.value.split(`@`)[1]))
      );
    } else {
      setIsDropbox(false);
    }
  };

  const clickEmail = (email: string, item: string) => {
    setEmail(`${email.split("@")[0]}${item}`);
    setIsDropbox(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.item}>
        <div>기업명</div>
        <input type="text" ref={businessNameRef} />
      </div>
      <div className={styles.item}>
        <div>사업자번호</div>
        <input type="number" ref={numberRef} />
      </div>
      <div className={styles.item}>
        <div>대표이름</div>
        <input type="text" ref={nameRef} />
      </div>
      <div className={styles.item}>
        <div>대표번호</div>
        <input type="text" ref={phoneRef} />
      </div>
      <div className={styles.item} ref={emailFormRef}>
        <div>대표이메일</div>
        <input
          type="text"
          value={email}
          onChange={(e) => {
            onChangeEmail(e);
          }}
        />
      </div>
      {isDrobBox && (
        <div className={styles.emailArea}>
          {emailList.map((item, index) => (
            <div
              className={styles.emailItem}
              onClick={() => clickEmail(email, item)}
              key={index}
            >
              <div> {email.split("@")[0]}</div>
              <div>{item}</div>
            </div>
          ))}
        </div>
      )}

      <div className={styles.applyBtn} onClick={sendApply}>
        신청하기
      </div>
    </div>
  );
}
