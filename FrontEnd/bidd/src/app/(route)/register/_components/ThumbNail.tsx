"use client";

import styles from "./thumbNail.module.scss";
import { useState, useRef } from "react";
import Image from "next/image";

export default function Thumbnail() {
  // 사용자가 불러온 파일 정보를 넣는 값
  const [file, setFile] = useState<string>("");
  // 사용자가 불러온 파일의 URL
  const [previewURL, setPreviewURL] = useState<string | ArrayBuffer | null>(null);
  const fileRef = useRef<HTMLInputElement>(null);

  const handleFileOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    if (e.target.files && e.target.files[0]) {
      let file = e.target.files[0];
      let reader = new FileReader();

      reader.onloadend = () => {
        setFile(file.name);
        setPreviewURL(reader.result);
      };
      console.log(previewURL);

      reader.readAsDataURL(file);
    }
  };

  const handleFileButtonClick = (e) => {
    fileRef.current?.click();
  };

  return (
    <div className={styles.upload} onClick={handleFileButtonClick}>
      {previewURL && <Image src={previewURL} alt="썸네일이미지" fill sizes="auto"></Image>}
      <input ref={fileRef} hidden={true} type="file" onChange={handleFileOnChange} />
      <div className={styles.notice}> {file ? "이미지 다시 선택하기" : "이미지 선택하기"}</div>
    </div>
  );
}
