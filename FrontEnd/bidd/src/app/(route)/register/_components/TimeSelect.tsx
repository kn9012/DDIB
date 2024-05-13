"use client";

import { useQuery } from "@tanstack/react-query";
import { getTimeInfo } from "@/app/_api/product";

interface Props {
  year: string;
  month: string;
  day: string;
}

export default function TimeSelect({ year, month, day }: Props) {
  interface TimeArray {
    [index: number]: boolean;
  }

  const { data } = useQuery<TimeArray>({
    queryKey: ["timelist", year, month, day],
    queryFn: () => getTimeInfo(year, month, day),
  });

  return <div>시간선택</div>;
}
