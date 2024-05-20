import { ReactNode } from "react";
import NavMenu from "@/app/_components/NavMenu";
import styles from "./layout.module.scss";
import RQProvider from "@/app/_components/RQProvider";

type Props = { children: ReactNode };

export default function Layout({ children }: Props) {
  return (
    <>
      <RQProvider>
        <div className={styles.nav}>
          <NavMenu></NavMenu>
        </div>
        <div className={styles.container}>{children}</div>
      </RQProvider>
    </>
  );
}
