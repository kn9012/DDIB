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
import { getDiscount } from "@/app/_utils/commonFunction";
import { A11y, Autoplay } from "swiper/modules";

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
            <div className={styles.itemArea}>
              <Swiper
                initialSlide={0}
                modules={[A11y, Autoplay]}
                effect="coverflow"
                slidesPerView={3}
                autoplay={{ delay: 2000, disableOnInteraction: false }}
                centeredSlides={true}
                slideToClickedSlide={true}
                loop={true}
                coverflowEffect={{
                  rotate: 0,
                  stretch: 0,
                  depth: 10,
                  modifier: 1,
                  slideShadows: false,
                }}
                slideActiveClass={styles.active}
              >
                {weekData.map((item, index) => {
                  return (
                    <SwiperSlide key={index} className={styles.swiperItem}>
                      <Link href={`/products/${item.productId}`}>
                        <div className={styles.container}>
                          <div className={styles.wrapper}>
                            <div className={styles.time}>
                              {`${item.eventStartTime}`.padStart(2, "0")}:00 - {`${item.eventEndTime}`.padStart(2, "0")}:00
                            </div>
                            <Image src={item.thumbnailImage} alt="상품썸네일" fill sizes="auto"></Image>
                            {item.over ? (
                              <>
                                <div className={styles.sold}></div>
                                <div className={styles.soldLogo}>SOLD OUT</div>
                              </>
                            ) : (
                              <>
                                <div className={styles.reserve}></div>
                                <div className={styles.reserveLogo}>{item.eventStartTime}시 오픈</div>
                              </>
                            )}
                          </div>
                          <div className={styles.name}>{item.name}</div>
                          <div className={styles.priceArea}>
                            <div>{item.price}</div>
                            <div>{getDiscount(item.price, item.discount)}</div>
                            <div>{item.discount}%</div>
                          </div>
                        </div>
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
