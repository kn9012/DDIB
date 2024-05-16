"use client";

import { useQuery } from "@tanstack/react-query";
import { getProductSearch } from "@/app/_api/product";
import { Product } from "@/app/_types/types";
import Link from "next/link";
import ProductItem from "@/app/_components/ProductItem";
import styles from "./searchResult.module.scss";

interface Props {
  keyword: string;
  category: string;
  over: boolean;
}

export default function SearchResult({ keyword, category, over }: Props) {
  const { data } = useQuery<Product[]>({
    queryKey: ["category", keyword, "", over],
    queryFn: () => getProductSearch(keyword, "", over),
  });

  return (
    <div className={styles.itemArea}>
      {data && (
        <>
          {data
            .filter((data) => {
              if (category != "All") {
                return data.category == category;
              }
              return true;
            })
            .map((item, index) => (
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
