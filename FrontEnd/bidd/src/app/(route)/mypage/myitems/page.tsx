import styles from "./myItems.module.scss";

export default function MyItems() {
  return (
    <div className={styles.container}>
      <div className={styles.addBtn}>상품등록하기</div>
      <div>내상품들</div>
    </div>
  );
}
