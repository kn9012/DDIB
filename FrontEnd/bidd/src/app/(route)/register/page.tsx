"use client";

import TimeSelect from "./_components/TimeSelect";
import ThumbNail from "./_components/Thumbnail";
import styles from "./register.module.scss";
import { useRef, ChangeEvent, useState, useEffect } from "react";
import ProductDetail from "./_components/ProductDetail";

export default function Register() {
  const titleRef = useRef<HTMLInputElement>(null);
  const amountRef = useRef<HTMLInputElement>(null);
  const categoryRef = useRef<HTMLInputElement>(null);
  const priceRef = useRef<HTMLInputElement>(null);
  const discountRef = useRef<HTMLInputElement>(null);
  const finalPriceRef = useRef<HTMLInputElement>(null);

  const calFinPrice = (e: ChangeEvent<HTMLInputElement>) => {
    if (priceRef.current && priceRef.current.value) {
      const now = priceRef.current.value as unknown as number;
      const dis = discountRef.current?.value as unknown as number;
      const sale = now * (dis * 0.01);
      const fin = now - sale;
      if (finalPriceRef.current) {
        finalPriceRef.current.value = fin + "";
      }
    } else {
      alert("가격을 먼저 입력해주세요");
      e.target.value = "";
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.title}>판매상품등록</div>
      <div className={styles.inputArea}>
        <div className={styles.item}>
          <div>
            <div>상품명</div>
            <input type="text" className={styles.input} ref={titleRef} />
          </div>
          <div>
            <div>수량</div>
            <input type="number" className={styles.input} ref={amountRef} />
          </div>
          <div>
            <div>카테고리</div>
            <select className={styles.select}>
              <option value="fashion">패션(Fashion)</option>
              <option value="beauty">뷰티(Beauty)</option>
              <option value="food">식품(Food)</option>
              <option value="appliance">가전제품(Appliance)</option>
              <option value="sports">스포츠(Sports)</option>
              <option value="living">생활(Living)</option>
              <option value="pet">반려동물(Pet)</option>
              <option value="travel">여행(Travel)</option>
            </select>
          </div>
        </div>
        <div className={styles.item}>
          <div>
            <div>가격</div>
            <input type="number" className={styles.input} ref={priceRef} />
          </div>
          <div>
            <div>할인율</div>
            <input type="number" max={4} className={styles.input} ref={discountRef} onChange={calFinPrice} />
          </div>
          <div>
            <div>최종할인가</div>
            <input type="number" className={styles.input} readOnly ref={finalPriceRef} />
          </div>
        </div>
        <div className={styles.item}>
          <div>
            <div>DDIB TIME</div>
            <TimeSelect year="2024" month="05" day="14" />
          </div>
        </div>
        <div>
          <div>상품 썸네일</div>
          <ThumbNail />
        </div>
        <div>
          <div>상품 상세사진</div>
          <ProductDetail />
        </div>
      </div>
    </div>
  );
}
