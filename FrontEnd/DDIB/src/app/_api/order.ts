import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getOrderDetail(orderId: string) {
  console.log("orderDetail");
  const { data } = await api.get(`/api/order/detail/${orderId}`);
  console.log(data);
  return data;
}

async function getOrderList(userPk: number) {
  const { data } = await api.get(`/api/order/${userPk}`);
  console.log(data);
  return data;
}

export { getOrderDetail, getOrderList };
