"use client";

import styles from "./searcch.module.scss";
import React, { useRef, useState, useEffect } from "react";
import { IoSearchOutline } from "react-icons/io5";
import SearchResult from "./_components/SearchResult";

export default function Search() {
  const [category, setCategory] = useState("All");

  const CategoryItem = [
    { title: "All" },
    { title: "Fashion" },
    { title: "Beauty" },
    { title: "Food" },
    { title: "Appliance" },
    { title: "Sports" },
    { title: "Living" },
    { title: "Pet" },
    { title: "Travel" },
  ];
  const searchRef = useRef<HTMLInputElement>(null);
  const [result, setResult] = useState("");
  const [showResult, setShowResult] = useState(true);
  const [over, setOver] = useState("");

  const searchKeyWord = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      if (searchRef.current) {
        setResult(searchRef.current.value);
        searchRef.current.value = "";
        setShowResult(false);
      }
    }
  };

  useEffect(() => {
    console.log(over);
  });

  return (
    <div className={styles.container}>
      <div className={styles.searchTitle}>Search</div>
      <div className={styles.searchInput}>
        <input type="text" ref={searchRef} onKeyDown={searchKeyWord} />
        <IoSearchOutline className={styles.icons} />
      </div>
      <div className={styles.resultArea}>
        {!showResult && (
          <>
            <div className={styles.result}>"{result}"로 검색한 결과입니다.</div>
            <div className={styles.category}>
              {CategoryItem.map((item, index) => (
                <div
                  onClick={() => setCategory(item.title)}
                  key={index}
                  className={
                    category === item.title
                      ? styles.selectedItem
                      : styles.categoryItem
                  }
                >
                  {item.title}
                </div>
              ))}
            </div>
            <div>
              <div
                className={over && styles.overYes}
                onClick={() => setOver(false)}
              >
                종료 띱 제외
              </div>
              {/* <div
                className={!over &&  styles.overYes : styles.overNo}
                onClick={() => setOver(true)}
              >
                종료 띱 제외
              </div> */}
            </div>
            <SearchResult keyword={result} category={category} over={over} />
          </>
        )}
      </div>
    </div>
  );
}
