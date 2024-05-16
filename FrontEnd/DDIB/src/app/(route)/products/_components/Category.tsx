"use client";

import ProductItem from "@/app/_components/ProductItem";
import styles from "./category.module.scss";
import Link from "next/link";
import { useQuery } from "@tanstack/react-query";
import { Product } from "@/app/_types/types";
import { getProductSearch } from "@/app/_api/product";
import { useEffect } from "react";

interface Props {
  category: string;
}

export default function Category({ category }: Props) {
  const { data } = useQuery<Product[]>({
    queryKey: ["category", "", category, true],
    queryFn: () => getProductSearch("", category, true),
  });

  useEffect(() => {
    console.log(category);
  }, [category]);

  return (
    <div className={styles.itemArea}>
      {data && (
        <>
          {data.map((item, index) => (
            <Link
              href={`/products/${item.productId}`}
              className={styles.item}
              key={index}
            >
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
          ))}
        </>
      )}
    </div>
  );
}
