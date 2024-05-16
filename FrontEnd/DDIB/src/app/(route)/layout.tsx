import { ReactNode } from "react";
import NavMenu from "@/app/_components/NavMenu";
import styles from "./layout.module.scss";
import RQProvider from "@/app/_components/RQProvider";

type Props = { children: ReactNode; modal: ReactNode };

export default function Layout({ children, modal }: Props) {
  return (
    <>
      <RQProvider>
        <div>
          <NavMenu></NavMenu>
        </div>
        <div className={styles.container}>
          {children}
          {modal}
        </div>
      </RQProvider>
    </>
  );
}
