import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getSellerInfo(userPk: number) {
  const { data } = await api.get(`/seller/${userPk}`);
  console.log(data);
  return data;
}

export { getSellerInfo };
