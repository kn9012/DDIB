import axios from "axios";

// const BASE_URL = "http://localhost:9090";
const BASE_URL = "https://k10c102.p.ssafy.io";

const PublicAxiosApi = () => {
  const instance = axios.create({
    baseURL: `${BASE_URL}`,
  });

  instance.defaults.headers.put["Content-Type"] = "application/json";
  return instance;
};

const ClientAxiosApi = () => {
  const instance = axios.create({
    baseURL: `${BASE_URL}/api`,
  });

  //const token = "";
  //instance.defaults.headers.common["Authorization"] = token;
  instance.defaults.headers.common["Authorization"] =
    "BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImVtYWlsIjoia245MDEyQG5hdmVyLmNvbSIsImlhdCI6MTcxNTUxMDIyOSwiZXhwIjoxNzE1NTEzODI5fQ.O5rZT12TyvHVDMkk4Ql1mN012HjkZEplF6Gr6q3ErZs";

  instance.defaults.headers.post["Content-Type"] = "application/json";

  return instance;
};

export { PublicAxiosApi, ClientAxiosApi };
