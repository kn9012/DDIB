"use client";

import styles from "./timeSelect.module.scss";
import { useQuery } from "@tanstack/react-query";
import { getTimeInfo } from "@/app/_api/product";
import { useEffect, useState } from "react";
import cx from "classnames";
import { productCreateStore } from "@/app/_store/product";

interface Props {
  date: string;
}

export default function TimeSelect({ date }: Props) {
  const { start, end, checkCnt, setStart, setEnd, setCheckCnt } = productCreateStore();

  interface TimeArray {
    [index: number]: boolean;
  }

  const [timeObject, setTimeObject] = useState([
    { time: "00:00", value: false, book: false },
    { time: "01:00", value: false, book: false },
    { time: "02:00", value: false, book: false },
    { time: "03:00", value: false, book: false },
    { time: "04:00", value: false, book: false },
    { time: "05:00", value: false, book: false },
    { time: "06:00", value: false, book: false },
    { time: "07:00", value: false, book: false },
    { time: "08:00", value: false, book: false },
    { time: "09:00", value: false, book: false },
    { time: "10:00", value: false, book: false },
    { time: "11:00", value: false, book: false },
    { time: "12:00", value: false, book: false },
    { time: "13:00", value: false, book: false },
    { time: "14:00", value: false, book: false },
    { time: "15:00", value: false, book: false },
    { time: "16:00", value: false, book: false },
    { time: "17:00", value: false, book: false },
    { time: "18:00", value: false, book: false },
    { time: "19:00", value: false, book: false },
    { time: "20:00", value: false, book: false },
    { time: "21:00", value: false, book: false },
    { time: "22:00", value: false, book: false },
    { time: "23:00", value: false, book: false },
  ]);

  const [nowCheck, setNowCheck] = useState(-1);

  const { data } = useQuery<TimeArray>({
    queryKey: ["timelist", date],
    queryFn: () => getTimeInfo(date),
  });

  const handleValueChange = (index: number) => {
    setTimeObject((prevState) => {
      const updatedTimeObject = [...prevState]; // 이전 상태 배열을 복사

      // index에 해당하는 요소의 value 값을 변경
      updatedTimeObject[index] = {
        ...updatedTimeObject[index],
        value: !updatedTimeObject[index].value, // 반전시킴
      };

      return updatedTimeObject; // 새로운 배열을 반환하여 상태를 업데이트
    });
  };

  const checkPossible = (min: number, max: number) => {
    for (let i = min; i <= max; i++) {
      if (data) {
        if (data[i] === true) {
          return false;
        }
      }
    }
    return true;
  };

  const setTime = (index: number) => {
    if (checkCnt == 0) {
      setCheckCnt(1);
      handleValueChange(index);
      setNowCheck(index);
      setStart(index);
    } else if (checkCnt == 1) {
      // 두번째 클릭했을 때 사이에 true가 있는지 확인
      let min = Math.min(nowCheck, index);
      let max = Math.max(nowCheck, index);
      const result = checkPossible(min, max);
      // 가능하면 클릭
      if (result) {
        setCheckCnt(2);
        handleValueChange(index);
        for (let i = min + 1; i < max; i++) {
          handleValueChange(i);
        }
        setStart(min);
        setEnd(max + 1);
      } else {
        alert("중간에 예약된 시간이 있습니다. 다시 선택해 주세요");
      }
    } else {
      alert("다시 선택하기를 누르고 시도해주세요");
    }
  };

  const clear = () => {
    if (checkCnt == 1) {
      handleValueChange(start);
      setStart(0);
      setEnd(0);
      setCheckCnt(0);
      setNowCheck(-1);
    } else if (checkCnt == 2) {
      for (let i = start; i < end; i++) {
        handleValueChange(i);
      }
      setStart(0);
      setEnd(0);
      setCheckCnt(0);
      setNowCheck(-1);
    }
  };

  useEffect(() => {}, [date]);

  return (
    <>
      {data && (
        <>
          <div>
            <div className={styles.title}>시간선택</div>

            <div className={styles.info}>시작시간과 끝시간을 선택해주세요. 최소 2시간</div>
            <div className={styles.container}>
              {timeObject.map((item, index) => (
                <div key={index} className={styles.items}>
                  {!data[index] ? (
                    <div className={cx(styles.possible, item.value && styles.checked)} onClick={() => setTime(index)}>
                      {item.time}
                    </div>
                  ) : (
                    <div className={styles.imPossible}>{item.time}</div>
                  )}
                </div>
              ))}
            </div>
            <div className={styles.timeResult}>
              <div>시작 : {start}시</div>
              <div>종료 : {end}시</div>

              <div onClick={() => clear()}>다시선택하기</div>
            </div>
          </div>
        </>
      )}
    </>
  );
}
