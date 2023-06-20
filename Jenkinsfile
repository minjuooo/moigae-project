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
        				ssh -o StrictHostKeyChecking=no ubuntu@172.31.7.214
                        scp /var/lib/jenkins/workspace/test13/build/libs/private-0.0.1-SNAPSHOT.jar ubuntu@172.31.7.214:/home/ubuntu/moigae
                        ssh -tt ubuntu@172.31.7.214 sh ./deploy.sh
                    '''
        		}
        	}
        }
    }
}
