// "use client";

// import styles from "./mainSlider.module.scss";
// import React, { useState, useRef, useEffect } from "react";
// import Slider from "react-slick";
// import "slick-carousel/slick/slick.css";
// import "slick-carousel/slick/slick-theme.css";

// type SlideshowItem = {
//   id: number;
//   imageUrl: string;
//   title: string;
// };

// const slideshowItems: SlideshowItem[] = [
//   { id: 1, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-2.jpg", title: "Canyon" },
//   { id: 2, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-3.jpg", title: "Desert" },
//   { id: 3, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-4.jpg", title: "Erosion" },
//   { id: 4, imageUrl: "https://raw.githubusercontent.com/supahfunk/supah-codepen/master/canyon-1.jpg", title: "Shape" },
// ];

// const SlideshowComponent: React.FC = () => {
//   const [currentSlide, setCurrentSlide] = useState(0);
//   const maxItems = 4; // 슬라이드 아이템 수
//   const sliderLeftRef = useRef<Slider | null>(null);
//   const sliderRightRef = useRef<Slider | null>(null);

//   const settingsLeft = {
//     vertical: true,
//     verticalSwiping: true,
//     infinite: true,
//     dots: true,
//     speed: 1000,
//     cssEase: "cubic-bezier(0.7, 0, 0.3, 1)",
//     beforeChange: (current: number, next: number) => handleBeforeChange(next),
//   };

//   const settingsRight = {
//     swipe: false,
//     vertical: true,
//     infinite: true,
//     speed: 950,
//     cssEase: "cubic-bezier(0.7, 0, 0.3, 1)",
//     initialSlide: maxItems - 1,
//   };

//   useEffect(() => {
//     const handleWheel = (e: WheelEvent) => {
//       e.preventDefault();
//       if (e.deltaY < 0) {
//         sliderLeftRef.current?.slickPrev();
//       } else {
//         sliderLeftRef.current?.slickNext();
//       }
//     };

//     // sliderElement 대신 slider 컴포넌트의 기본 요소에 접근
//     const sliderElement = document.querySelector(".slideshow-left .slick-track");

//     sliderElement?.addEventListener("wheel", handleWheel);

//     return () => {
//       sliderElement?.removeEventListener("wheel", handleWheel);
//     };
//   }, []);

//   const handleBeforeChange = (next: number) => {
//     setCurrentSlide(next);
//     const targetSlide = maxItems - 1 - next;
//     sliderRightRef.current?.slickGoTo(targetSlide);
//   };

//   return (
//     <div className={styles.container}>
//       <div className="slideshow-left">
//         <Slider ref={sliderLeftRef} {...settingsLeft}>
//           {slideshowItems.map((item) => (
//             <div key={item.id}>
//               <img src={item.imageUrl} alt={item.title} />
//             </div>
//           ))}
//         </Slider>
//       </div>
//       <div className="slideshow-right">
//         <Slider ref={sliderRightRef} {...settingsRight}>
//           {slideshowItems
//             .map((item) => (
//               <div key={item.id}>
//                 <img src={item.imageUrl} alt={item.title} />
//               </div>
//             ))
//             .reverse()}
//         </Slider>
//       </div>
//     </div>
//   );
// };

// export default SlideshowComponent;
