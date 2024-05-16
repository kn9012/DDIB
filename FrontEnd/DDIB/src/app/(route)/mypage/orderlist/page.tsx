"use client";

import styles from "./orderList.module.scss";
import product from "../../../../../public/product.webp";
import { useState, useEffect } from "react";
import { OrderDetail, OrderProduct } from "@/app/_types/types";
import Link from "next/link";
import ProductOrdered from "@/app/_components/ProductOrdered";
import { useQuery } from "@tanstack/react-query";
import { getOrderList } from "@/app/_api/order";
import { userStore } from "@/app/_store/user";

export default function OrderList() {
  const { userPk } = userStore();

  const { data } = useQuery<OrderDetail[]>({
    queryKey: ["orderList", userPk],
    queryFn: () => getOrderList(userPk),
  });

  return (
    <div>
      <div className={styles.subTitle}>주문내역</div>
      <div>
        {data && (
          <>
            {data.map((items) => {
              return (
                <div key={items.orderId} className={styles.productList}>
                  <Link href={`/order/detailview/${items.orderId}`}>
                    <div className={styles.date}>
                      <div>{items.orderDate}</div>
                      {items.status === 0 ? "결제완료" : "취소완료"}
                    </div>
                    <div className={styles.orderNum}>주문번호 : {items.orderId}</div>
                    <ProductOrdered
                      productId={0}
                      thumbnailImage={items.thumbnailImage}
                      companyName={items.companyName}
                      name={items.productName}
                      totalAmount={items.quantity}
                      price={items.price}
                      salePrice={items.totalAmount / items.quantity}
                      status={items.status}
                    />
                  </Link>
                </div>
              );
            })}
          </>
        )}
      </div>
    </div>
  );
}
