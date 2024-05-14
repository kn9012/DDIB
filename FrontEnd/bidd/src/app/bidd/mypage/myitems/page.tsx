"use client";

import ItemList from "./_components/ItemList";
import styles from "./myItems.module.scss";

import Link from "next/link";

export default function MyItems() {
  return (
    <div className={styles.container}>
      <Link href={`/bidd/register`} className={styles.addBtn}>
        <div>상품등록하기</div>
      </Link>
      <div className={styles.listArea}>
        <ItemList />
      </div>
    </div>
  );
}
