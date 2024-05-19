"use client";

import styles from "./mainSlider.module.scss";
import React, { useState, useRef, useEffect } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

type SlideshowItem = {
  id: number;
  imageUrl: string;
  title: string;
};

const slideshowItems: SlideshowItem[] = [
  { id: 1, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-2.jpg", title: "Canyon" },
  { id: 2, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-3.jpg", title: "Desert" },
  { id: 3, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-4.jpg", title: "Erosion" },
  { id: 4, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-1.jpg", title: "Shape" },
];

const MainSlider: React.FC = () => {
  const [currentSlide, setCurrentSlide] = useState(0);
  const maxItems = 4; // 슬라이드 아이템 수
  const sliderLeftRef = useRef<Slider | null>(null);
  const sliderRightRef = useRef<Slider | null>(null);

  useEffect(() => {
    const handleWheel = (e: WheelEvent) => {
      e.preventDefault();
      if (e.deltaY < 0) {
        sliderLeftRef.current?.slickPrev();
        sliderRightRef.current?.slickGoTo(2 - (currentSlide - 1));
      } else {
        sliderLeftRef.current?.slickNext();
        sliderRightRef.current?.slickGoTo(2 - (currentSlide + 1));
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
      <div className="slideshow-left">
        <Slider ref={sliderRightRef} {...settingsLeft}>
          {slideshowItems.map((item) => (
            <div key={item.id}>
              <img src={item.imageUrl} alt={item.title} />
            </div>
          ))}
        </Slider>
      </div>
      <div className="slideshow-right">
        <Slider ref={sliderLeftRef} {...settingsRight}>
          {slideshowItems
            .map((item) => (
              <div key={item.id}>
                <img src={item.imageUrl} alt={item.title} />
              </div>
            ))
            .reverse()}
        </Slider>
      </div>
    </div>
  );
};

export default MainSlider;
