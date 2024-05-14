import { ClientAxiosApi, FileAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getSellerProducts(userPk: number) {
  const { data } = await api.get(`/product/seller/${userPk}`);
  console.log(data);
  return data;
}

async function getTimeInfo(year: string, month: string, day: string) {
  const { data } = await api.get(`/product/time/${year}-${month}-${day}`);
  console.log(data);
  return data;
}

async function postCreateProuct() {
  await api.post(`/product/`);
}

export { getSellerProducts, getTimeInfo, postCreateProuct };
