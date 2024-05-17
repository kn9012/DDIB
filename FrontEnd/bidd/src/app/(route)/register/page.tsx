"use client";

import TimeSelect from "./_components/TimeSelect";
import ThumbNail from "./_components/ThumbNail";
import styles, { select } from "./register.module.scss";
import { useRef, ChangeEvent, useState, useEffect } from "react";
import ProductDetail from "./_components/ProductDetail";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { BsFillCalendarHeartFill } from "react-icons/bs";
import moment from "moment";
import { useMutation } from "@tanstack/react-query";

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export default function Register() {
  const titleRef = useRef<HTMLInputElement>(null);
  const amountRef = useRef<HTMLInputElement>(null);
  const priceRef = useRef<HTMLInputElement>(null);
  const discountRef = useRef<HTMLInputElement>(null);
  const finalPriceRef = useRef<HTMLInputElement>(null);

  const [value, setValue] = useState(new Date());
  const [showCal, setShowCal] = useState(false);
  const [dateStr, setDateStr] = useState("");

  const [selected, setSelected] = useState("카테고리 선택");

  const handleSelect = (e) => {
    setSelected(e.target.value);
    console.log(selected);
  };

  const handleDateChange = (newValue: Date) => {
    setValue(newValue);
    console.log(newValue);
  };

  useEffect(() => {
    const formattedDate = formatDate(value);
    setDateStr(formattedDate);
  }, [value]);

  const formatDate = (date: Date) => {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = `0${d.getMonth() + 1}`.slice(-2); // 월은 0부터 시작하므로 1을 더해주고, 항상 2자리로 표현
    const day = `0${d.getDate()}`.slice(-2);
    return `${year}-${month}-${day}`;
  };

  const calFinPrice = (e: ChangeEvent<HTMLInputElement>) => {
    if (priceRef.current && priceRef.current.value) {
      const now = priceRef.current.value as unknown as number;
      const dis = discountRef.current?.value as unknown as number;
      if (dis < 0 || dis > 100) {
        alert("1과 100 사이의 수를 입력해주세요");
        if (discountRef.current && finalPriceRef.current) {
          discountRef.current.value = "";
          finalPriceRef.current.value = "";
        }
      } else {
        const sale = now * (dis * 0.01);
        const fin = now - sale;
        if (finalPriceRef.current) {
          finalPriceRef.current.value = fin + "";
        }
      }
    } else {
      alert("가격을 먼저 입력해주세요");
      e.target.value = "";
    }
  };

  const registerItem = () => {};

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
            <select
              className={styles.select}
              onChange={handleSelect}
              value={selected}
            >
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
            <input
              type="number"
              min={0}
              className={styles.input}
              ref={priceRef}
            />
          </div>
          <div>
            <div>할인율</div>
            <input
              type="number"
              min={0}
              max={100}
              className={styles.input}
              ref={discountRef}
              onChange={calFinPrice}
            />
          </div>
          <div>
            <div>최종할인가</div>
            <input
              type="number"
              className={styles.input}
              readOnly
              ref={finalPriceRef}
            />
          </div>
        </div>
        <div className={styles.item}>
          <div>
            <div>DDIB TIME</div>
            <div>
              <div
                className={styles.dateArea}
                onClick={() => setShowCal((prev) => !prev)}
              >
                <input
                  type="text"
                  className={styles.inputDate}
                  readOnly
                  value={dateStr}
                />
                <div className={styles.icons}>
                  <BsFillCalendarHeartFill />
                </div>
              </div>
              {showCal && (
                <Calendar
                  locale="ko"
                  onChange={() => handleDateChange}
                  value={value}
                />
              )}
            </div>
            <TimeSelect date={dateStr} />
          </div>
        </div>
        <div>
          <div className={styles.subTitle}>상품 썸네일</div>
          <ThumbNail />
        </div>
        <div>
          <div className={styles.subTitle}>상품 상세사진</div>
          <ProductDetail />
        </div>
        <div onClick={registerItem}>상품등록하기</div>
      </div>
    </div>
  );
}
