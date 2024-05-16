import styles from "./productList.module.scss";
import WeeklyDeal from "./_components/WeeklyDeal";
import Category from "./_components/Category";
import CategoryArea from "./_components/CategoryArea";

export default function ProductList() {
  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles.title}>Weekly Deal</div>
        <WeeklyDeal />
      </div>
      <div className={styles.sectionTwo}>
        <div className={styles.titleTwo}>Category</div>

        <div className={styles.categoryArea}>
          <CategoryArea></CategoryArea>
        </div>
      </div>
    </div>
  );
}
