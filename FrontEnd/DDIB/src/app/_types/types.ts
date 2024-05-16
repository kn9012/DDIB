export interface User {
  name: string;
  phone: string;
  email: string;
  zipCode: string;
  roadAddress: string;
  detailAddress: string;
}

export interface Product {
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
  companyName: string;
  businessNumber: number;
  companyPhone: number;
  companyEmail: string;
  over: boolean;
}

export interface ProductInfo extends Product {
  liked: boolean;
}

export interface DetailImage {
  productDetailId: number;
  imageUrl: string;
}

export interface OrderProduct {
  productId: number;
  thumbnailImage: string;
  companyName: string;
  name: string;
  totalAmount: number;
  price: number;
  salePrice: number;
  status: number;
}

export interface OrderAddressInfo {
  receiverName: string;
  receiverPhone: string;
  orderZipcode: string;
  orderRoadAddress: string;
  orderDetailAddress: string;
}

export interface Que {
  rank: number;
}

export interface OrderInfo extends OrderAddressInfo {
  productId: number;
  itemName: string;
  quantity: number;
  totalAmount: number;
  taxFreeAmount: number;
}

export interface OrderDetail extends OrderAddressInfo {
  orderId: string;
  orderDate: string;
  status: number;
  companyName: string;
  thumbnailImage: string;
  productName: string;
  quantity: number;
  price: number;
  totalAmount: number;
  paymentMethod: string;
}
