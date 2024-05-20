"use client";

import styles from "./mainSlider.module.scss";
import React, { useState, useRef, useEffect } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useQuery } from "@tanstack/react-query";
import { Product } from "@/app/_types/types";
import TimeCount from "@/app/_components/TimeCount";
import { getDiscount } from "@/app/_utils/commonFunction";

import Link from "next/link";
import Image from "next/image";

type SlideshowItem = {
  id: number;
  imageUrl: string;
  title: string;
};

const slideshowItems: Product[] = [
  {
    productId: 23,
    name: "나이키 조던",
    totalStock: 1000,
    stock: 1000,
    eventStartDate: "2024-05-17T14:00:00",
    eventEndDate: "2024-05-17T16:00:00",
    eventStartTime: "14",
    eventEndTime: "16",
    price: 10000,
    discount: 10.0,
    thumbnailImage: "https://iandwe.s3.ap-northeast-2.amazonaws.com/thumbnail/OQcNkT4d",
    category: "Fashion",
    details: [
      {
        productDetailId: 23,
        imageUrl: "https://iandwe.s3.ap-northeast-2.amazonaws.com/details/hL5SqOBk",
      },
    ],
    likeCount: 1,
    sellerId: 1,
    sellerEmail: "sarah@dunst.com",
    companyName: "joonseong",
    businessNumber: 101010101,
    ceoName: "Tom",
    ceoEmail: "306yyy@naver.com",
    ceoPhone: "1043200933",
    over: false,
  },
  {
    productId: 23,
    name: "name3",
    totalStock: 1000,
    stock: 1000,
    eventStartDate: "2024-05-17T17:00:00",
    eventEndDate: "2024-05-17T18:00:00",
    eventStartTime: "17",
    eventEndTime: "18",
    price: 10000,
    discount: 20.0,
    thumbnailImage: "https://iandwe.s3.ap-northeast-2.amazonaws.com/thumbnail/egqHlHZG",
    category: "Fashion",
    details: [
      {
        productDetailId: 23,
        imageUrl: "https://iandwe.s3.ap-northeast-2.amazonaws.com/details/hL5SqOBk",
      },
    ],
    likeCount: 1,
    sellerId: 1,
    sellerEmail: "sarah@dunst.com",
    companyName: "joonseong",
    businessNumber: 101010101,
    ceoName: "Tom",
    ceoEmail: "306yyy@naver.com",
    ceoPhone: "1043200933",
    over: false,
  },
  {
    productId: 23,
    name: "name3",
    totalStock: 1000,
    stock: 1000,
    eventStartDate: "2024-05-17T19:00:00",
    eventEndDate: "2024-05-17T20:00:00",
    eventStartTime: "19",
    eventEndTime: "20",
    price: 10000,
    discount: 15.0,
    thumbnailImage: "https://iandwe.s3.ap-northeast-2.amazonaws.com/thumbnail/egqHlHZG",
    category: "Fashion",
    details: [
      {
        productDetailId: 23,
        imageUrl: "https://iandwe.s3.ap-northeast-2.amazonaws.com/details/hL5SqOBk",
      },
    ],
    likeCount: 1,
    sellerId: 1,
    sellerEmail: "sarah@dunst.com",
    companyName: "joonseong",
    businessNumber: 101010101,
    ceoName: "Tom",
    ceoEmail: "306yyy@naver.com",
    ceoPhone: "1043200933",
    over: false,
  },
];

interface Props {
  todayList: Product[];
  onSlideChange: (index: number) => void;
}

export default function MainSlider({ todayList, onSlideChange }: Props) {
  const [currentSlide, setCurrentSlide] = useState(0);
  const maxItems = todayList.length; // 슬라이드 아이템 수
  const sliderLeftRef = useRef<Slider | null>(null);
  const sliderRightRef = useRef<Slider | null>(null);

  useEffect(() => {
    onSlideChange(currentSlide); // currentSlide가 변경될 때 부모 함수를 호출
  }, [currentSlide, onSlideChange]);

  useEffect(() => {
    const handleWheel = (e: WheelEvent) => {
      e.preventDefault();
      if (e.deltaY < 0) {
        sliderLeftRef.current?.slickPrev();
        sliderRightRef.current?.slickGoTo(2 - (currentSlide - 1));
      } else {
        sliderLeftRef.current?.slickNext();
        sliderRightRef.current?.slickGoTo(2 - (currentSlide + 1));
        if (currentSlide === maxItems - 1) {
          // currentSlide가 2일 때 아래로 스크롤이 발생하면, 화면을 100vh 아래로 이동
          window.scrollTo({
            top: window.innerHeight,
            behavior: "smooth",
          });
        }
      }
    };

    const sliderElement = document.querySelector(".slideshow-left") as HTMLElement;
    const sliderRightElement = document.querySelector(".slideshow-right") as HTMLElement;
    sliderElement?.addEventListener("wheel", handleWheel);
    sliderRightElement?.addEventListener("wheel", handleWheel);

    return () => {
      sliderElement?.removeEventListener("wheel", handleWheel);
      sliderRightElement?.removeEventListener("wheel", handleWheel);
    };
  }, [currentSlide, maxItems]);

  const settingsLeft = {
    vertical: true,
    verticalSwiping: true,
    infinite: false,
    speed: 1000,
    cssEase: "cubic-bezier(0.7, 0, 0.3, 1)",
    beforeChange: (current: number, next: number) => {
      setCurrentSlide(next);
      sliderRightRef.current?.slickGoTo(maxItems - 1 - next);
    },
  };

  const settingsRight = {
    vertical: true,
    swipe: false,
    infinite: false,
    speed: 950,
    cssEase: "cubic-bezier(0.7, 0, 0.3, 1)",
    beforeChange: (current: number, next: number) => {
      sliderLeftRef.current?.slickGoTo(maxItems - 1 - next);
    },
    initialSlide: maxItems - 1,
  };

  return (
    <div className={styles.container}>
      <div className="slideshow-right">
        <Slider ref={sliderRightRef} {...settingsRight}>
          {todayList
            .map((item, index) => (
              <div className={styles.background} key={index}>
                <div className={styles.timer}>
                  <TimeCount startTime={item.eventStartDate} />
                  <div className={styles.name}>{item.name}</div>
                  <div className={styles.priceArea}>
                    <div>{item.price.toLocaleString("ko-KR")}</div>
                    <div>{getDiscount(item.price, item.discount).toLocaleString("ko-KR")}</div>
                  </div>
                </div>
              </div>
            ))
            .reverse()}
        </Slider>
      </div>
      <div className="slideshow-left">
        <Slider ref={sliderLeftRef} {...settingsLeft}>
          {todayList.map((item, index) => (
            <div className={styles.background} key={index}>
              <div className={styles.eventTime}>
                <div>{item.eventStartTime}:00</div>
                <div>~</div>
                <div>{item.eventEndTime}:00</div>
              </div>
              <div className={styles.imageArea}>
                <Link href={`/products/${item.productId}`}>
                  <div className={styles.wrapper}>
                    <Image src={item.thumbnailImage} alt="썸네일" fill sizes=""></Image>
                  </div>
                  <div className={styles.hover}>
                    <div>Detail View ---&gt;</div>
                  </div>
                </Link>
              </div>
            </div>
          ))}
        </Slider>
      </div>
      <div className={styles.discount}>{todayList[currentSlide].discount}%</div>
    </div>
  );
}
