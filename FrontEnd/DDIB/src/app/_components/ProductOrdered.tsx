"use client";

import styles from "./productOrdered.module.scss";
import Image from "next/image";
import { OrderProduct } from "@/app/_types/types";
import { BiSolidBusiness } from "react-icons/bi";
import { useEffect } from "react";

export default function ProductOrdered({
  productId,
  thumbnailImage,
  companyName,
  name,
  totalAmount,
  price,
  salePrice,
  status,
}: OrderProduct) {
  useEffect(() => {
    console.log(thumbnailImage);
  }, []);

  return (
    <div className={status === 0 ? styles.mainPink : styles.mainGray}>
      <div className={styles.orderedItem}>
        <div>
          <div className={styles.thumbnail}>
            <Image
              src={thumbnailImage}
              alt="상품썸네일"
              fill
              sizes="auto"
            ></Image>
          </div>
        </div>
        <div>
          <div className={styles.companyMini}>
            <div>
              <BiSolidBusiness />
            </div>
            <div>{companyName}</div>
          </div>
          <div>{name}</div>
        </div>
      </div>
      <div className={styles.totalAmount}>{totalAmount}</div>
      <div className={styles.price}>
        <div>{price.toLocaleString("ko-KR")}</div>
        <div>{salePrice.toLocaleString("ko-KR")}</div>
      </div>
      <div className={styles.totalPrice}>
        {(salePrice * totalAmount).toLocaleString("ko-KR")}
      </div>
    </div>
  );
}
