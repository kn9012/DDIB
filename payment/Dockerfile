# # 빌드 관련
# FROM openjdk:17-alpine

# CMD ["./gradlew", "clean", "build"]

# VOLUME /tmp

# # 만들어진 jar파일 복사
# COPY build/libs/*.jar user.jar

# # 실행할 명령어
# # ENTRYPOINT ["java", "-jar", "user.jar" ,"--spring.profiles.active=prod"]

# ENTRYPOINT ["java", "-jar", "user.jar" , "--spring.profiles.active=prod"]


FROM gradle:8.7-jdk17 AS builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY user/build.gradle user/settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY ./user /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:17
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
# COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar
COPY --from=builder /build/build/libs/*.jar user.jar

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                                \
    "java",                                                 \
    "-jar",                                                 \
    "user.jar"             \
    ]
