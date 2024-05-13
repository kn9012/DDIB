export interface User {
  name: string;
  phone: string;
  email: string;
  zipCode: string;
  roadAddress: string;
  detailAddress: string;
}

export interface BusinessInfo {
  companyName: string;
  businessNumber: number;
  ceoName: string;
  ceoEmail: string;
  ceoPhone: string;
}

export interface BusinessInfos extends BusinessInfo {
  sellerId: number;
}

export interface Product extends BusinessInfo {
  productId: number;
  name: string;
  totalStock: number;
  stock: number;
  eventStartDate: string;
  eventEndDate: string;
  eventStartTime: string;
  eventEndTime: string;
  price: number;
  discount: number;
  thumbnailImage: string;
  category: string;
  details: Array<DetailImage>;
  likeCount: number;
  sellerId: number;
  sellerEamil: string;
  over: boolean;
}
export interface DetailImage {
  productDetailId: number;
  imageUrl: string;
}
