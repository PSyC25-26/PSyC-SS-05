package com.example.restservice.Controller;

import com.example.restservice.Entity.Calendario;
import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Usuario;
import com.example.restservice.Service.GestDatosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GestDatosControllerTest {

    @Test
    void obtenerUsuarios_deberiaResponderOk() {

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Mockito.when(service.cargarUsuarios()).thenReturn(List.of(usuario));

        ResponseEntity<List<Usuario>> response = controller.obtenerUsuarios();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void eliminarUsuario_deberiaResponderNoContent() {

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        ResponseEntity<Void> response = controller.eliminarUsuario(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void guardarUsuario_conError_deberiaBadRequest() {

        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.guardarUsuario(null))
                .thenThrow(new IllegalArgumentException());

        ResponseEntity<Long> response = controller.guardarUsuario(null);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void guardarUsuario_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Usuario usuario = new Usuario();
        Mockito.when(service.guardarUsuario(usuario)).thenReturn(1L);

        ResponseEntity<Long> response = controller.guardarUsuario(usuario);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }


    @Test
    void guardarTarea_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Tarea tarea = new Tarea();

        Mockito.when(service.guardarTarea(tarea, List.of(1L))).thenReturn(1L);

        ResponseEntity<Long> response = controller.guardarTarea(List.of(1L), tarea);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }   

    @Test
    void eliminarUsuario_error() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.doThrow(new IllegalArgumentException())
                .when(service).eliminarUsuario(1L);

        ResponseEntity<Void> response = controller.eliminarUsuario(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void obtenerUsuarios_error() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.cargarUsuarios())
                .thenThrow(new RuntimeException());

        ResponseEntity<List<Usuario>> response = controller.obtenerUsuarios();

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void obtenerTareasPorUsuario_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.cargarTareasPorUsuario(1L))
                .thenReturn(List.of(new Tarea()));

        ResponseEntity<List<Tarea>> response = controller.obtenerTareasPorUsuario(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void modificarTarea_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Tarea tarea = new Tarea();

        Mockito.when(service.modificarTarea(1L, tarea))
                .thenReturn(tarea);

        ResponseEntity<Tarea> response = controller.modificarTarea(1L, tarea);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void guardarCategoria_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Categoria categoria = new Categoria();

        Mockito.when(service.guardarCategoria(categoria)).thenReturn(1L);

        ResponseEntity<Long> response = controller.guardarCategoria(categoria);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void guardarCategoria_error() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.guardarCategoria(null))
                .thenThrow(new IllegalArgumentException());

        ResponseEntity<Long> response = controller.guardarCategoria(null);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void eliminarTarea_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        ResponseEntity<Void> response = controller.eliminarTarea(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }


    @Test
    void eliminarTarea_error() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.doThrow(new IllegalArgumentException())
                .when(service).eliminarTarea(1L);

        ResponseEntity<Void> response = controller.eliminarTarea(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void obtenerCalendario_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Calendario cal = new Calendario();

        Mockito.when(service.cargarCalendarioPorUsuario(1L))
                .thenReturn(cal);

        ResponseEntity<Calendario> response = controller.obtenerCalendarioPorUsuario(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void obtenerCategoriaPorTarea_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Categoria cat = new Categoria();

        Mockito.when(service.obtenerCategoriaPorTarea(1L))
                .thenReturn(cat);

        ResponseEntity<Categoria> response = controller.obtenerCategoriaPorTarea(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void modificarCalendario_ok() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Calendario cal = new Calendario();

        Mockito.when(service.modificarCalendario(1L, cal))
                .thenReturn(cal);

        ResponseEntity<Calendario> response = controller.modificarCalendario(1L, cal);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void modificarCalendario_error() {
        GestDatosService service = Mockito.mock(GestDatosService.class);
        GestDatosController controller = new GestDatosController(service);

        Mockito.when(service.modificarCalendario(1L, null))
                .thenThrow(new IllegalArgumentException());

        ResponseEntity<Calendario> response = controller.modificarCalendario(1L, null);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    
}