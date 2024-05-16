import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const firebaseConfig = {
  apiKey: "AIzaSyCgQxyvJ_L4cjliM8kq1M7xn0yHNWwvHw4",
  authDomain: "ddib-17d11.firebaseapp.com",
  projectId: "ddib-17d11",
  storageBucket: "ddib-17d11.appspot.com",
  messagingSenderId: "985317289307",
  appId: "1:985317289307:web:99769c89469fde3e93fe7e",
  measurementId: "G-7GVS48JWR9",
};

const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

const requestPermission = async () => {
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
    vapidKey: process.env.REACT_APP_VAPID_KEY,
  });

  if (token) {
    console.log("token: ", token);
  } else {
    console.log("Can not get Token");
  }

  onMessage(messaging, (payload) => {
    console.log("메시지가 도착했습니다.", payload);
    // ...
  });
};

requestPermission();
