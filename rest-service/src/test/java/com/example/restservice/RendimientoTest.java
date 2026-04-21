package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.restservice.Entity.Usuario;
import com.example.restservice.Cliente.GestDatosCliente;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RendimientoTest {

    @LocalServerPort
    int port;

    private static final Logger logger = LoggerFactory.getLogger(RendimientoTest.class);

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    private GestDatosCliente getClient() {
        return new GestDatosCliente("http://localhost:" + port);
    }

    // CASO EXITOSO
    @Test
    @PerfTest(invocations = 100, threads = 5)
    public void rendimiento_casoExitoso() {
        GestDatosCliente client = getClient();

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
        }
        long duracion = System.currentTimeMillis() - inicio;

        assertThat(duracion).isLessThan(5000);
    }

    // CASO FALLIDO
    @Test
    @PerfTest(invocations = 100, threads = 5)
    public void rendimiento_casoFallido() throws InterruptedException {
        GestDatosCliente client = getClient();

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
            Thread.sleep(5);
        }
        long duracion = System.currentTimeMillis() - inicio;

        assertThat(duracion).isGreaterThan(200);
    }

    // INVOCACIONES
    @Test
    @PerfTest(invocations = 200, threads = 1)
    public void rendimiento_invocaciones() {
        GestDatosCliente client = getClient();

        for (int i = 0; i < 200; i++) {
            List<Usuario> usuarios = client.obtenerUsuarios();
            assertThat(usuarios).isNotNull();
        }
    }

    // HILOS
    @Test
    @PerfTest(invocations = 100, threads = 5)
    public void rendimiento_threads() {
        List<Usuario> usuarios = getClient().obtenerUsuarios();
        assertThat(usuarios).isNotNull();
    }

    // PROMEDIO Y MAX
    @Test
    @PerfTest(invocations = 50, threads = 2)
    public void rendimiento_average_max() {
        GestDatosCliente client = getClient();

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

    // THROUGHPUT
    @Test
    @PerfTest(invocations = 200, threads = 10)
    public void rendimiento_throughput() {
        int operaciones = 200;

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < operaciones; i++) {
            getClient().obtenerUsuarios();
        }

        long duracion = System.currentTimeMillis() - inicio;

        double throughput = (operaciones * 1000.0) / duracion;

        logger.info("Throughput: {} ops/sec", throughput);

        assertThat(throughput).isGreaterThan(0);
    }

    // DURACIÓN
    @Test
    @PerfTest(invocations = 100, threads = 5)
    public void rendimiento_duracion() {
        long inicio = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            getClient().obtenerUsuarios();
        }

        long duracion = System.currentTimeMillis() - inicio;

        logger.info("Duración total: {} ms", duracion);

        assertThat(duracion).isLessThan(10000);
    }
}