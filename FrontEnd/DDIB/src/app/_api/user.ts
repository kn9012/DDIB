import { ClientAxiosApi } from "@/app/_utils/commons";
import { UserModi } from "@/app/_types/types";

const api = ClientAxiosApi();

// 일반회원 정보 조회
async function getUserInfo(userPk: string) {
  console.log("ddd");
  const { data } = await api.get(`/api/user/${userPk}`);
  console.log("회원정보조회호출");
  console.log(data);
  return data;
}

// 일반회원 정보 수정
async function putUserInfo(userPk: string, sendUser: UserModi) {
  console.log(sendUser);
  try {
    await api.put(`/api/user/${userPk}`, sendUser);
  } catch (error) {
    throw error;
  }
}

// 일반회원로그아웃
async function postUser() {
  const { data } = await api.post(`/api/user/logout`);
  return data;
}

// 일반회원탈퇴
async function deleteUser(userPk: string) {
  await api.delete(`/api/user/${userPk}`);
}

export { getUserInfo, putUserInfo, postUser, deleteUser };
