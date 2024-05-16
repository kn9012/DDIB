import { ReactNode } from "react";
import NavMenu from "@/app/_components/NavMenu";
import styles from "./layout.module.scss";
import RQProvider from "../_components/RQProvider";

type Props = { children: ReactNode };

export default function Layout({ children }: Props) {
  return (
    <>
      <RQProvider>
        <div>
          <NavMenu></NavMenu>
        </div>
        <div>{children}</div>
      </RQProvider>
    </>
  );
}
