pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
    stages {
        stage('Git Clone') {
            steps {
                withCredentials([string(credentialsId: 'your_credentials_id', variable: 'GITHUB_TOKEN')]) {
                    git branch: 'main',
                        url: 'https://github.com/chldnrwo/moigae-common-private.git',
                        credentialsId: 'your_credentials_id',
                        credentialsVariable: 'GITHUB_TOKEN'
                }
            }
        }
        stage('Set Permissions') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
    }
}