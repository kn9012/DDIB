"use client";
import { useState, useEffect } from "react";
import styles from "./week.module.scss";

interface Props {
  checkDay: number;
  changeDay: (num: number) => void;
}

export default function Week({ checkDay, changeDay }: Props) {
  const now = new Date();
  const todayWeek = now.getDay();
  const today = now.getDate();
  const lastday = new Date(now.getFullYear(), now.getMonth() + 1, 0).getDate();

  const getAlldate = (today: number, lastday: number) => {
    let dates = [];

    dates[0] = today;
    for (let i = 1; i <= 6; i++) {
      today++;
      if (today > lastday) {
        today = 1;
        dates[i] = today;
      } else {
        dates[i] = today;
      }
    }

    return dates;
  };

  const getAllweak = (todayWeek: number) => {
    let strWeak = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    let weaklist = [];

    weaklist[0] = strWeak[todayWeek];

    for (let i = 1; i <= 6; i++) {
      todayWeek++;
      if (todayWeek > 6) {
        todayWeek = 0;
        weaklist[i] = strWeak[todayWeek];
      } else {
        weaklist[i] = strWeak[todayWeek];
      }
    }

    return weaklist;
  };

  const CalendarDay = getAlldate(today, lastday);
  const CalendarWeek = getAllweak(todayWeek);

  const CalendarObject = [
    { week: CalendarWeek[0], day: CalendarDay[0] },
    { week: CalendarWeek[1], day: CalendarDay[1] },
    { week: CalendarWeek[2], day: CalendarDay[2] },
    { week: CalendarWeek[3], day: CalendarDay[3] },
    { week: CalendarWeek[4], day: CalendarDay[4] },
    { week: CalendarWeek[5], day: CalendarDay[5] },
    { week: CalendarWeek[6], day: CalendarDay[6] },
  ];

  useEffect(() => {
    return () => console.log("Clean up");
  });

  return (
    <>
      <div className={styles.container}>
        {CalendarObject.map((calendar, index) => (
          <div key={index} className={styles.dayBox} onClick={() => changeDay(index)}>
            <div className={calendar.week === "Sat" ? styles.sat : calendar.week === "Sun" ? styles.sun : styles.day}>{calendar.week}</div>
            <div className={styles.dayNum}>{calendar.day}</div>
            {checkDay === index && <div className={styles.underBar}></div>}
          </div>
        ))}
      </div>
    </>
  );
}
