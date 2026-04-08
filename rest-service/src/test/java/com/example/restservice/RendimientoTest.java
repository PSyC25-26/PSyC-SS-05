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

    //Caso exitoso: 100 llamadas deben tardar menos de 5 segundos
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

    //Caso fallido: el umbral es imposible (0 ms) y el test debe fallar
    @Test
    void rendimiento_casoFallido() {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
        }
        long duracion = System.currentTimeMillis() - inicio;

        assertThat(duracion).isLessThan(0);
    }
}