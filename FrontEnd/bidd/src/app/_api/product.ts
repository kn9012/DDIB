import { ClientAxiosApi, FileAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getSellerProducts(userPk: string) {
  const { data } = await api.get(`/product/seller/${userPk}`);
  console.log(data);
  return data;
}

async function getTimeInfo(date: string) {
  console.log(date);
  const { data } = await api.get(`/product/time/${date}`);
  console.log(data);
  return data;
}

async function postCreateProuct() {
  await api.post(`/product/`);
}

export { getSellerProducts, getTimeInfo, postCreateProuct };
