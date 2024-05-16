"use client";

import { useState, useEffect } from "react";
import styles from "./timeCount.module.scss";

interface Props {
  startTime: string;
}

export default function TimeCount({ startTime }: Props) {
  const [targetTime] = useState<Date>(new Date(startTime));
  const [timeLeft, setTimeLeft] = useState<number>(
    targetTime.getTime() - new Date().getTime()
  );

  useEffect(() => {
    const timerID = setInterval(() => {
      const newTimeLeft = targetTime.getTime() - new Date().getTime();
      setTimeLeft(newTimeLeft);

      if (newTimeLeft <= 0) {
        clearInterval(timerID);
      }
    }, 1000);

    return () => clearInterval(timerID);
  }, [targetTime]);

  const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
  const hours = Math.floor(
    (timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
  );
  const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
  const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

  return (
    <>
      {/* <span>{start}</span> */}
      {/* <div>{endTime}</div> */}
      <div>
        {days <= 0 ? "" : `${days}:`}
        {hours < 0 ? "00" : hours < 10 ? `0${hours}` : `${hours}`}:
        {minutes < 0 ? "00" : minutes < 10 ? `0${minutes}` : `${minutes}`}:
        {seconds < 0 ? "00" : seconds < 10 ? `0${seconds}` : `${seconds}`}
      </div>
    </>
  );
}
