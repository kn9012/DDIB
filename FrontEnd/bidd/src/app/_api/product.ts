import { ClientAxiosApi, FileAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();
const fileApi = FileAxiosApi();

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
  try {
    await fileApi.post(`/product/`);
  } catch (error) {
    throw error;
  }
}

export { getSellerProducts, getTimeInfo, postCreateProuct };
