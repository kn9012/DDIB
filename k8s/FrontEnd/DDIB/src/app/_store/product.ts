import { create } from "zustand";
import { persist } from "zustand/middleware";
import { OrderAddressInfo, OrderProduct } from "@/app/_types/types";

interface Amount {
  amount: number;
  increaseAmount: () => void;
  decreaseAmount: () => void;
  setAmount: (num: number) => void;
}

export const amountStore = create<Amount>((set) => ({
  amount: 1,
  increaseAmount: () => set((state) => ({ amount: state.amount + 1 })),
  decreaseAmount: () => set((state) => ({ amount: state.amount - 1 })),
  setAmount: (num) => set(() => ({ amount: num })),
}));

interface OrderProducts {
  orderInfo: OrderProduct;
  setOrderInfo: (info: OrderProduct) => void;
}

export const orderStore = create(
  persist<OrderProducts>(
    (set) => ({
      orderInfo: {
        productId: 0,
        thumbnailImage: "",
        companyName: "",
        name: "",
        totalAmount: 0,
        price: 0,
        salePrice: 0,
        status: 0,
      },
      setOrderInfo: (info) => set(() => ({ orderInfo: info })),
    }),
    {
      name: "orderStorage",
    }
  )
);

interface OrderAddress {
  addressInfo: OrderAddressInfo;
  setOrderAddressInfo: (info: OrderAddressInfo) => void;
}

export const orderAddressStore = create<OrderAddress>((set) => ({
  addressInfo: {
    receiverName: "",
    receiverPhone: "",
    orderZipcode: "",
    orderRoadAddress: "",
    orderDetailAddress: "",
  },
  setOrderAddressInfo: (info) => set(() => ({ addressInfo: info })),
}));

// interface Timer {
//   timer : boolean;
//   set : ()
// }
