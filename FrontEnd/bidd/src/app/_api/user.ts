import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getSellerInfo(userPk: number) {
  const { data } = await api.get(`/seller/${userPk}`);
  console.log(data);
  return data;
}

// 기업회원로그아웃
async function postUser() {
  console.log("ddd");
  const { data } = await api.post(`/seller/logout`);
  return data;
}

export { getSellerInfo, postUser };
