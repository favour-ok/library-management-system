pipeline {
    agent any

    tools{
        maven 'Maven'
    }

    stages {
       stage('Clean'){
           steps{
               sh "mvn clean -DskipTests"
           }
       }

       stage('Test'){
           steps{
               sh "mvn test"
           }
       }

        stage('Package'){
            steps{
                sh "mvn package -DskipTests"
            }
        }
    }
}