# 가져올 이미지를 정의
FROM node:20

# 경로 설정하기
WORKDIR /app

# package.json과 package-lock.json 워킹 디렉토리에 복사 (.은 설정한 워킹 디렉토리를 뜻함)
COPY package.json package-lock.json ./

# 명령어 실행 (의존성 설치)
RUN npm install

# 현재 디렉토리의 모든 파일을 도커 컨테이너의 워킹 디렉토리에 복사한다.
COPY . .

# 애플리케이션을 빌드한다.
RUN npm run build

# 3000번 포트 노출
EXPOSE 3000

# 빌드된 애플리케이션을 실행
CMD ["npm", "run", "start"]

# 그리고 Dockerfile로 docker 이미지를 빌드해야한다.
# $ docker build .
