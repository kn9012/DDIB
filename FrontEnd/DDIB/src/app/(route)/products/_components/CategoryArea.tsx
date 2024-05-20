"use client";

import Category from "./Category";
import { useState } from "react";
import styles from "./categoryArea.module.scss";

export default function CategoryArea() {
  const [category, setCategory] = useState("");

  const CategoryItem = [
    { title: "All", value: "" },
    { title: "Fashion", value: "fashion" },
    { title: "Beauty", value: "beauty" },
    { title: "Food", value: "food" },
    { title: "Appliance", value: "appliance" },
    { title: "Sports", value: "sports" },
    { title: "Living", value: "living" },
    { title: "Pet", value: "pet" },
    { title: "Travel", value: "travel" },
  ];

  return (
    <>
      <div className={styles.category}>
        {CategoryItem.map((item, index) => (
          <div onClick={() => setCategory(item.value)} key={index} className={styles.categoryItem}>
            {item.title}
            {item.value == category && <div className={styles.underBar}></div>}
          </div>
        ))}
      </div>
      <Category category={category}></Category>
    </>
  );
}
