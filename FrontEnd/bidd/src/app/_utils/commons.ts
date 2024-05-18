import axios from "axios";
import Cookies from "js-cookie";

// const BASE_URL = "http://localhost:9090";
const BASE_URL = "https://bidd.kro.kr";
//const BASE_URL = "https://ddib.kro.kr";
//const BASE_URL = "https://k10c102.p.ssafy.io";

const ProductAxiosApi = () => {
  const instance = axios.create({
    baseURL: `https://ddib.kro.kr`,
  });

  const token = Cookies.get("jwt");
  instance.defaults.headers.common["Authorization"] = token;
  instance.defaults.headers.put["Content-Type"] = "application/json";
  return instance;
};

const ClientAxiosApi = () => {
  const instance = axios.create({
    baseURL: `${BASE_URL}/api`,
  });

  const token = Cookies.get("jwt");
  instance.defaults.headers.common["Authorization"] = token;
  instance.defaults.headers.post["Content-Type"] = "application/json";

  return instance;
};

const FileAxiosApi = () => {
  const instanceFile = axios.create({
    baseURL: `${BASE_URL}/api`,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });

  return instanceFile;
};

export { ProductAxiosApi, ClientAxiosApi, FileAxiosApi };
