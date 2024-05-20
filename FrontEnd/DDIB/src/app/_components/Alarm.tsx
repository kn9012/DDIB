"use client";

import { getAlarmList } from "@/app/_api/alarm";
import styles from "./alarm.module.scss";
import { useQuery } from "@tanstack/react-query";
import { AlarmList } from "@/app/_types/types";
import Link from "next/link";
import Cookies from "js-cookie";

export default function Alarm() {
  const userPk = Cookies.get("num") as string;

  const { data } = useQuery<AlarmList[]>({
    queryKey: ["orderDetail", userPk],
    queryFn: () => getAlarmList(userPk),
  });

  const setDate = (date: string) => {
    const day = date.substring(0, 10);
    const time = date.substring(11, 19);
    return day + " " + time;
  };

  const cookie = Cookies.get("fcm");

  return (
    <div className={styles.container}>
      <div className={styles.alarm}>
        <div>키워드 알람</div>
        {cookie == "true" ? <div className={styles.on}>ON</div> : <div className={styles.off}>OFF</div>}
        <Link href="/mypage/userinfo">
          <div>&gt;</div>
        </Link>
      </div>
      <div className={styles.textArea}>
        {data &&
          data.map((item, index) => (
            <div className={styles.text} key={index}>
              {item.generatedTime.length != 0 && <div>{setDate(item.generatedTime)}</div>}
              <div>{item.title}</div>
              <div>{item.content}</div>
            </div>
          ))}
      </div>
    </div>
  );
}
