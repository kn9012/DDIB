"use client";

import styles from "./timeSelect.module.scss";
import { useQuery } from "@tanstack/react-query";
import { getTimeInfo } from "@/app/_api/product";
import { useEffect, useState } from "react";
import cx from "classnames";

interface Props {
  year: string;
  month: string;
  day: string;
}

export default function TimeSelect({ year, month, day }: Props) {
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
  const [start, setStart] = useState(0);
  const [end, setEnd] = useState(0);

  const { data } = useQuery<TimeArray>({
    queryKey: ["timelist", year, month, day],
    queryFn: () => getTimeInfo(year, month, day),
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

  const checkPossible = () => {};

  const setTime = (index: number) => {
    // 선택한거를 선택했는지 아니면 처음인지 확인
    console.log(timeObject[index].value);
    if (!timeObject[index].value) {
      console.log("if니");
      // 취소하기가 아니라 선택하기면 처음으로 누른 건지 확인
      if (nowCheck == -1) {
        // 처음이면 처음 값으로 세팅
        setNowCheck(index);
        // 값을 참으로 바꿈
        handleValueChange(index);
      } else {
        // 처음이 아니면 지금 값이랑 값을 비교해서 작은거를 처음으로 큰거를 마지막 타임으로
        const st = Math.min(index, nowCheck);
        const ed = Math.max(index, nowCheck);
        // 두 값의 사이가 다 false 인지 확인
      }
    } else {
      handleValueChange(index);
      console.log("els니");
    }
    console.log(nowCheck);
  };

  useEffect(() => {});

  console.log(data);

  return (
    <>
      {data && (
        <>
          <div>
            <div>시간선택</div>
            <div className={styles.container}>
              {timeObject.map((item, index) => (
                <div key={index} className={styles.items}>
                  {data[index] ? (
                    <div
                      className={cx(
                        styles.possible,
                        item.value && styles.checked
                      )}
                      onClick={() => setTime(index)}
                    >
                      {item.time}
                    </div>
                  ) : (
                    <div className={styles.imPossible}>{item.time}</div>
                  )}
                </div>
              ))}
            </div>
          </div>
        </>
      )}
    </>
  );
}
