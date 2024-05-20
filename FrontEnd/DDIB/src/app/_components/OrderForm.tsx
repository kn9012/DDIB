"use client";

import ProductOrdered from "@/app/_components/ProductOrdered";
import styles from "./orderForm.module.scss";
import AddressForm, { RefProps } from "./AddressForm";
import { orderStore, orderAddressStore } from "@/app/_store/product";
import { userStore } from "@/app/_store/user";
import { FaDotCircle } from "react-icons/fa";
import { FaRegDotCircle } from "react-icons/fa";
import { useRouter } from "next/navigation";
import { useState, useRef } from "react";
import Image from "next/image";
import kakao from "../../../public/kakaopay.svg";
import { useMutation } from "@tanstack/react-query";
import { postReady, putCancelPay } from "../_api/pay";
import { OrderInfo } from "@/app/_types/types";
import Cookies from "js-cookie";
import SetUserInfo from "./SetUserInfo";

interface Props {
  type: string;
  orderId: string;
  orderDate: string | undefined;
  paymentMethod: string | undefined;
}

export default function OrderForm({ type, orderId, orderDate, paymentMethod }: Props) {
  const saveRef = useRef<RefProps>(null);
  const { orderInfo } = orderStore();
  const { addressInfo } = orderAddressStore();
  const { setUserInfo } = userStore();
  const userPk = Cookies.get("num") as string;
  const router = useRouter();
  const [checkPay, setCheckPay] = useState(false);

  const sendOrder = useMutation({
    mutationFn: async (data: OrderInfo) => {
      return postReady(data, userPk);
    },
    async onSuccess(response) {
      if (Cookies.get("state")) {
        Cookies.remove("state");
      }
      const url = response.next_redirect_pc_url;
      window.location.href = url;
    },
    onError(error) {
      console.error(error);
    },
  });

  const cancelOrder = useMutation({
    mutationFn: async (orderId: string) => {
      return await putCancelPay(orderId);
    },
    async onSuccess(response) {
      console.log("취소성공");
      router.back;
    },
    onError(error) {
      console.error(error);
    },
  });

  const buyItem = (type: string) => {
    if (type === "order") {
      const check = saveRef?.current?.saveAddress();
      if (check && checkPay) {
        const sendInfo = {
          productId: orderInfo.productId,
          itemName: orderInfo.name,
          quantity: orderInfo.totalAmount,
          totalAmount: orderInfo.totalAmount * orderInfo.salePrice,
          taxFreeAmount: 0,
          receiverName: check.receiverName,
          receiverPhone: check.receiverPhone,
          orderRoadAddress: check.orderRoadAddress,
          orderDetailAddress: check.orderDetailAddress,
          orderZipcode: check.orderZipcode,
        };

        console.log(sendInfo);
        sendOrder.mutate(sendInfo);
        //router.push(`/order/complete/${orderInfo.productId}`);
      } else if (!check) {
        alert("주소에 비어있는 칸이 있어요");
      } else if (!checkPay) {
        alert("결제 방식을 선택해주세요");
      }
    } else if (type === "complete") {
      router.push("/");
    } else {
      router.back();
    }
  };

  const cancelItem = () => {
    console.log("취소");
    cancelOrder.mutate(orderId);
  };

  return (
    <div className={styles.main}>
      <SetUserInfo />
      {type === "order" && <div className={styles.title}>주문/결제</div>}
      {type === "complete" && <div className={styles.title}>결제완료</div>}
      {type === "orderView" && (
        <>
          <div className={styles.title}>주문내역상세</div>
          <div>{orderId}</div>
          <div>{orderDate}</div>
        </>
      )}
      <div className={styles.section}>
        <div className={styles.subTitle}>주문상품</div>
        <div className={styles.lineTwo}></div>
        <div className={styles.productInfo}>
          <div>상품정보</div>
          <div>수량</div>
          <div>가격</div>
          <div>구매금액</div>
        </div>
        <div className={styles.lineOne}></div>
        {orderInfo && (
          <div className={styles.productArea}>
            <ProductOrdered
              productId={orderInfo.productId}
              thumbnailImage={orderInfo.thumbnailImage}
              companyName={orderInfo.companyName}
              name={orderInfo.name}
              totalAmount={orderInfo.totalAmount}
              price={orderInfo.price}
              salePrice={orderInfo.salePrice}
              status={orderInfo.status}
            />
          </div>
        )}
        <div className={styles.lineTwo}></div>
      </div>
      <div className={styles.section}>
        <div className={styles.subTitle}>배송주소</div>
        <div className={styles.lineTwo}></div>
        <AddressForm type={type} ref={saveRef} />
        <div className={styles.lineTwo}></div>
      </div>
      <div className={styles.section}>
        <div className={styles.subTitle}>결제금액</div>
        <div className={styles.lineTwo}></div>
        <div className={styles.priceArea}>
          <div className={styles.priceItem}>
            <div>상품금액</div>
            <div>{(orderInfo.price * orderInfo.totalAmount).toLocaleString("ko-KR")}</div>
          </div>
          <div className={styles.priceItem}>
            <div>배송비</div>
            <div>0</div>
          </div>
          <div className={styles.priceItem}>
            <div>할인금액</div>
            <div>{((orderInfo.price - orderInfo.salePrice) * orderInfo.totalAmount).toLocaleString("ko-KR")}</div>
          </div>
          <div className={styles.priceItem}>
            <div>총 상품 금액</div>
            <div>{(orderInfo.salePrice * orderInfo.totalAmount).toLocaleString("ko-KR")}</div>
          </div>
        </div>
        <div className={styles.lineTwo}></div>
      </div>
      <div className={styles.section}>
        <div className={styles.subTitle}>결제방식</div>
        <div className={styles.lineTwo}></div>
        {type === "order" ? (
          <div className={styles.payBtn} onClick={() => setCheckPay((prev) => !prev)}>
            <div>{checkPay ? <FaDotCircle color="#ff5454" /> : <FaRegDotCircle color="gray" />}</div>
            <Image className={styles.kakaologo} src={kakao} alt="kakao"></Image>
            <div>
              kakao<span style={{ fontWeight: "bold" }}>pay</span>
            </div>
          </div>
        ) : (
          <div className={styles.payBtn}>
            <Image className={styles.kakaologo} src={kakao} alt="kakao"></Image>
            <div>
              kakao<span style={{ fontWeight: "bold" }}>pay</span>
            </div>
            {type != "order" && <div>{paymentMethod}</div>}
          </div>
        )}
      </div>
      <div className={styles.btnArea}>
        <div className={styles.buyBtn} onClick={() => buyItem(type)}>
          {type === "order" ? "결제하기" : "확인"}
        </div>
        {type === "orderView" && orderInfo.status === 0 && (
          <>
            <div className={styles.cancelBtn} onClick={cancelItem}>
              취소하기
            </div>
          </>
        )}
      </div>
    </div>
  );
}
