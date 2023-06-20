pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
    stages {
        stage('Git Clone') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/chldnrwo/moigae-common-private.git',
                    credentialsId: 'github-signin'
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
        stage('Deploy') {
            steps {
                sshagent(credentials: ['moispring']) {
                    sh '''
                        scp -o StrictHostKeyChecking=no deploy.sh ubuntu@172.31.7.214:/home/ubuntu/
                        ssh -o StrictHostKeyChecking=no ubuntu@172.31.7.214 mkdir -p /home/ubuntu/moigae
                        scp -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/test13/build/libs/private-0.0.1-SNAPSHOT.jar ubuntu@172.31.7.214:/home/ubuntu/moigae/
                        ssh -o StrictHostKeyChecking=no -tt ubuntu@172.31.7.214 sh /home/ubuntu/deploy.sh
                    '''
                }
            }
        }

    }
}
