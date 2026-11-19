package com.ddib.notification.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    /**
     * Firebase 서비스 계정 키 위치. 키 파일은 저장소에 커밋하지 않고
     * FCM_CREDENTIALS_PATH 환경 변수(예: file:/etc/ddib/firebase-adminsdk.json)로 주입한다.
     * 형식은 firebase/firebase-adminsdk.example.json 참고.
     */
    @Value("${fcm.credentials.path:classpath:firebase/firebase-adminsdk.json}")
    private Resource credentialsResource;

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream refreshToken = credentialsResource.getInputStream();

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> fireBaseAppList = FirebaseApp.getApps();

        if (fireBaseAppList != null && !fireBaseAppList.isEmpty()) {
            for (FirebaseApp app : fireBaseAppList) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }
        } else {
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken)).build();
            firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        }

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
