import Image from "next/image";
import styles from "./page.module.scss";
import CheckToken from "@/app/_components/CheckToken";
import GetAlarmToken from "@/app/_components/GetAlarmToken";

export default function Home() {
  return (
    <>
      <CheckToken />
      <GetAlarmToken />

      <main className={styles.main}>
        <div>메인</div>
      </main>
    </>
  );
}
