import { AlarmApply } from "@/app/_types/types";
import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function putAlarmOn(userPk: string, info: AlarmApply) {
  console.log(info);
  await api.put(`/api/notification/subscribe/${userPk}`, info);
}

async function putAlarmOff(userPk: string) {
  await api.put(`/api/notification/subscribe/cancel/${userPk}`);
}

async function getAlarmList(userPk: string) {
  const { data } = await api.get(`/api/notification/${userPk}`);
  console.log(data);
  return data;
}

async function getAlarmCategory(userPk: string) {
  const { data } = await api.get(`/api/notification/subscription/${userPk}`);
  return data;
}

export { putAlarmOn, putAlarmOff, getAlarmList, getAlarmCategory };
