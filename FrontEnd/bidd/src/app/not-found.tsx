import Link from "next/link";
import { NextPage } from "next";

const NotFound: NextPage = () => {
  return (
    <div>
      <div>존재하지 않는 페이지 입니다.</div>
      <Link href="/">홈</Link>
    </div>
  );
};

export default NotFound;
