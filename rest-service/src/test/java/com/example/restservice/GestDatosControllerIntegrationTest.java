package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import com.example.restservice.Entity.Usuario;
import com.example.restservice.Cliente.GestDatosCliente;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GestDatosControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    void clienteDeberiaLlamarAlServidor() {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);
        List<Usuario> usuarios = client.obtenerUsuarios();

        assertThat(usuarios).isNotNull();
    }

    @Test
    void clienteFallaConServidorNoDisponible() {
        GestDatosCliente client = new GestDatosCliente("http://localhost:9999");

        assertThatThrownBy(client::obtenerUsuarios)
                .isInstanceOf(Exception.class);
    }
}