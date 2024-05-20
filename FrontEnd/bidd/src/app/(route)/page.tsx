import styles from "./page.module.scss";
import MainSlider from "../_components/MainSlider";

export default function Home() {
  return (
    <div className={styles.main}>
      <MainSlider />
    </div>
  );
}
