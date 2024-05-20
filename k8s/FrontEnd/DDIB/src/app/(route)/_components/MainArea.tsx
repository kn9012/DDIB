"use client";

import { useState, useEffect } from "react";
import styles from "./mainArea.module.scss";
import TodayItems from "./TodayItems";
import { useQuery } from "@tanstack/react-query";
import { getTodayList } from "@/app/_api/product";
import { TodayList } from "@/app/_types/types";
import MainSlider from "@/app/(route)/_components/MainSlider";

export default function MainArea() {
  const [lastYPos, setLastYPos] = useState(0);
  const [scrollingDown, setScrollingDown] = useState(false);
  const [currentSlide, setCurrentSlide] = useState(0);
  const { data } = useQuery<TodayList>({
    queryKey: ["todayList"],
    queryFn: () => getTodayList(),
  });

  console.log(data);

  const handleSlideChange = (index: number) => {
    setCurrentSlide(index);
  };

  return (
    <div className={styles.main}>
      {data && (
        <>
          {data.todayNotOverProducts.length != 0 && <MainSlider todayList={data.todayNotOverProducts} onSlideChange={handleSlideChange} />}
          <TodayItems todayList={data.todayProducts} />
        </>
      )}
    </div>
  );
}
