"use client";

import styles from "./eventBtm.module.scss";

interface Props {
  joinBuy: () => void;
}

export default function EventBtn({ joinBuy }: Props) {
  return (
    <>
      <div
        className={styles.joinBuy}
        onClick={() => {
          joinBuy();
        }}
      ></div>
    </>
  );
}
