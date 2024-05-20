"use client";

import styles from "./thumbNail.module.scss";
import { useState, useRef } from "react";
import Image from "next/image";
import { productCreateStore } from "@/app/_store/product";

export default function Thumbnail() {
  // 사용자가 불러온 파일 정보를 넣는 값
  const { setThumb } = productCreateStore();
  // 사용자가 불러온 파일의 URL
  const [previewURL, setPreviewURL] = useState<string>("");
  const fileRef = useRef<HTMLInputElement>(null);

  const handleFileOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    if (e.target.files && e.target.files[0]) {
      let file = e.target.files[0];
      let reader = new FileReader();

      reader.onloadend = () => {
        setThumb(file);
        setPreviewURL(reader.result as string);
      };
      console.log(previewURL);

      reader.readAsDataURL(file);
    }
  };

  const handleFileButtonClick = () => {
    fileRef.current?.click();
  };

  return (
    <>
      <div className={styles.thumbBtn} onClick={handleFileButtonClick}>
        <input
          ref={fileRef}
          hidden={true}
          type="file"
          onChange={handleFileOnChange}
        />

        {!previewURL && <div>썸네일선택</div>}
        {previewURL && <div>다시선택</div>}
        <div></div>
      </div>
      {previewURL && (
        <div className={styles.upload}>
          <Image src={previewURL} alt="썸네일이미지" fill sizes="auto"></Image>
        </div>
      )}
    </>
  );
}
