pipeline {
    agent any

    environment {
        APP_NAME = "sinkuro-backend"
    }

    tools {
        dockerTool 'docker'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                credentialsId: 'github',
                url: 'https://github.com/sharphurt/sync-lyrics.git'

                sh "ls -lat"
            }
        }

        stage('Check Docker') {
            steps {
                sh 'docker --version'
            }
        }

        stage("Build Docker Image") {
            steps {
                sh "docker image prune -f"
                sh "docker build --force-rm -t ${env.APP_NAME} ."
            }
        }

        stage("Run Docker Compose") {
            steps {
                sh "docker stop ${env.APP_NAME} || true && docker container rm ${env.APP_NAME} || true"
                withCredentials([
                        string(credentialsId: 'mysql-user', variable: 'MYSQL_USER'),
                        string(credentialsId: 'mysql-password', variable: 'MYSQL_PASSWORD'),
                        string(credentialsId: 'spotify-client-id', variable: 'SPOTIFY_CLIENT_ID'),
                        string(credentialsId: 'spotify-client-secret', variable: 'SPOTIFY_CLIENT_SECRET'),
                        string(credentialsId: 'ssl-keystore-password', variable: 'SSL_KEYSTORE_PASSWORD'),
                    ]) {
                        sh """
                        docker run --name ${env.APP_NAME} -d -p 8104:8080 \
                        -v /etc/ssl:/etc/ssl \
                        -e MYSQL_USER=$MYSQL_USER \
                        -e MYSQL_PASSWORD=$MYSQL_PASSWORD \
                        -e SPOTIFY_CLIENT_ID=$SPOTIFY_CLIENT_ID \
                        -e SPOTIFY_CLIENT_SECRET=$SPOTIFY_CLIENT_SECRET \
                        -e SSL_KEYSTORE_PASSWORD=$SSL_KEYSTORE_PASSWORD \
                        -e FRONTEND_URL=https://sinkuro.sharphurt.ru \
                        ${env.APP_NAME}
                        """
                }
            }
        }
    }

    post {
        always {
            script {
                withCredentials([string(credentialsId: 'telegram-bot-token', variable: 'TOKEN'),
                        string(credentialsId: 'telegram-bot-chat-id', variable: 'CHAT_ID')]) {
                sh """
                curl -s -X POST https://api.telegram.org/bot$TOKEN/sendMessage -d chat_id=$CHAT_ID -d parse_mode=markdown -d text="\
                *${env.BUILD_TAG}*: Build Complete!\n\n\
                *Project:* ${env.APP_NAME}\n\
                *Build status:* ${currentBuild.currentResult}\n\n\
                *App URL:* sharphurt.ru:8104/
                "
                """
                }
            }

            cleanWs()
        }
    }
}