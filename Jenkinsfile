pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            allure(
                includeProperties: false,
                results: [[path: 'allure-results']]
            )
        }
    }
}
