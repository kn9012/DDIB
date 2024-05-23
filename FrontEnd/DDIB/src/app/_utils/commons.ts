import axios from "axios";
import Cookies from "js-cookie";

//const BASE_URL = "http://localhost:8081";
//const BASE_URL = "https://k10c102.p.ssafy.io";
const BASE_URL = "https://ddib.kro.kr";

const PublicAxiosApi = () => {
  const instance = axios.create({
    baseURL: `${BASE_URL}`,
  });

  instance.defaults.headers.put["Content-Type"] = "application/json";
  return instance;
};

const ClientAxiosApi = () => {
  const instance = axios.create({
    baseURL: `${BASE_URL}`,
  });

  const token = Cookies.get("jwt");
  // instance.defaults.headers.common["Authorization"] =
  instance.defaults.headers.common["Authorization"] = token;

  instance.defaults.headers.post["Content-Type"] = "application/json";
  instance.defaults.headers.delete["Content-Type"] = "application/json";

  return instance;
};

export { PublicAxiosApi, ClientAxiosApi };
