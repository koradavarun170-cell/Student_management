pipeline {
    agent any

    tools {
        maven 'Maven'   // Name must match a Maven install configured in Manage Jenkins > Tools
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository from GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building with Maven...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging JAR...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy (Run Locally)') {
            steps {
                echo 'Stopping any previous instance and starting the app...'
                sh '''
                    pkill -f student-management-system.jar || true
                    nohup java -jar target/student-management-system.jar > app.log 2>&1 &
                    sleep 10
                    echo "App should now be running on http://localhost:8080/students"
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully! App is live on localhost:8080/students'
        }
        failure {
            echo 'Pipeline failed. Check the logs above.'
        }
    }
}
