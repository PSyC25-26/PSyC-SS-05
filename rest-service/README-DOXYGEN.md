# PSyC Quality Manager

Aplicación de gestión de tareas y calendario desarrollada con Java, Spring Boot, Vaadin, MySQL y Maven.

El proyecto incluye gestión de usuarios, creación y edición de tareas, categorías, calendario, tests automáticos, integración continua (CI) y generación de informes de calidad.

---

## Requisitos previos

Antes de empezar es necesario instalar:

### 1. Git

Descargar: https://git-scm.com/downloads

Instalar con configuración por defecto.

### 2. Visual Studio Code

Descargar: https://code.visualstudio.com/

### 3. Java JDK 21

Descargar: https://adoptium.net/

Instalar Temurin 21 (Windows x64).

Comprobar instalación:

```
java -version
```

Debe aparecer Java 21.

### 4. Maven

Descargar: https://maven.apache.org/download.cgi (Binary zip archive)

Descomprimir en `C:\Maven` y añadir `C:\Maven\bin` al PATH. Reiniciar el ordenador.

Comprobar instalación:

```
mvn -version
```

### 5. MySQL + MySQL Workbench

Descargar: https://dev.mysql.com/downloads/installer/

Instalar MySQL Server y MySQL Workbench.

Durante la instalación usar:

```
usuario:    root
contraseña: 1234
```

### 6. Node.js

Descargar: https://nodejs.org/ (versión LTS)

> **Importante:** marcar la opción **Add to PATH** durante la instalación. Reiniciar el ordenador.

Comprobar instalación:

```
node -v
```

---

## Instalación

### Descargar el proyecto

```
git clone https://github.com/PSyC25-26/PSyC-SS-05.git
cd PSyC-SS-05
```

### Abrir en Visual Studio Code

Ir a **File → Open Folder** y seleccionar la carpeta del proyecto.

### Configurar MySQL

Abrir MySQL Workbench, conectarse con usuario `root` / contraseña `1234` y ejecutar:

```sql
CREATE DATABASE db_calidad;
```

### Configurar el proyecto

Abrir `rest-service/src/main/resources/application.properties` y comprobar:

```properties
spring.application.name=rest-service

spring.datasource.url=jdbc:mysql://localhost:3306/db_calidad?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true

server.port=8080
```

### Instalar dependencias

Dentro de `PSyC-SS-05/rest-service` ejecutar:

```
mvn clean install
```

La primera ejecución puede tardar varios minutos.

---

## Ejecutar la aplicación

```
mvn spring-boot:run
```

Abrir en el navegador: http://localhost:8080

---

## Lanzamiento con Docker

Alternativa al método manual: lanza toda la aplicación con un único comando, sin necesidad de instalar Java, Maven, MySQL ni Node.js.

Requisito: tener instalado **Docker Desktop** → https://www.docker.com/products/docker-desktop

Comprobar instalación:

```
docker -v
docker compose version
```

### Iniciar los contenedores

Si solo necesitas arrancar los contenedores sin reconstruirlos:

```
docker-compose up
```

Si has modificado el Dockerfile, dependencias o configuraciones de entorno:

```
docker-compose up --build
```

La aplicación estará disponible en: http://localhost:8082

### Detener los contenedores

```
docker-compose down
```

Documentación adicional disponible en `docker_essentials.md`.

---

## Registro e inicio de sesión

Registrar un usuario desde la interfaz y luego iniciar sesión.

> **Importante:** el login utiliza el campo `username`, no el email.

| Campo    | Ejemplo |
|----------|---------|
| Username | maria   |
| Password | 1234    |

Para verificar usuarios en MySQL Workbench:

```sql
USE db_calidad;
SELECT * FROM usuario;
```

---

## Tests

El proyecto incluye tests unitarios, de integración, de aceptación y de rendimiento.

Entrar en el directorio `PSyC-SS-05/rest-service` y ejecutar según el tipo:

```
# Unitarios e integración
mvn test

# Aceptación (Playwright)
mvn test -P playwright

# Rendimiento
mvn test -P performance

# Build completo con todos los tests
mvn clean package
```

### Informes de cobertura JaCoCo

Después de `mvn clean package` se genera el informe en:

```
rest-service/target/site/jacoco/index.html
```

Abrir ese archivo en el navegador para ver cobertura de clases, métodos cubiertos y porcentaje de tests.

---

## Integración continua (CI)

El proyecto utiliza workflows automáticos definidos en `.github/workflows/` que incluyen compilación automática, ejecución de tests y generación de informes.

También incluye configuración Jenkins mediante `Jenkinsfile` para builds automáticos e integración continua.

---

## Problemas comunes

**Error: puerto ocupado** (`Port 8080 was already in use`)

```
netstat -ano | findstr :8080
taskkill /PID NUMERO /F
```

**Error: `node` command not found**

Node.js no está correctamente instalado. Reinstalarlo marcando **Add to PATH** y reiniciar Windows.

**Error: `No plugin found for prefix 'vaadin'`**

```
mvn clean install
```

**Error: `no POM in this directory`**

```
cd rest-service
```

---

## Estructura del proyecto

```
rest-service/
│
├── src/
│   ├── main/
│   └── test/
│
├── target/
├── pom.xml
└── application.properties
```

---

## Tecnologías utilizadas

Java 21 · Spring Boot · Vaadin · Maven · MySQL · JUnit · JaCoCo · Jenkins · GitHub Actions · Playwright

---

## Comandos de referencia rápida

| Acción                  | Comando                     |
|-------------------------|-----------------------------|
| Compilar                | `mvn clean install`         |
| Ejecutar aplicación     | `mvn spring-boot:run`       |
| Ejecutar tests          | `mvn test`                  |
| Generar paquete         | `mvn clean package`         |
| Arrancar con Docker     | `docker-compose up`         |
| Reconstruir con Docker  | `docker-compose up --build` |

Aplicación disponible en: http://localhost:8080

---

## Estado esperado

La aplicación debe permitir iniciar sesión, gestionar tareas, visualizar el calendario, gestionar categorías, almacenar información en MySQL, ejecutar tests correctamente y generar informes de calidad.

## Documentación del Proyecto

La documentación técnica detallada, incluyendo la arquitectura del sistema, la guía de inicio rápido y la referencia de la API REST, está disponible en el siguiente enlace:

[**Portal de Documentación (Sphinx)**](https://psyc25-26.github.io/PSyC-SS-05/sphinx/)

---

## Documentación automática con Doxygen

El proyecto incluye documentación automática generada mediante **Doxygen** integrado con **Maven**. La documentación se genera automáticamente utilizando comentarios en formato Doxygen añadidos al código fuente del proyecto.

### Herramientas utilizadas

- Doxygen
- Graphviz
- Maven
- GitHub Actions
- GitHub Pages
- LaTeX / MiKTeX (generación PDF)

---

### Instalación de dependencias para documentación

#### Doxygen

Descargar: https://www.doxygen.nl/download.html

Comprobar instalación:

```bash
doxygen --version
```

#### Graphviz

Descargar: https://graphviz.org/download/

> **Importante:** marcar la opción **Add Graphviz to PATH** durante la instalación.

Comprobar instalación:

```bash
dot -V
```

#### MiKTeX 

Descargar: https://miktex.org/download

---

### Generar documentación HTML

Desde `PSyC-SS-05/rest-service` ejecutar:

```bash
mvn doxygen:report
```

La documentación HTML se genera en:

```txt
target/doxygen/html
```

Abrir:

```txt
target/doxygen/html/index.html
```

---

### Generar documentación PDF

Tras ejecutar:

```bash
mvn doxygen:report
```

acceder a:

```txt
target/doxygen/latex
```

y ejecutar:

```bash
make.bat
```

El PDF generado será:

```txt
refman.pdf
```

---

### GitHub Actions y GitHub Pages

El proyecto incluye automatización completa de la documentación mediante GitHub Actions.

Workflow utilizado:

```txt
.github/workflows/doxygen.yml
```

Cada push realizado sobre la rama principal genera automáticamente:

- documentación Doxygen
- integración Maven
- despliegue automático en GitHub Pages

Documentación publicada en:

https://psyc25-26.github.io/PSyC-SS-05/

---