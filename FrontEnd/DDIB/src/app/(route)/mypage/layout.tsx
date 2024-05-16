import { ReactNode } from "react";
import styles from "./layout.module.scss";
import MyPageMenu from "./_components/MyPageMenu";

type Props = { children: ReactNode };

export default function Layout({ children }: Props) {
  return (
    <>
      <div className={styles.container}>
        <div className={styles.myTitle}>MY DIBB</div>
        <MyPageMenu />
        <div className={styles.myPageSection}>{children}</div>
      </div>
    </>
  );
}
