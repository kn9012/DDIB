// declare module "react-slick" {
//   import * as React from "react";

//   export interface Settings {
//     accessibility?: boolean;
//     adaptiveHeight?: boolean;
//     afterChange?: (currentSlide: number) => void;
//     appendDots?: (dots: React.ReactNode) => React.ReactNode;
//     arrows?: boolean;
//     asNavFor?: React.ReactInstance;
//     autoplay?: boolean;
//     autoplaySpeed?: number;
//     beforeChange?: (currentSlide: number, nextSlide: number) => void;
//     centerMode?: boolean;
//     centerPadding?: string;
//     className?: string;
//     cssEase?: string;
//     customPaging?: (index: number) => React.ReactNode;
//     dots?: boolean;
//     dotsClass?: string;
//     draggable?: boolean;
//     easing?: string;
//     edgeFriction?: number;
//     fade?: boolean;
//     focusOnSelect?: boolean;
//     infinite?: boolean;
//     initialSlide?: number;
//     lazyLoad?: "ondemand" | "progressive";
//     nextArrow?: React.ReactNode;
//     onEdge?: (swipeDirection: string) => void;
//     onInit?: () => void;
//     onLazyLoadError?: (e: any) => void;
//     onReInit?: () => void;
//     pauseOnDotsHover?: boolean;
//     pauseOnFocus?: boolean;
//     pauseOnHover?: boolean;
//     prevArrow?: React.ReactNode;
//     responsive?: Array<{
//       breakpoint: number;
//       settings: Partial<Settings>;
//     }>;
//     rows?: number;
//     rtl?: boolean;
//     slide?: string;
//     slidesPerRow?: number;
//     slidesToScroll?: number;
//     slidesToShow?: number;
//     speed?: number;
//     swipe?: boolean;
//     swipeEvent?: (swipeDirection: string) => void;
//     swipeToSlide?: boolean;
//     touchMove?: boolean;
//     touchThreshold?: number;
//     useCSS?: boolean;
//     useTransform?: boolean;
//     variableWidth?: boolean;
//     vertical?: boolean;
//     verticalSwiping?: boolean;
//     waitForAnimate?: boolean;
//   }

//   export interface SliderMethods {
//     slickPrev(): void;
//     slickNext(): void;
//     // 필요한 경우 다른 메서드들도 추가합니다.
//   }

//   export default class Slider
//     extends React.Component<Settings>
//     implements SliderMethods {}
// }
