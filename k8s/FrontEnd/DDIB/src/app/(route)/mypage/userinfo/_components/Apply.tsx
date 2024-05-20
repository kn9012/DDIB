"use client";

import styles from "./apply.module.scss";
import { useEffect, useState } from "react";
import { putAlarmOff, putAlarmOn, getAlarmCategory } from "@/app/_api/alarm";
import { useMutation, useQuery } from "@tanstack/react-query";
import { AlarmApply } from "@/app/_types/types";
import Cookies from "js-cookie";

export default function Apply() {
  const userPk = Cookies.get("num") as string;

  const [CategoryItem, setCategoryItem] = useState([
    { title: "Fashion", value: false },
    { title: "Beauty", value: false },
    { title: "Food", value: false },
    { title: "Appliance", value: false },
    { title: "Sports", value: false },
    { title: "Living", value: false },
    { title: "Pet", value: false },
    { title: "Travel", value: false },
  ]);

  const { data } = useQuery<AlarmApply>({
    queryKey: ["alarmApply", userPk],
    queryFn: () => getAlarmCategory(userPk),
  });

  const apply = useMutation({
    mutationFn: async (data: AlarmApply) => {
      return await putAlarmOn(userPk, data);
    },
    async onSuccess(response) {
      alert("알림신청성공");
    },
    onError(error) {
      console.error(error);
    },
  });

  const cancle = useMutation({
    mutationFn: async () => {
      return await putAlarmOff(userPk);
    },
    async onSuccess(response) {
      alert("알림취소완료");
    },
    onError(error) {
      console.error(error);
    },
  });

  const handleItemClick = (index: number) => {
    setCategoryItem((prevItems) => prevItems.map((item, i) => (i === index ? { ...item, value: !item.value } : item)));
  };

  const applyAlarm = () => {
    const alarm = {
      subscribeFashion: CategoryItem[0].value,
      subscribeBeauty: CategoryItem[1].value,
      subscribeFood: CategoryItem[2].value,
      subscribeAppliance: CategoryItem[3].value,
      subscribeSports: CategoryItem[4].value,
      subscribeLiving: CategoryItem[5].value,
      subscribePet: CategoryItem[6].value,
      subscribeTravel: CategoryItem[7].value,
    };
    apply.mutate(alarm);
  };

  const cancelAlarm = () => {
    cancle.mutate();
  };

  useEffect(() => {
    if (data) {
      setCategoryItem([
        { title: "Fashion", value: data.subscribeFashion },
        { title: "Beauty", value: data.subscribeBeauty },
        { title: "Food", value: data.subscribeFood },
        { title: "Appliance", value: data.subscribeAppliance },
        { title: "Sports", value: data.subscribeSports },
        { title: "Living", value: data.subscribeLiving },
        { title: "Pet", value: data.subscribePet },
        { title: "Travel", value: data.subscribeTravel },
      ]);
    }
  }, [data]);

  return (
    <>
      <div>키워드 알림 신청</div>
      <div className={styles.underBar}></div>
      <div className={styles.category}>
        {CategoryItem.map((item, index) => (
          <div key={index} className={item.value ? styles.check : styles.item} onClick={() => handleItemClick(index)}>
            {item.title}
          </div>
        ))}
      </div>
      <div className={styles.btnArea}>
        <div onClick={applyAlarm}>알림신청</div>
        <div onClick={cancelAlarm}>알림취소</div>
      </div>
    </>
  );
}
