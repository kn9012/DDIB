"use client";

import styles from "./amountBtn.module.scss";
import { useEffect } from "react";
import { amountStore } from "@/app/_store/product";

interface Props {
  stock: number;
}

export default function AmountBtn({ stock }: Props) {
  const { amount, increaseAmount, decreaseAmount, setAmount } = amountStore();

  useEffect(() => {
    setAmount(1);
  }, []);

  const handleChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = parseInt(e.target.value);

    if (isNaN(newValue) || newValue < 1) {
      setAmount(1);
    } else {
      setAmount(newValue);
    }
  };

  const handleBlurInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    let newValue = parseInt(e.target.value);

    if (stock < newValue) {
      alert(`${stock}개 이하로 구매하실 수 있습니다.`);
      newValue = stock;
    }

    setAmount(newValue);
  };

  return (
    <div className={styles.amountBtnArea}>
      <button
        className={styles.amountBtn}
        type="button"
        disabled={amount === 1}
        onClick={() => decreaseAmount()}
      >
        -
      </button>
      <input
        className={styles.amountInput}
        type="number"
        min={1}
        max={stock}
        value={amount}
        onChange={handleChangeInput}
        onBlur={handleBlurInput}
      />
      <button
        className={styles.amountBtn}
        type="button"
        disabled={stock < 1 || stock === amount}
        onClick={() => increaseAmount()}
      >
        +
      </button>
    </div>
  );
}
