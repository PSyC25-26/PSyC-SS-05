# Arquitectura del Sistema

La aplicación está construida sobre **Java y el framework Spring Boot**, estructurada mediante una arquitectura multicapa (N-Tier) para separar claramente la interfaz de usuario, la lógica de negocio y el acceso a los datos. Para la interfaz gráfica (Frontend), el proyecto integra **Vaadin**, lo que permite construir aplicaciones web dinámicas directamente en Java.

## Capas de la Aplicación

1. **Capa de Presentación (UI):** Ubicada en el paquete `com.example.restservice.UI`. Contiene los layouts y vistas generadas con Vaadin (`MainLayout`, `HomeView`, `TareaView`, `CategoriaView`, `LoginView`, `RegistroView`). Esta capa gestiona la interacción directa del usuario y los eventos de los botones y formularios.

2. **Capa de Controladores / Clientes:**
   Los paquetes `Controller` (`GestDatosController`) y `Cliente` (`GestDatosCliente`) se encargan de manejar las peticiones, coordinando la información que entra por las vistas hacia las capas inferiores.

3. **Capa de Servicios (Lógica de Negocio):**
   El paquete `Service` (`GestDatosService`) es el núcleo lógico de la aplicación. Aquí se procesan las reglas de negocio antes de enviar o solicitar información a la base de datos.

4. **Capa de Acceso a Datos (DAO):**
   Ubicada en el paquete `Dao`, aplica el patrón Data Access Object. Clases como `UsuarioDAO`, `TareaDAO`, `CalendarioDAO` y `CategoriaDAO` encapsulan y gestionan las consultas CRUD (Create, Read, Update, Delete).

5. **Capa de Entidades (Modelo):**
   El paquete `Entity` define los objetos de dominio exactos (`Usuario`, `Tarea`, `Calendario`, `Categoria`). Estos objetos se mapean directamente con las tablas de la base de datos relacional.

## Base de Datos

El sistema almacena la persistencia en una base de datos relacional. El esquema inicial de las tablas y las relaciones puede inicializarse utilizando el script `db_calidad.sql` incluido en los recursos del proyecto.