package com.example.restservice.Controller;

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
}