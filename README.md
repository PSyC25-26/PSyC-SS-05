#PSyC Quality Manager

Aplicación de gestión de tareas y calendario desarrollada con Java, Spring Boot, Vaadin, MySQL, Maven

El proyecto incluye:gestión de usuarios, creación y edición de tareas, categorías, calendario, tests automáticos,
integración continua (CI) y generación de informes de calidad.
Requisitos previos

Antes de empezar es necesario instalar:

1. Git

Descargar:

https://git-scm.com/downloads

Instalar con configuración por defecto.

2. Visual Studio Code

Descargar:

https://code.visualstudio.com/
3. Java JDK 21

Descargar:

https://adoptium.net/

Instalar:

Temurin 21
Windows x64

Comprobar instalación:

java -version

Debe aparecer Java 21.

4. Maven

Descargar:

https://maven.apache.org/download.cgi

Descargar Binary zip archive.

Configuración
Descomprimir en:
C:\Maven
Añadir al PATH:
C:\Maven\bin
Reiniciar el ordenador.

Comprobar instalación:

mvn -version
5. MySQL + MySQL Workbench

Descargar:

https://dev.mysql.com/downloads/installer/

Instalar:

MySQL Server
MySQL Workbench

Durante instalación:

usuario: root
contraseña: 1234
6. Node.js

Descargar:

https://nodejs.org/

Instalar versión LTS.

MUY IMPORTANTE:
marcar:

Add to PATH

Reiniciar el ordenador.

Comprobar instalación:

node -v
Descargar el proyecto

Abrir terminal y ejecutar:

git clone https://github.com/PSyC25-26/PSyC-SS-05.git

Entrar en el proyecto:

cd PSyC-SS-05
Abrir proyecto en Visual Studio Code

Abrir VS Code.

Seleccionar:

File → Open Folder

Elegir la carpeta del proyecto.

Configuración de MySQL

Abrir MySQL Workbench.

Conectarse usando:

usuario: root
contraseña: 1234
Crear la base de datos

Abrir una pestaña SQL y ejecutar:

CREATE DATABASE db_calidad;
Configuración del proyecto

Abrir:

rest-service/src/main/resources/application.properties

Comprobar configuración:

spring.application.name=rest-service

spring.datasource.url=jdbc:mysql://localhost:3306/db_calidad?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true

server.port=8080
Instalación de dependencias

Abrir terminal dentro de:

PSyC-SS-05/rest-service

Ejecutar:

mvn clean install

La primera ejecución puede tardar varios minutos.

Ejecutar la aplicación

Ejecutar:

mvn spring-boot:run

Si todo funciona correctamente aparecerá:

Tomcat started on port 8080
Started RestServiceApplication
Abrir la aplicación

Abrir navegador:

http://localhost:8080
Registro e inicio de sesión
Crear usuario

Registrar un usuario desde la interfaz.

Iniciar sesión

IMPORTANTE:
el login utiliza el campo username, no el email.

Ejemplo:

Campo	Valor
Username	maria
Password	1234
Verificar usuarios en MySQL

Abrir MySQL Workbench y ejecutar:

USE db_calidad;

SELECT * FROM usuario;
Ejecutar tests

El proyecto incluye:

unit tests,
integration tests,
pruebas de rendimiento.
Ejecutar todos los tests

Dentro de rest-service ejecutar:

mvn test
Ejecutar build completo con tests
mvn clean package
Informes de cobertura JaCoCo

Después de ejecutar:

mvn clean package

Se genera el informe en:

rest-service/target/site/jacoco/index.html

Abrir ese archivo en navegador para ver:

cobertura de clases,
métodos cubiertos,
porcentaje de tests.
Integración continua (CI)

El proyecto utiliza workflows automáticos definidos en:

.github/workflows/

Incluyen:

compilación automática,
ejecución de tests,
generación de informes.
Jenkins

El proyecto incluye configuración Jenkins mediante:

Jenkinsfile

Permite:

builds automáticos,
ejecución de tests,
integración continua.
Problemas comunes
Error: puerto ocupado

Ejemplo:

Port 8080 was already in use

Solución:

netstat -ano | findstr :8080

Después:

taskkill /PID NUMERO /F
Error: node command not found

Node.js no está correctamente instalado.

Reinstalar Node.js marcando:

Add to PATH

y reiniciar Windows.

Error: No plugin found for prefix 'vaadin'

Ejecutar:

mvn clean install
Error: no POM in this directory

Entrar en:

cd rest-service
Estructura del proyecto
rest-service/
│
├── src/
│   ├── main/
│   └── test/
│
├── target/
│
├── pom.xml
│
└── application.properties
Tecnologías utilizadas
Java 21
Spring Boot
Vaadin
Maven
MySQL
JUnit
JaCoCo
Jenkins
GitHub Actions
Playwright
Comandos importantes
Compilar
mvn clean install
Ejecutar aplicación
mvn spring-boot:run
Ejecutar tests
mvn test
Generar paquete completo
mvn clean package
Abrir aplicación
http://localhost:8080
Estado esperado

La aplicación debe permitir:

iniciar sesión,
gestionar tareas,
visualizar calendario,
gestionar categorías,
almacenar información en MySQL,
ejecutar tests correctamente,
generar informes de calidad.