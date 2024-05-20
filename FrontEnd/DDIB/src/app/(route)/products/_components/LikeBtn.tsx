"use client";

import styles from "./likeBtn.module.scss";
import { useState, useEffect } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { postLike, deleteLike } from "@/app/_api/product";
import { userStore } from "@/app/_store/user";
import cx from "classnames";
import Cookies from "js-cookie";

interface Props {
  productId: number;
  like: boolean;
  likeCnt: number;
}

export default function LikeBtn({ productId, like, likeCnt }: Props) {
  const userPk = Cookies.get("num") as string;
  const [isLiked, setIsLiked] = useState(like);
  const [likeNowCnt, setLikeNowCnt] = useState(likeCnt);

  const heart = useMutation({
    mutationFn: async () => {
      return postLike(productId, userPk);
    },
    async onSuccess(response) {},
    onError(error) {
      console.error(error);
    },
  });

  const unheart = useMutation({
    mutationFn: async () => {
      return deleteLike(productId, userPk);
    },
    async onSuccess(response) {},
    onError(error) {
      console.error(error);
    },
  });

  const likeProduct = async () => {
    if (isLiked) {
      console.log("좋아요취소");
      setIsLiked(false);
      setLikeNowCnt((prev) => prev - 1);
      unheart.mutate();
    } else {
      console.log("좋아요");
      setIsLiked(true);
      setLikeNowCnt((prev) => prev + 1);
      heart.mutate();
    }
  };

  return (
    <div className={styles.container} onClick={() => likeProduct()}>
      <div className={cx(styles.heart, isLiked && styles.active)}></div>
      <div className={styles.cnt}>{likeNowCnt}</div>
    </div>
  );
}
