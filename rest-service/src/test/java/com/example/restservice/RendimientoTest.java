package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.restservice.Entity.Usuario;
import com.example.restservice.Cliente.GestDatosCliente;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RendimientoTest {

    @LocalServerPort
    int port;

    private static final Logger logger = LoggerFactory.getLogger(RendimientoTest.class);

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



    @Test
    void rendimiento_threads() throws InterruptedException {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);

        int numThreads = 5;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                List<Usuario> usuarios = client.obtenerUsuarios();
                assertThat(usuarios).isNotNull();
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }


    @Test
    void rendimiento_average_max() {
        String baseUrl = "http://localhost:" + port;
        GestDatosCliente client = new GestDatosCliente(baseUrl);

        List<Long> tiempos = new java.util.ArrayList<>();

        for (int i = 0; i < 50; i++) {
            long inicio = System.currentTimeMillis();

            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();

            long fin = System.currentTimeMillis();
            tiempos.add(fin - inicio);
        }

        double avg = tiempos.stream().mapToLong(Long::longValue).average().orElse(0);
        long max = tiempos.stream().mapToLong(Long::longValue).max().orElse(0);

        logger.info("AVG: {}", avg);
        logger.info("MAX: {}", max);

        assertThat(avg).isGreaterThan(0);
        assertThat(max).isGreaterThan(0);
    }


}