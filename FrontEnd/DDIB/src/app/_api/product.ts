import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getProductWeek() {
  console.log("week");
  const { data } = await api.get("/api/product/all");
  console.log(data);
  return data;
}

async function getProductSearch(
  keyword: string | null,
  category: string | null,
  isOver: boolean
) {
  console.log(keyword);
  console.log(category);
  const { data } = await api.get(
    `/api/product/search?keyword=${keyword}&category=${category}&isOver=${isOver}`
  );
  console.log(data);
  return data;
}

async function getProductDetail(productId: string, userPk: number) {
  const { data } = await api.get(`/api/product/${productId}/${userPk}`);
  return data;
}

async function getWishList(userPk: number) {
  console.log("wishlist");
  const { data } = await api.get(`/api/product/like/user/${userPk}`);
  console.log(data);
  return data;
}

async function postLike() {}

async function deleteLike(productId: string, userPk: number) {
  await api.delete(`/api/product/${productId}/${userPk}`);
}

export { getProductWeek, getProductSearch, getProductDetail, getWishList };
