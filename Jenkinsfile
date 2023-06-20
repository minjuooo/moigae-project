pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
    stages {
        stage('Git Clone') {
            steps {
                withCredentials([string(credentialsId: 'github-signin', variable: 'GITHUB_TOKEN')]) {
                    git branch: 'main',
                        url: 'https://github.com/chldnrwo/moigae-common-private.git',
                        credentialsId: 'github-signin',
                        variable: 'GITHUB_TOKEN'
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
