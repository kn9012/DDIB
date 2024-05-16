"use client";

import styles from "./weekItem.module.scss";
import Image from "next/image";
import { Swiper, SwiperSlide } from "swiper/react";
import { EffectCoverflow } from "swiper/modules";
import "swiper/swiper-bundle.css";
import Link from "next/link";
import { useQuery } from "@tanstack/react-query";
import { getProductWeek } from "@/app/_api/product";
import { Product } from "@/app/_types/types";
import { useState, useEffect } from "react";
import ProductItem from "@/app/_components/ProductItem";

interface Props {
  checkDay: number;
}

export default function WeekItem({ checkDay }: Props) {
  const { data } = useQuery<Product[][]>({
    queryKey: ["weekList"],
    queryFn: () => getProductWeek(),
  });

  const [weekData, setWeekData] = useState<Product[]>();

  useEffect(() => {
    if (data) {
      setWeekData(data[checkDay]);
      console.log(weekData);
    }
  }, [data, checkDay, weekData]);

  return (
    <>
      {weekData && (
        <>
          <div className={styles.container}>
            <div className={styles.section}>
              <div className={styles.wrapper}>
                {weekData.length == 0 ? (
                  <>
                    <div>ㅠㅠ</div>
                  </>
                ) : (
                  <>
                    <Link href={`/products/${weekData[0].productId}`}>
                      <Image
                        src={weekData[0].thumbnailImage}
                        alt="상품썸네일"
                        fill
                        sizes="auto"
                      ></Image>
                    </Link>
                  </>
                )}
              </div>
            </div>
            <div className={styles.itemArea}>
              <Swiper
                modules={[EffectCoverflow]}
                effect="coverflow"
                slidesPerView={3}
                centeredSlides={true}
                slideToClickedSlide={true}
                coverflowEffect={{
                  rotate: 0,
                  stretch: 0,
                  depth: 10,
                  modifier: 1,
                  slideShadows: false,
                }}
              >
                {weekData.map((item, index) => {
                  return (
                    <SwiperSlide key={index} className={styles.swiperItem}>
                      <Link href={`/products/${item.productId}`}>
                        <ProductItem
                          thumbnailImage={item.thumbnailImage}
                          companyName={item.companyName}
                          name={item.name}
                          eventStartTime={item.eventStartTime}
                          eventEndTime={item.eventEndTime}
                          price={item.price}
                          totalStock={item.totalStock}
                          stock={item.stock}
                          discount={item.discount}
                        />
                      </Link>
                    </SwiperSlide>
                  );
                })}
              </Swiper>
            </div>
          </div>
        </>
      )}
    </>
  );
}
