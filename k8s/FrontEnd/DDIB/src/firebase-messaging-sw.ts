import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { PublicAxiosApi, ClientAxiosApi } from "./app/_utils/commons";
import Cookies from "js-cookie";

const api = ClientAxiosApi();

const firebaseConfig = {
  apiKey: process.env.NEXT_PUBLIC_FIREBASE_API_KEY,
  authDomain: process.env.NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.NEXT_PUBLIC_FIREBASE_PROJECT_ID,
  storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.NEXT_PUBLIC_FIREBASE_APP_ID,
  measurementId: process.env.NEXT_PUBLIC_FIREBASE_MEASUREMENT_ID,
};

const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

const requestPermission = async () => {
  if (Cookies.get("Authorization")) {
    console.log("권한 요청 중...");

    Notification.requestPermission().then((permission) => {
      if (permission === "granted") {
        console.log("알림 권한이 허용됨");

        // FCM 메세지 처리
      } else {
        console.log("알림 권한 허용 안됨");
      }
    });

    const token = await getToken(messaging, {
      vapidKey: process.env.NEXT_PUBLIC_VAPID_KEY,
    });

    if (token) {
      const data = {
        userId: Cookies.get("num"),
        fcmToken: token,
      };

      console.log(data);

      try {
        api.put(`https://ddib.kro.kr/api/user/token`, data);
      } catch (error) {
        console.log("전송실패");
      }

      console.log("token: ", token);
    } else {
      console.log("Can not get Token");
    }

    onMessage(messaging, (payload) => {
      console.log("메시지가 도착했습니다.", payload);
      // ...
    });
  }
};

requestPermission();
