# PSyC-SS-05

Para ejecutar el programa:
Paso 1: Configurar la Base de Datos. Abre MySQL, crea la base de datos ejecutando CREATE DATABASE db_calidad; y asegúrate de que tus credenciales (usuario y contraseña) coinciden con las del archivo application.properties.

Paso 2: Abre tu terminal y muévete al directorio del backend ejecutando el comando: cd PSyC-SS-05/rest-service.

Paso 3: Ejecutar la aplicación. Inicia el servidor usando el Wrapper de Maven. Ejecuta mvnw.cmd spring-boot:run si estás en Windows, o ./mvnw spring-boot:run si usas Linux/macOS.

Paso 4: Verificar. Espera a ver el logo de Spring Boot en la consola indicando que ha arrancado sin errores. La API ya estará funcionando y lista para recibir peticiones en http://localhost:8080/gestDatos.

Ejecución de pruebas (TEST):
Paso 1: Abre tu terminal y muévete al directorio del backend ejecutando el comando: cd PSyC-SS-05/rest-service.
        Depende del test que quieras probar, ejecutar el siguiente comando:

    TEST UNITARIOS y de INTEGARCIÓN: mvn test

    TEST DE ACEPTACIÓN: mvn test -P playwright

    TEST DE RENDIMIENTO: mvn test -P performance

## Product Backlog

| Sprint sugerido | Issue / Historia de Usuario | Criterios de Aceptación | Prioridad | Estimación (Puntos) |
|---|---|---|---|---:|
| Sprint 1 (Completado) | Entorno y Repositorio | Uso de Maven, repositorio en GitHub y el proyecto configurado en GitHub Projects. | Alta | 3 |
| Sprint 1 (Completado) | Arquitectura y BD | Los modelos (Tarea, Usuario, Categoría, Calendario), controladores y la base de datos están creados y conectados correctamente. | Alta | 8 |
| Sprint 1 (Completado) | CRUD de Tareas (Backend) | Los controladores permiten crear, leer, actualizar y borrar una Tarea en la base de datos mediante código. | Alta | 5 |
| Sprint 2 | Interfaz Visual Básica | Existe una vista inicial (GUI) navegable para comprobar que el sistema responde y se muestra por pantalla. | Alta | 5 |
| Sprint 2 | Integración CRUD en UI | El usuario puede ejecutar el CRUD de Tareas interactuando directamente con los botones y formularios de la interfaz visual. | Alta | 8 |
| Sprint 2 | Visualización del Calendario | El Calendario muestra correctamente las vistas mensual y semanal con las tareas posicionadas en sus respectivos días/horas. | Alta | 13 |
| Sprint 3 | Asignación y Filtro de Categorías | El usuario puede crear una Categoría, asignarla a una tarea y filtrar su vista de calendario para ver solo ciertas categorías. | Media | 5 |
| Sprint 3 | Explicación de errores y warnings de usuario | El sistema impide guardar tareas incongruentes y muestra mensajes de error por pantalla. | Media | 4 |
| Sprint 3 | Gestión de Usuarios (Auth) | El sistema permite registrar un Usuario nuevo e iniciar sesión verificando sus credenciales en la base de datos. | Media | 8 |
