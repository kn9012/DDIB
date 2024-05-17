"use client";

import styles from "./itemList.module.scss";
import { useQuery } from "@tanstack/react-query";
import { getSellerProducts } from "@/app/_api/product";
import { Product } from "@/app/_types/types";
import Image from "next/image";
import Cookies from "js-cookie";

export default function ItemList() {
  // const { user, userPk } = userStore();

  const pk = Cookies.get("num") as string;

  const { data } = useQuery<Product[]>({
    queryKey: ["ProductList", pk],
    queryFn: () => getSellerProducts(pk),
  });

  return (
    <>
      {data && (
        <>
          {data.map((item) => {
            return (
              <div key={item.productId} className={styles.container}>
                <div className={styles.timeArea}>
                  <div>DDIB TIME</div>
                  <div>-</div>
                  <div>{item.eventStartDate.substring(0, 10)}</div>
                  <div>{item.eventStartTime}:00</div>
                  <div>-</div>
                  <div>{item.eventEndTime}:00</div>
                </div>
                <div className={styles.product}>
                  <div>
                    <div className={styles.wrapper}>
                      <Image src={item.thumbnailImage} alt="상품썸네일" fill sizes="auto"></Image>
                    </div>
                  </div>
                  <div className={styles.infoArea}>
                    <div className={styles.item}>
                      <div className={styles.productTitle}>상품명</div>
                      <div>{item.name}</div>
                    </div>
                    <div className={styles.item}>
                      <div>가격</div>
                      <div>{item.price}</div>
                    </div>
                    <div className={styles.item}>
                      <div>할인율</div>
                      <div>{item.discount}</div>
                    </div>
                    <div className={styles.item}>
                      <div>수량</div>
                      <div>{item.stock}</div>
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </>
      )}
    </>
  );
}
