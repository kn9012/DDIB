"use client";

import styles from "./productItem.module.scss";
import Image from "next/image";
import { useEffect, useState } from "react";
import { MdCorporateFare } from "react-icons/md";
import { BiSolidBusiness } from "react-icons/bi";
import { getDiscount } from "../_utils/commonFunction";

interface Props {
  thumbnailImage: string;
  companyName: string;
  name: string;
  eventStartTime: string;
  eventEndTime: string;
  price: number;
  totalStock: number;
  stock: number;
  discount: number;
  over: boolean;
}

export default function ProductItem({ thumbnailImage, companyName, name, eventStartTime, eventEndTime, price, totalStock, stock, discount, over }: Props) {
  const [salePrice, setSalePrice] = useState(0);

  useEffect(() => {
    const finPrice = getDiscount(price, discount);
    setSalePrice(finPrice);
  }, []);

  return (
    <div className={styles.containers}>
      <div className={styles.wrapper}>
        <Image src={thumbnailImage} alt="상품썸네일" fill sizes="auto"></Image>
        {over && (
          <>
            <div className={styles.sold}></div>
            <div className={styles.soldLogo}>SOLD</div>
          </>
        )}
      </div>
      <div className={styles.companyMini}>
        <div>
          <BiSolidBusiness />{" "}
        </div>
        <div> {companyName}</div>
      </div>
      <div className={styles.name}>{name}</div>
      <div className={styles.price}>{price}</div>
      <div className={styles.salePrice}>
        <div>{discount}%</div>
        <div>{salePrice.toLocaleString("ko-KR")}</div>
      </div>
    </div>
  );
}
