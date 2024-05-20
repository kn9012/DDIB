import styles from "./apply.module.scss";
import company from "../../../../public/company.png";
import Image from "next/image";
import ApplyForm from "./_components/ApplyForm";

export default function Apply() {
  return (
    <div className={styles.main}>
      <Image src={company} alt="배경이미지" fill objectPosition="center" />
      <div className={styles.applyArea}>
        <div className={styles.title}>기업신청</div>
        <div className={styles.inputArea}>
          <ApplyForm />
        </div>
      </div>
    </div>
  );
}
