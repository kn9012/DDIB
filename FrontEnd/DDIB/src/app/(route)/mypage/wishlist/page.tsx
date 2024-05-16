"use client";

import ProductItem from "@/app/_components/ProductItem";
import Link from "next/link";
import styles from "./wishList.module.scss";
import { useQuery } from "@tanstack/react-query";
import { getWishList } from "@/app/_api/product";
import { userStore } from "@/app/_store/user";
import { Product } from "@/app/_types/types";

export default function WishList() {
  const { user, userPk } = userStore();

  const pk = userPk;

  const { data } = useQuery<Product[]>({
    queryKey: ["wishList", pk],
    queryFn: () => getWishList(pk),
  });

  return (
    <div className={styles.container}>
      {data && (
        <>
          {data.map((item) => {
            return (
              <div key={item.productId} className={styles.listItem}>
                <Link href={`/products/${item.productId}`}>
                  <ProductItem
                    thumbnailImage={item.thumbnailImage}
                    companyName={item.companyName}
                    name={item.name}
                    eventStartTime={item.eventStartTime}
                    eventEndTime={item.eventEndTime}
                    price={item.price}
                    totalStock={item.totalStock}
                    stock={item.stock}
                    discount={item.discount}
                  />
                </Link>
              </div>
            );
          })}
        </>
      )}
    </div>
  );
}
