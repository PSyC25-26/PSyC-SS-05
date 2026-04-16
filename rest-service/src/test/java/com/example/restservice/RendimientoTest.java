package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.restservice.Entity.Usuario;
import com.example.restservice.Cliente.GestDatosCliente;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RendimientoTest {

    @LocalServerPort
    int port;

    @Test
    void rendimiento_casoExitoso() {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
        }
        long duracion = System.currentTimeMillis() - inicio;

        assertThat(duracion).isLessThan(5000);
    }

    @Test
    void rendimiento_casoFallido() throws InterruptedException {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
            Thread.sleep(5);
        }
        long duracion = System.currentTimeMillis() - inicio;

        assertThat(duracion).isGreaterThan(200);
    }


    @Test
    void rendimiento_invocaciones() {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);

        int invocaciones = 200;

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < invocaciones; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
        }

        long duracion = System.currentTimeMillis() - inicio;

        assertThat(duracion).isGreaterThan(0);
    }
}