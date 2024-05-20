"use client";

import styles from "./mainSlider.module.scss";
import React, { useState, useRef, useEffect } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import main1 from "../../../public/main1.jpg";
import main2 from "../../../public/main2.jpg";

const MainSlider: React.FC = () => {
  const sliderRef = useRef<Slider | null>(null);

  useEffect(() => {
    const handleWheel = (e: WheelEvent) => {
      e.preventDefault();
      if (e.deltaY < 0) {
        sliderRef.current?.slickPrev();
      } else {
        sliderRef.current?.slickNext();
      }
    };

    const sliderElement = document.querySelector(`.${styles.container}`) as HTMLElement;
    sliderElement?.addEventListener("wheel", handleWheel);

    return () => {
      sliderElement?.removeEventListener("wheel", handleWheel);
    };
  }, []);

  const settings = {
    vertical: true,
    verticalSwiping: true,
    infinite: false,
    speed: 1000,
    cssEase: "cubic-bezier(0.7, 0, 0.3, 1)",
  };

  return (
    <div className={styles.container}>
      <Slider ref={sliderRef} {...settings}>
        <div className={styles.slide}>
          <img src="https://cdn.pixabay.com/photo/2024/05/14/05/38/technology-8760347_1280.jpg" alt="main" />
          <div className={styles.titleOne}>터지지 않는 서버</div>
          <div className={styles.titleTwo}>BIDD</div>
        </div>
        <div className={styles.slide}>
          <img src="https://cdn.pixabay.com/photo/2015/06/24/15/45/laptop-820274_1280.jpg" alt="Desert" />
          <div className={styles.titleThree}>판매 상품을 등록하면</div>
          <div className={styles.titleFour}>터지지 않는 쇼핑몰 DDIB을 빌려드립니다.</div>
        </div>
        <div className={styles.slide}>
          <img src="https://cdn.pixabay.com/photo/2024/03/06/05/55/ai-generated-8615732_1280.png" alt="Erosion" />
          <div className={styles.titleFive}>기업등록하고 BIDD와 함께할까요?</div>
          {/* <div className={styles.titleSix}>BIDD와 함꼐 할까요?</div> */}
        </div>
      </Slider>
    </div>
  );
};

export default MainSlider;
