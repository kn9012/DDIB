"use client";

import AddressForm from "@/app/_components/AddressForm";
import styles from "./userInfo.module.scss";
import { useMutation, useQuery } from "@tanstack/react-query";
import { deleteUser, putUserInfo, getUserInfo } from "@/app/_api/user";
import SetUserInfo from "@/app/_components/SetUserInfo";
import Apply from "@/app/(route)/mypage/userinfo/_components/Apply";
import Cookies from "js-cookie";
import { useRef, useEffect } from "react";
import { RefProps } from "@/app/_components/AddressForm";
import { useRouter } from "next/navigation";
import { orderAddressStore } from "@/app/_store/product";
import { User, UserModi } from "@/app/_types/types";
import { userStore } from "@/app/_store/user";

export default function UserInfo() {
  const { setUserInfo } = userStore();
  const { addressInfo } = orderAddressStore();
  const userPk = Cookies.get("num") as string;
  const saveRef = useRef<RefProps>(null);
  const router = useRouter();

  const { data, refetch } = useQuery<User>({
    queryKey: ["userInfo", userPk],
    queryFn: () => getUserInfo(userPk),
    staleTime: 86400000,
  });

  useEffect(() => {
    console.log("useEffect");
    if (data) {
      const info = {
        name: data.name,
        phone: data.phone,
        email: data.email,
        zipcode: data.zipcode,
        roadAddress: data.roadAddress,
        detailAddress: data.detailAddress,
      };

      console.log(data);
      console.log(info);
      setUserInfo(info);
    }
  }, [data]);

  const quitUser = useMutation({
    mutationFn: async () => {
      return await deleteUser(userPk);
    },
    async onSuccess(response) {
      alert("탈퇴완료");
      router.replace("/");
    },
    onError(error) {
      console.error(error);
    },
  });

  const modifyUser = useMutation({
    mutationFn: async (data: UserModi) => {
      return await putUserInfo(userPk, data);
    },
    async onSuccess(response) {
      alert("수정완료");
      refetch();
    },
    onError(error) {
      console.error(error);
    },
  });

  const modify = () => {
    const check = saveRef?.current?.saveAddress();

    if (check) {
      const sendUser = {
        name: check.receiverName,
        phone: check.receiverPhone,
        roadAddress: check.orderRoadAddress,
        detailAddress: check.orderDetailAddress,
        zipcode: check.orderZipcode,
      };
      console.log(sendUser);
      modifyUser.mutate(sendUser);
    } else {
      alert("비어있는 칸이 있습니다.");
    }
  };

  const quit = () => {
    quitUser.mutate();
  };

  return (
    <>
      {data && (
        <div>
          <div className={styles.title}>User Info</div>
          <div className={styles.categoryArea}>
            <Apply />
          </div>
          <AddressForm type="mypage" ref={saveRef} />
          <div className={styles.btnArea}>
            <div className={styles.modifyBtn} onClick={() => modify()}>
              수정하기
            </div>
            <div className={styles.quitBtn} onClick={() => quit()}>
              탈퇴하기
            </div>
          </div>
        </div>
      )}
    </>
  );
}
