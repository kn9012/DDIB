import { ClientAxiosApi } from "@/app/_utils/commons";

const api = ClientAxiosApi();

async function getTodayList() {
  const { data } = await api.get("/api/product/main");
  return data;
}

async function getProductWeek() {
  console.log("week");
  try {
    const { data } = await api.get("/api/product/all");
    console.log(data);
    return data;
  } catch (error) {
    console.log(error);
  }
}

async function getProductSearch(keyword: string | null, category: string | null, isOver: string | null) {
  console.log(keyword);
  console.log(category);
  const { data } = await api.get(`/api/product/search?keyword=${keyword}&category=${category}&isOver=${isOver}`);
  console.log(data);
  return data;
}

async function getProductDetail(productId: string, userPk: string) {
  const { data } = await api.get(`/api/product/${productId}/${userPk}`);
  return data;
}

async function getWishList(userPk: string) {
  console.log("wishlist");
  const { data } = await api.get(`/api/product/like/user/${userPk}`);
  console.log(data);
  return data;
}

async function postLike(productId: number, userPk: string) {
  const info = {
    productId: productId,
    userId: userPk,
  };
  await api.post(`/api/product/like`, info);
  console.log(info);
  console.log("좋아요");
}

async function deleteLike(productId: number, userPk: string) {
  await api.delete(`/api/product/${productId}/${userPk}`);
  console.log("좋아요 취소");
}

export { getTodayList, getProductWeek, getProductSearch, getProductDetail, getWishList, postLike, deleteLike };
