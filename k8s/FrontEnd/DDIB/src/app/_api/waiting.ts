import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getWaitingList(pk: string) {
  console.log("ddd");
  const { data } = await api.get(`/api/v1/queue/rank?user_id=1`);
  console.log(data);
  return data;
}

async function test() {
  console.log("aaaa");
  const { data } = await api.get(`/api/order/1`);
  console.log(data);
}

async function listIn(pk: string) {
  console.log("bbbb");
  await api.get(`/api/v1/queue?user_id=1`);
}

export { getWaitingList, test, listIn };
