"use client";

import styles from "./orderList.module.scss";
import { useState, useEffect } from "react";
import { OrderDetail } from "@/app/_types/types";
import Link from "next/link";
import ProductOrdered from "@/app/_components/ProductOrdered";
import { useQuery } from "@tanstack/react-query";
import { getOrderList } from "@/app/_api/order";
import { userStore } from "@/app/_store/user";
import Cookies from "js-cookie";

export default function OrderList() {
  const userPk = Cookies.get("num") as string;

  const { data } = useQuery<OrderDetail[]>({
    queryKey: ["orderList", userPk],
    queryFn: () => getOrderList(userPk),
  });

  return (
    <div>
      <div className={styles.subTitle}>Order List</div>
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
                    <div className={styles.orderNum}>
                      주문번호 : {items.orderId}
                    </div>
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
