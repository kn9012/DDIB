# 빌드 관련
FROM openjdk:17-alpine

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

# 만들어진 jar파일 복사
COPY build/libs/*.jar payment.jar

# 실행할 명령어
<<<<<<< HEAD
ENTRYPOINT ["java", "-jar", "payment.jar", "--spring.profiles.active=prod"]
=======
# ENTRYPOINT ["java", "-jar", "user.jar" ,"--spring.profiles.active=prod"]

ENTRYPOINT ["java", "-jar", "payment.jar" , "--spring.profiles.active=prod"]
>>>>>>> eba0b6e5e0fc2b98fc2cc7e80a0acc14cbb497f0
