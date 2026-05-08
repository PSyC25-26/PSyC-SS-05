pipeline {
    agent any

    environment {
        DB_PASSWORD = 'Calidad2026'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/PSyC25-26/PSyC-SS-05.git'
            }
        }

        // stage('Preparar Base de Datos') {
        //     steps {
        //         dir('rest-service') {
        //             bat "mysql -h 127.0.0.1 -uroot -p${DB_PASSWORD} < src/main/resources/db_calidad.sql"
        //         }
        //     }
        // }

        stage('Unit Tests & Cobertura') {
            steps {
                dir('rest-service') {
                    bat 'mvn clean test jacoco:report'
                }
            }
        }

        stage('Integration & Performance Tests') {
            steps {
                dir('rest-service') {
                    bat 'mvn -Pintegration integration-test'
                    bat 'mvn -Pperformance integration-test'
                }
            }
        }

        stage('Maven Site') {
            steps {
                dir('rest-service') {
                    bat 'mvn site'
                    bat 'mvn -Pperformance resources:copy-resources@copy-perf-report'
                }
            }
        }
    }
}