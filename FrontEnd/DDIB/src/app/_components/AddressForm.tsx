"use client";

import { ChangeEvent, useState, useEffect, useRef, forwardRef, useImperativeHandle } from "react";
import styles from "./addressForm.module.scss";
import { FaCheckCircle } from "react-icons/fa";
import { FaRegCheckCircle } from "react-icons/fa";
import { useDaumPostcodePopup } from "react-daum-postcode";
import { orderAddressStore } from "@/app/_store/product";
import { userStore } from "@/app/_store/user";
import { OrderAddressInfo } from "../_types/types";

interface ChildProps {
  type: string;
}

export interface RefProps {
  saveAddress: () => OrderAddressInfo | undefined;
}

const AddressForm = forwardRef<RefProps, ChildProps>((props, ref) => {
  useImperativeHandle(ref, () => ({
    saveAddress,
  }));

  const [myAddress, setMyAddress] = useState(false);

  const nameRef = useRef<HTMLInputElement>(null);
  const numRef = useRef<HTMLInputElement>(null);
  const emailRef = useRef<HTMLInputElement>(null);
  const zipCodeRef = useRef<HTMLInputElement>(null);
  const addressRef = useRef<HTMLInputElement>(null);
  const addressDetailRef = useRef<HTMLInputElement>(null);

  const { user } = userStore();
  const { addressInfo, setOrderAddressInfo } = orderAddressStore();

  function saveAddress() {
    console.log("dddd");
    if (
      nameRef.current?.value &&
      nameRef.current.value.length !== 0 &&
      numRef.current?.value &&
      numRef.current.value.length !== 0 &&
      zipCodeRef.current?.value &&
      zipCodeRef.current.value.length !== 0 &&
      addressRef.current?.value &&
      addressRef.current.value.length !== 0 &&
      addressDetailRef.current?.value &&
      addressDetailRef.current.value.length !== 0
    ) {
      const info = {
        receiverName: nameRef.current.value,
        receiverPhone: numRef.current.value,
        orderZipcode: zipCodeRef.current.value,
        orderRoadAddress: addressRef.current.value,
        orderDetailAddress: addressDetailRef.current.value,
      };

      return info;
    }
  }

  const scriptUrl = "https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
  const open = useDaumPostcodePopup(scriptUrl);

  const toggleHandler = () => {
    open({ onComplete: completeHandler });
  };

  const completeHandler = (data: any) => {
    const { address, zonecode } = data;
    if (zipCodeRef.current) {
      zipCodeRef.current.value = zonecode;
    }
    if (addressRef.current) {
      addressRef.current.value = address;
    }
  };

  const getMyAddress = () => {
    setMyAddress((prev) => !prev);
  };

  const numberFormat = (e: ChangeEvent<HTMLInputElement>) => {
    const rawPhone = e.target.value.replace(/-/g, "");
    let formattedPhone = "";

    if (rawPhone.length < 4) {
      formattedPhone = rawPhone;
    } else if (rawPhone.length < 8) {
      formattedPhone = `${rawPhone.slice(0, 3)}-${rawPhone.slice(3)}`;
    } else if (rawPhone.length < 11) {
      formattedPhone = `${rawPhone.slice(0, 3)}-${rawPhone.slice(3, 7)}-${rawPhone.slice(7)}`;
    } else {
      formattedPhone = `${rawPhone.slice(0, 3)}-${rawPhone.slice(3, 7)}-${rawPhone.slice(7, 11)}`;
    }

    const displayPhone = formattedPhone.length > 0 ? formattedPhone : "";
    if (numRef.current) {
      numRef.current.value = displayPhone;
    }
  };

  useEffect(() => {
    if (props.type === "mypage") {
      setMyAddress(true);
    }
  }, []);

  useEffect(() => {
    if (myAddress) {
      if (nameRef.current && numRef.current && zipCodeRef.current && addressRef.current && addressDetailRef.current) {
        nameRef.current.value = user.name;
        numRef.current.value = user.phone;
        if (emailRef.current) {
          emailRef.current.value = user.email;
        }
        zipCodeRef.current.value = user.zipcode;
        addressRef.current.value = user.roadAddress;
        addressDetailRef.current.value = user.detailAddress;
      }
    } else {
      if (nameRef.current && numRef.current && zipCodeRef.current && addressRef.current && addressDetailRef.current) {
        nameRef.current.value = "";
        numRef.current.value = "";
        zipCodeRef.current.value = "";
        addressRef.current.value = "";
        addressDetailRef.current.value = "";
      }
    }
  }, [myAddress]);

  return (
    <div className={styles.main}>
      {props.type === "order" && (
        <>
          <div className={styles.addressItem}>
            <div>배송지 선택</div>
            <div className={styles.addressCheck} onClick={getMyAddress}>
              {myAddress ? <FaCheckCircle /> : <FaRegCheckCircle color="gray" />}
              <div>기본배송지</div>
            </div>
          </div>
          <div className={styles.line}></div>
        </>
      )}

      <div className={styles.addressItem}>
        <div>{props.type === "mypage" ? "이름" : "받으시는 분"}</div>
        <div>{props.type === "order" || props.type === "mypage" ? <input type="text" className={styles.input} ref={nameRef} maxLength={5}></input> : <div>{addressInfo.receiverName}</div>}</div>
      </div>
      <div className={styles.line}></div>
      <div className={styles.addressItem}>
        <div>휴대폰 번호</div>
        <div>
          {props.type === "order" || props.type === "mypage" ? (
            <>
              <input type="text" className={styles.inputNum} maxLength={13} ref={numRef} onChange={numberFormat}></input>
            </>
          ) : (
            <div>{addressInfo.receiverPhone}</div>
          )}
        </div>
      </div>
      <div className={styles.line}></div>
      {props.type === "mypage" && (
        <>
          <div className={styles.addressItem}>
            <div>이메일</div>
            <div>
              <input type="text" className={styles.inputNum} ref={emailRef} readOnly></input>
            </div>
          </div>
          <div className={styles.line}></div>
        </>
      )}
      <div className={styles.addressItem}>
        <div>주소</div>
        <div className={styles.addressArea}>
          {props.type === "order" || props.type === "mypage" ? (
            <>
              <div>
                <input type="text" className={styles.input} ref={zipCodeRef} readOnly></input>
                <div onClick={toggleHandler}>우편번호찾기</div>
              </div>
              <div>
                <input type="text" className={styles.inputAddress} ref={addressRef} readOnly></input>
                <input type="text" className={styles.inputAddress} ref={addressDetailRef}></input>
              </div>
            </>
          ) : (
            <>
              <div>({addressInfo.orderZipcode})</div>
              <div>{addressInfo.orderRoadAddress}</div>
              <div>{addressInfo.orderDetailAddress}</div>
            </>
          )}
        </div>
      </div>
    </div>
  );
});

AddressForm.displayName = "AddressForm";

export default AddressForm;
