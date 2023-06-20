pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
    stages {
        stage('Git Clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-signin', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    git branch: 'main',
                        url: 'https://github.com/chldnrwo/moigae-common-private.git',
                        credentialsId: 'github-signin',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD'
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
