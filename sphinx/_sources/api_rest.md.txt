# Referencia de la API REST

El controlador `GestDatosController` expone los servicios del backend para la aplicación PSyC Quality Manager. Todas las rutas parten del prefijo `/gestDatos`.

## Usuarios

* **`POST /gestDatos/guardarUsuario`**
  * **Descripción:** Guarda un nuevo usuario en el sistema.
  * **Cuerpo (Body):** Objeto JSON `Usuario`.
  * **Respuesta Exitosa:** `201 CREATED` con el `Long` del ID generado.

* **`GET /gestDatos/obtenerUsuarios`**
  * **Descripción:** Devuelve una lista con todos los usuarios registrados.
  * **Respuesta Exitosa:** `200 OK` con un array JSON de objetos `Usuario`.

* **`DELETE /gestDatos/eliminarUsuario/{idUsuario}`**
  * **Descripción:** Elimina un usuario existente del sistema.
  * **Parámetros:** `idUsuario` (Path Variable).
  * **Respuesta Exitosa:** `204 NO CONTENT`.

---

## Tareas

* **`POST /gestDatos/guardarTarea`**
  * **Descripción:** Crea una nueva tarea y la asocia a uno o varios usuarios.
  * **Parámetros:** `idUsuarios` (Query Param, Lista de IDs).
  * **Cuerpo (Body):** Objeto JSON `Tarea`.
  * **Respuesta Exitosa:** `201 CREATED` con el `Long` del ID generado.

* **`GET /gestDatos/obtenerTareasPorUsuario/{idUsuario}`**
  * **Descripción:** Recupera todas las tareas asignadas a un usuario específico.
  * **Parámetros:** `idUsuario` (Path Variable).
  * **Respuesta Exitosa:** `200 OK` con un array JSON de objetos `Tarea`.

* **`PUT /gestDatos/modificarTarea/{idTarea}`**
  * **Descripción:** Actualiza los datos de una tarea existente.
  * **Parámetros:** `idTarea` (Path Variable).
  * **Cuerpo (Body):** Objeto JSON `Tarea` modificado.
  * **Respuesta Exitosa:** `200 OK` con el objeto modificado.

* **`DELETE /gestDatos/eliminarTarea/{idTarea}`**
  * **Descripción:** Elimina una tarea del sistema.
  * **Parámetros:** `idTarea` (Path Variable).
  * **Respuesta Exitosa:** `204 NO CONTENT`.

---

## Categorías y Calendarios

* **`POST /gestDatos/guardarCategoria/{idUsuario}`**
  * **Descripción:** Crea una categoría personalizada para un usuario.
  * **Parámetros:** `idUsuario` (Path Variable).
  * **Cuerpo (Body):** Objeto JSON `Categoria`.
  * **Respuesta Exitosa:** `201 CREATED`.

* **`GET /gestDatos/obtenerCategoriaPorTarea/{idTarea}`**
  * **Descripción:** Obtiene la categoría asignada a una tarea en concreto.
  * **Parámetros:** `idTarea` (Path Variable).
  * **Respuesta Exitosa:** `200 OK` con el objeto `Categoria`.

* **`POST /gestDatos/guardarCalendario/{idUsuario}`**
  * **Descripción:** Asocia y guarda un calendario para un usuario.
  * **Parámetros:** `idUsuario` (Path Variable).
  * **Cuerpo (Body):** Objeto JSON `Calendario`.
  * **Respuesta Exitosa:** `201 CREATED`.

* **`GET /gestDatos/obtenerCalendario/{idUsuario}`**
  * **Descripción:** Recupera el calendario de un usuario.
  * **Parámetros:** `idUsuario` (Path Variable).
  * **Respuesta Exitosa:** `200 OK` con el objeto `Calendario`.

* **`PUT /gestDatos/modificarCalendario/{idCalendario}`**
  * **Descripción:** Actualiza la configuración de un calendario.
  * **Parámetros:** `idCalendario` (Path Variable).
  * **Respuesta Exitosa:** `200 OK`.