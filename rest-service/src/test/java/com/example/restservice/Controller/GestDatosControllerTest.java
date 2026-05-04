package com.example.restservice.Controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.example.restservice.Entity.Calendario;
import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Usuario;
import com.example.restservice.Service.GestDatosService;

class GestDatosControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(GestDatosController.class);

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

    @Test
    void eliminarUsuario_deberiaResponderNoContent() {
        logger.info("Test eliminarUsuario_deberiaResponderNoContent START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        ResponseEntity<Void> response = controller.eliminarUsuario(1L);

        logger.info("Response status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void guardarUsuario_conError_deberiaBadRequest() {
        logger.warn("Test guardarUsuario_conError_deberiaBadRequest START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.guardarUsuario(null))
                .thenThrow(new IllegalArgumentException());

        ResponseEntity<Long> response = controller.guardarUsuario(null);

        logger.warn("Expected error, status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void guardarUsuario_ok() {
        logger.info("Test guardarUsuario_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        
        Usuario usuario = new Usuario();
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        
        // CORRECCIÓN: El mock debe devolver un objeto Usuario, no un 1L
        Mockito.when(service.guardarUsuario(usuario)).thenReturn(usuarioGuardado);
        
        ResponseEntity<Long> response = controller.guardarUsuario(usuario);
        
        logger.info("Usuario guardado con status: {}", response.getStatusCode().value()); 
        assertThat(response.getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void guardarTarea_ok() {
        logger.info("Test guardarTarea_ok START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Tarea tarea = new Tarea();

        Mockito.when(service.guardarTarea(tarea, List.of(1L))).thenReturn(1L);

        ResponseEntity<Long> response = controller.guardarTarea(List.of(1L), tarea);

        logger.info("Tarea guardada con status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

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

    @Test
    void modificarTarea_ok() {
        logger.info("Test modificarTarea_ok START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Tarea tarea = new Tarea();

        Mockito.when(service.modificarTarea(1L, tarea))
                .thenReturn(tarea);

        ResponseEntity<Tarea> response = controller.modificarTarea(1L, tarea);

        logger.info("Tarea modificada, status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void guardarCategoria_ok() {
        logger.info("Test guardarCategoria_ok START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        
        Categoria categoria = new Categoria();
        
        Mockito.when(service.guardarCategoria(categoria, 1L)).thenReturn(1L);
        
        ResponseEntity<Long> response = controller.guardarCategoria(1L, categoria);
        
        logger.info("Categoria guardada, status: {}", response.getStatusCode().value());
        assertThat(response.getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void guardarCategoria_error() {
        logger.warn("Test guardarCategoria_error START");
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);
        
        Mockito.when(service.guardarCategoria(null, 1L))
                .thenThrow(new IllegalArgumentException());
                
        ResponseEntity<Long> response = controller.guardarCategoria(1L, null);
        
        logger.warn("Error esperado, status: {}", response.getStatusCode().value());
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void eliminarTarea_ok() {
        logger.info("Test eliminarTarea_ok START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        ResponseEntity<Void> response = controller.eliminarTarea(1L);

        logger.info("Tarea eliminada, status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

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

    @Test
    void modificarCalendario_ok() {
        logger.info("Test modificarCalendario_ok START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Calendario cal = new Calendario();

        Mockito.when(service.modificarCalendario(1L, cal))
                .thenReturn(cal);

        ResponseEntity<Calendario> response = controller.modificarCalendario(1L, cal);

        logger.info("Calendario modificado, status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void modificarCalendario_error() {
        logger.warn("Test modificarCalendario_error START");

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.modificarCalendario(1L, null))
                .thenThrow(new IllegalArgumentException());

        ResponseEntity<Calendario> response = controller.modificarCalendario(1L, null);

        logger.warn("Error esperado, status: {}", response.getStatusCodeValue());

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}