"use client";

import { useState } from "react";
import Week from "./Week";
import WeekItem from "./WeekItem";

export default function WeeklyDeal() {
  const [checkDay, setCheckDay] = useState(0);

  const changeDay = (num: number) => {
    setCheckDay(num);
  };

  return (
    <>
      <Week checkDay={checkDay} changeDay={changeDay} />
      <WeekItem checkDay={checkDay} />
    </>
  );
}
