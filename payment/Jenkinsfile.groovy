pipeline {
    agent any
<<<<<<< HEAD
    tools {
        gradle 'Gradle 8.5'
    }
=======

>>>>>>> eba0b6e5e0fc2b98fc2cc7e80a0acc14cbb497f0
    environment {
        DOCKER_IMAGE_NAME = 'kimyusan/ddib_payment'
        DOCKERFILE_PATH = './payment/Dockerfile'
        CONTAINER_NAME = 'ddib_payment'
        REGISTRY_CREDENTIAL = 'dockerhub_IdPwd'
        DOCKER_IMAGE = ''
        DOCKER_IMAGE_TAG = 'latest'
    }
    stages {
        stage('GitLab Clone') {
            steps {
<<<<<<< HEAD
                git branch : 'dev-payment', credentialsId: 'gitlab_access_token', url: 'https://lab.ssafy.com/s10-final/S10P31C102.git'
            }
        }
=======
                git branch : 'dev-payment', credentialsId: 'jenkins', url: 'https://lab.ssafy.com/s10-final/S10P31C102.git'
            }
        }

>>>>>>> eba0b6e5e0fc2b98fc2cc7e80a0acc14cbb497f0
        stage('Gradle Build') {
            steps {
                echo 'Building..'
                dir('./payment') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean bootjar'
                }
            }
        }
        stage('Docker Build Image') {
            steps {
                dir('./payment') {
                    script {
                        DOCKER_IMAGE = docker.build("${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}", "-f Dockerfile .")
                    }
                }
            }
        }
        stage('Push Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry('', REGISTRY_CREDENTIAL) {
                        DOCKER_IMAGE.push()
                    }
                }
            }
        }
        stage('Delete Previous back Docker Container'){
            steps {
                script {
                    // 컨테이너가 실행중이 아니거나 중지되어 있는 경우 아무런 동작하지 않고 넘어가도록
                    sh "docker stop ${CONTAINER_NAME} || true"

<<<<<<< HEAD
//                    def exitedContainers = sh(script: "docker ps --filter status=exited -q", returnStdout: true).trim()
//                    if (exitedContainers) {
//                        sh "docker rm ${exitedContainers}"
//                    } else {
//                        echo "No exited containers to remove."
//                    }
=======
                   def exitedContainers = sh(script: "docker ps --filter status=exited -q", returnStdout: true).trim()
                   if (exitedContainers) {
                       sh "docker rm ${exitedContainers}"
                   } else {
                       echo "No exited containers to remove."
                   }
>>>>>>> eba0b6e5e0fc2b98fc2cc7e80a0acc14cbb497f0
                }
            }
        }

        stage('Prune Docker Object'){
            steps {
                echo '##### delete stopped containers, networks, volumes, images, cache... #####'
                script {
//                    sh "docker image prune -f"
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
<<<<<<< HEAD
                    sh 'docker run -d --name ${CONTAINER_NAME} -p 8082:8082 ${DOCKER_IMAGE_NAME}'
=======
                    sh 'docker run -d --name ${CONTAINER_NAME} -p 8083:8083 ${DOCKER_IMAGE_NAME}'
>>>>>>> eba0b6e5e0fc2b98fc2cc7e80a0acc14cbb497f0
                }
            }
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> eba0b6e5e0fc2b98fc2cc7e80a0acc14cbb497f0
