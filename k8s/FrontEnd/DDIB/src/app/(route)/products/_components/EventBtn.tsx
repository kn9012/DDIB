"use client";

import styles from "./eventBtn.module.scss";
import TimeCount from "@/app/_components/TimeCount";

interface Props {
  joinBuy: () => void;
  over: boolean;
  startTime: string;
}

export default function EventBtn({ joinBuy, over, startTime }: Props) {
  return (
    <>
      {over ? (
        <div className={styles.end}>
          <div>타임딜이 종료되었습니다.</div>
        </div>
      ) : (
        <div
          className={styles.joinBuy}
          onClick={() => {
            joinBuy();
          }}
        >
          <div>
            <TimeCount startTime={startTime} />
          </div>
        </div>
      )}
    </>
  );
}
