"use client";

import styles from "./productDetail.module.scss";
import { useState, useRef, useEffect } from "react";
import Image from "next/image";
import { productCreateStore } from "@/app/_store/product";

export default function ProductDetail() {
  const [imageViewValue, setImageView] = useState<string[]>([]);
  const fileRef = useRef<HTMLInputElement>(null);

  const { setDetail, resetDetails } = productCreateStore();

  const handleFileOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    if (e.target.files && e.target.files[0]) {
      let file = e.target.files[0];
      let reader = new FileReader();

      reader.onloadend = () => {
        setDetail(file);
        // 이전 URL 목록에 새로운 미리보기 URL을 추가합니다.
        setImageView((prevURLs) => [...prevURLs, reader.result as string]);
        console.log(imageViewValue);
      };

      reader.readAsDataURL(file);
    }
  };

  const handleFileButtonClick = (e) => {
    fileRef.current?.click();
  };

  const clearFile = () => {
    resetDetails();
    setImageView([]);
  };

  useEffect(() => console.log("ddd"), [imageViewValue]);

  return (
    <div>
      <div>
        <input
          ref={fileRef}
          hidden={true}
          type="file"
          onChange={handleFileOnChange}
        />

        <div onClick={handleFileButtonClick} className={styles.upload}>
          상세이미지 선택
        </div>
        {imageViewValue.length != 0 && (
          <div className={styles.upload} onClick={clearFile}>
            다시 선택하기
          </div>
        )}
        {/* <div className={styles.notice}> {imageFilesValue.length  ? "이미지 다시 선택하기" : "이미지 선택하기"}</div> */}
      </div>
      <div className={styles.details}>
        <>
          {imageViewValue.map((item, index) => (
            <div key={index} className={styles.frame}>
              <Image src={item} alt="Thumbnail Detail" layout="fill" />
            </div>
          ))}
        </>
      </div>
    </div>
  );
}
