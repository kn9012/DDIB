pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'kimyusan/bidd_front'
        DOCKERFILE_PATH = './FrontEnd/bidd/Dockerfile'
        CONTAINER_NAME = 'bidd_front'
        REGISTRY_CREDENTIAL = 'dockerhub_IdPwd'
        DOCKER_IMAGE = ''
        DOCKER_IMAGE_TAG = 'latest'


    }

    stages {
        //프로젝트 클론
        stage('GitLab Clone') {
            steps {
                git branch : 'dev-front2', credentialsId: 'jenkins', url: 'https://lab.ssafy.com/s10-final/S10P31C102.git'
            }
        }

        //Dockerfile로 생성된 빌드 파일로 도커 이미지 생성
        stage('Docker Build Image') {
            steps {
                dir('./FrontEnd/bidd') {
                    script {
                        DOCKER_IMAGE = docker.build("${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}", "-f Dockerfile .")
                    }
                }
            }
        }

        //이미지 도커 허브에 올리기
        stage('Push Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry('', REGISTRY_CREDENTIAL) {
                        DOCKER_IMAGE.push()
                    }
                }
            }
        }

        stage('Delete Previous frontend Docker Container'){
            steps {
                script {
                    // 컨테이너가 실행중이 아니거나 중지되어 있는 경우 아무런 동작하지 않고 넘어가도록
                    sh "docker stop ${CONTAINER_NAME} || true"
                }
            }
        }

        stage('Prune Docker Object'){
            steps {
                echo '##### delete stopped containers, networks, volumes, images, cache... #####'
                script {
                    sh "docker system prune --volumes -f"
                }
            }
        }

        stage('Pull from DockerHub') {
            steps {
                script {
                    sh 'docker pull ${DOCKER_IMAGE_NAME}'
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    sh 'docker run -d --name ${CONTAINER_NAME} -p 3001:3001 ${DOCKER_IMAGE_NAME}'
                }
            }
        }

    }
}