# Guía de Inicio (Getting Started)

Esta sección detalla los pasos necesarios para preparar el entorno de desarrollo y ejecutar **PSyC Quality Manager** en una máquina local.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalados los siguientes componentes en tu sistema:
* **Java Development Kit (JDK):** Versión compatible con el proyecto (típicamente Java 17 o superior).
* **Maven:** Para la gestión de dependencias (se puede utilizar el *wrapper* `mvnw` que ya viene incluido en la raíz).
* **Base de Datos:** Un gestor de bases de datos relacional (como MySQL o MariaDB) en ejecución.

## Instalación y Ejecución

1. **Clonar el repositorio:**
   Descarga el código fuente del proyecto en tu máquina local.

2. **Configurar la Base de Datos:**
   Abre tu gestor de base de datos preferido y ejecuta el script `db_calidad.sql` ubicado en la carpeta `src/main/resources/`. Esto creará el esquema y las tablas necesarias para el correcto funcionamiento.

3. **Revisar Propiedades:**
   Verifica el archivo `src/main/resources/application.properties`. Asegúrate de que la URL de conexión, el usuario y la contraseña de la base de datos coincidan con la configuración de tu máquina local.

4. **Compilar y Ejecutar:**
   Abre una terminal en la carpeta raíz del proyecto y arranca el servidor embebido ejecutando el siguiente comando con Maven Wrapper:
   
   *En Windows (PowerShell/CMD):*
   **`bat`**
   .\mvnw.cmd spring-boot:run