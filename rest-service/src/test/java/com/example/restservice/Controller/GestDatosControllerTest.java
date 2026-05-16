package com.example.restservice.Controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.example.restservice.Dto.CalendarioDTO;
import com.example.restservice.Dto.CategoriaDTO;
import com.example.restservice.Dto.TareaDTO;
import com.example.restservice.Dto.UsuarioDTO;
import com.example.restservice.Entity.Calendario;
import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Usuario;
import com.example.restservice.Service.GestDatosService;

/**
 * @class GestDatosControllerTest
 * @brief Pruebas unitarias de la clase GestDatosController.
 * * Comprueba el funcionamiento de los endpoints del controlador
 * mediante el uso de servicios simulados con Mockito.
 */
class GestDatosControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(GestDatosControllerTest.class);

    /**
     * @brief Comprueba la obtención correcta de usuarios.
     */
    @Test
    void obtenerUsuarios_deberiaResponderOk() {
        logger.info("Test obtenerUsuarios_deberiaResponderOk START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Mockito.when(service.cargarUsuarios()).thenReturn(List.of(usuario));

        ResponseEntity<List<Usuario>> response = controller.obtenerUsuarios();

        logger.info("Response status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    /**
     * @brief Comprueba la eliminación correcta de usuarios.
     */
    @Test
    void eliminarUsuario_deberiaResponderNoContent() {
        logger.info("Test eliminarUsuario_deberiaResponderNoContent START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        ResponseEntity<Void> response = controller.eliminarUsuario(1L);

        logger.info("Response status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    /**
     * @brief Comprueba el manejo de errores al guardar usuarios.
     */
    @Test
    void guardarUsuario_conError_deberiaBadRequest() {
        logger.warn("Test guardarUsuario_conError_deberiaBadRequest START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.guardarUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException());

        UsuarioDTO dto = new UsuarioDTO(); 
        ResponseEntity<Long> response = controller.guardarUsuario(dto);

        logger.warn("Expected error, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    /**
     * @brief Comprueba el guardado correcto de usuarios.
     */
    @Test
    void guardarUsuario_ok() {
        logger.info("Test guardarUsuario_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        
        UsuarioDTO dto = new UsuarioDTO(); 
        dto.setUsername("testuser");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        
        Mockito.when(service.guardarUsuario(any(Usuario.class))).thenReturn(usuarioGuardado);
        
        ResponseEntity<Long> response = controller.guardarUsuario(dto);
        
        logger.info("Usuario guardado con status: {}", response.getStatusCodeValue()); 
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    /**
     * @brief Comprueba el guardado correcto de tareas.
     */
    @Test
    void guardarTarea_ok() {
        logger.info("Test guardarTarea_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        TareaDTO dto = new TareaDTO(); 

        Mockito.when(service.guardarTarea(any(Tarea.class), eq(List.of(1L)))).thenReturn(1L);

        ResponseEntity<Long> response = controller.guardarTarea(List.of(1L), dto);

        logger.info("Tarea guardada con status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    /**
     * @brief Comprueba el manejo de errores al eliminar usuarios.
     */
    @Test
    void eliminarUsuario_error() {
        logger.warn("Test eliminarUsuario_error START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.doThrow(new IllegalArgumentException())
                .when(service).eliminarUsuario(1L);

        ResponseEntity<Void> response = controller.eliminarUsuario(1L);

        logger.warn("Error esperado, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    /**
     * @brief Comprueba el manejo de errores al obtener usuarios.
     */
    @Test
    void obtenerUsuarios_error() {
        logger.warn("Test obtenerUsuarios_error START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.cargarUsuarios())
                .thenThrow(new RuntimeException());

        ResponseEntity<List<Usuario>> response = controller.obtenerUsuarios();

        logger.warn("Error esperado, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    /**
     * @brief Comprueba la obtención de tareas por usuario.
     */
    @Test
    void obtenerTareasPorUsuario_ok() {
        logger.info("Test obtenerTareasPorUsuario_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.cargarTareasPorUsuario(1L))
                .thenReturn(List.of(new Tarea()));

        ResponseEntity<List<Tarea>> response = controller.obtenerTareasPorUsuario(1L);

        logger.info("Response status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    /**
     * @brief Comprueba la modificación de tareas.
     */
    @Test
    void modificarTarea_ok() {
        logger.info("Test modificarTarea_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        TareaDTO dto = new TareaDTO(); 
        Tarea tareaModificada = new Tarea(); 

        Mockito.when(service.modificarTarea(eq(1L), any(Tarea.class)))
                .thenReturn(tareaModificada);

        ResponseEntity<Tarea> response = controller.modificarTarea(1L, dto);

        logger.info("Tarea modificada, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    /**
     * @brief Comprueba el guardado correcto de categorías.
     */
    @Test
    void guardarCategoria_ok() {
        logger.info("Test guardarCategoria_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        
        CategoriaDTO dto = new CategoriaDTO(); 
        
        Mockito.when(service.guardarCategoria(any(Categoria.class), eq(1L))).thenReturn(1L);
        
        ResponseEntity<Long> response = controller.guardarCategoria(1L, dto);
        
        logger.info("Categoria guardada, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    /**
     * @brief Comprueba el manejo de errores al guardar categorías.
     */
    @Test
    void guardarCategoria_error() {
        logger.warn("Test guardarCategoria_error START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        
        Mockito.when(service.guardarCategoria(any(Categoria.class), eq(1L)))
                .thenThrow(new IllegalArgumentException());
                
        CategoriaDTO dto = new CategoriaDTO(); 
        ResponseEntity<Long> response = controller.guardarCategoria(1L, dto);
        
        logger.warn("Error esperado, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    /**
     * @brief Comprueba la eliminación de tareas.
     */
    @Test
    void eliminarTarea_ok() {
        logger.info("Test eliminarTarea_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        ResponseEntity<Void> response = controller.eliminarTarea(1L);

        logger.info("Tarea eliminada, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    /**
     * @brief Comprueba el manejo de errores al eliminar tareas.
     */
    @Test
    void eliminarTarea_error() {
        logger.warn("Test eliminarTarea_error START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.doThrow(new IllegalArgumentException())
                .when(service).eliminarTarea(1L);

        ResponseEntity<Void> response = controller.eliminarTarea(1L);

        logger.warn("Error esperado, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    /**
     * @brief Comprueba la obtención del calendario de un usuario.
     */
    @Test
    void obtenerCalendario_ok() {
        logger.info("Test obtenerCalendario_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Calendario cal = new Calendario();

        Mockito.when(service.cargarCalendarioPorUsuario(1L))
                .thenReturn(cal);

        ResponseEntity<Calendario> response = controller.obtenerCalendarioPorUsuario(1L);

        logger.info("Calendario obtenido, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    /**
     * @brief Comprueba la obtención de categorías por tarea.
     */
    @Test
    void obtenerCategoriaPorTarea_ok() {
        logger.info("Test obtenerCategoriaPorTarea_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Categoria cat = new Categoria();

        Mockito.when(service.obtenerCategoriaPorTarea(1L))
                .thenReturn(cat);

        ResponseEntity<Categoria> response = controller.obtenerCategoriaPorTarea(1L);

        logger.info("Categoria obtenida, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    /**
     * @brief Comprueba la modificación de calendarios.
     */
    @Test
    void modificarCalendario_ok() {
        logger.info("Test modificarCalendario_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        CalendarioDTO dto = new CalendarioDTO(); 
        Calendario calModificado = new Calendario();

        Mockito.when(service.modificarCalendario(eq(1L), any(Calendario.class)))
                .thenReturn(calModificado);

        ResponseEntity<Calendario> response = controller.modificarCalendario(1L, dto);

        logger.info("Calendario modificado, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    /**
     * @brief Comprueba el manejo de errores al modificar calendarios.
     */
    @Test
    void modificarCalendario_error() {
        logger.warn("Test modificarCalendario_error START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.modificarCalendario(eq(1L), any(Calendario.class)))
                .thenThrow(new IllegalArgumentException());

        CalendarioDTO dto = new CalendarioDTO(); 
        ResponseEntity<Calendario> response = controller.modificarCalendario(1L, dto);

        logger.warn("Error esperado, status: {}", response.getStatusCodeValue());
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}