pipeline {
    agent any

    // tools{
    //     jdk 'jdk17'
    //     maven 'maven3'
    // }

    stages {
       stage('Clean'){
           steps{
               sh "mvn clean -DskipTests"
           }
       }

        stage('Package'){
            steps{
                sh "mvn package -DskipTests"
            }
        }

        stage('Test'){
            steps{
                sh "mvn test"
            }
        }
    }
}
