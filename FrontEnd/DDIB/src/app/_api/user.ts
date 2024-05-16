import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getUserInfo() {
  console.log("ddd");
  const { data } = await api.get(`/api/user`);
  return data;
}

// 뒤에 쿼리 필요
async function putUserInfo() {
  const { data } = await api.get(`/api/user`);
  return data;
}

// 뒤에 쿼리 필요
async function deleteUser() {
  await api.delete(`api/user`);
}

export { getUserInfo, putUserInfo, deleteUser };
