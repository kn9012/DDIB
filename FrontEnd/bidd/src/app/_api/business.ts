import { ClientAxiosApi } from "@/app/_utils/commons";
import { BusinessInfo } from "../_types/types";
import axios from "axios";

const api = ClientAxiosApi();

async function putApplyBusiness(sellerId: number, info: BusinessInfo) {
  api.put(`/seller/apply/${sellerId}`);
}

async function postBusinessCheck(key: string) {
  console.log("사업자확인");
  const apiKey = process.env.NEXT_PUBLIC_BUSINESS_API_KEY;
  console.log(apiKey);
  const url: string = `https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=${apiKey}`;
  const { data } = await axios.post(url, {
    b_no: [key],
  });
  console.log(data);
  return data.data[0].b_stt_cd;
}

export { putApplyBusiness, postBusinessCheck };
