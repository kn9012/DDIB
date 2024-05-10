package com.ddib.notification.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/ddib-17d11-firebase-adminsdk-cbmvc-22ed36311c.json");

        InputStream refreshToken = resource.getInputStream();

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
