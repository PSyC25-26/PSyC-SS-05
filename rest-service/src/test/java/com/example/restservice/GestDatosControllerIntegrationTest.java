package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.restservice.Entity.Calendario;
import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Usuario;

/**
 * @class GestDatosControllerIntegrationTest
 * @brief Pruebas de integración de los endpoints del controlador.
 * 
 * Comprueba el funcionamiento completo de la aplicación
 * realizando peticiones HTTP reales sobre la API REST.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GestDatosControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    /**
     * @brief Inicializa la URL base antes de cada prueba.
     */
    @BeforeEach
    void setUp() {

        baseUrl = "http://localhost:" + port + "/gestDatos";
    }

    /**
     * @brief Comprueba el flujo completo de usuario y calendario.
     */
    @Test
    void testFlujoCompletoUsuarioYCalendario() {

        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setUsername("usuarioIntegracion");
        nuevoUsuario.setPassword("1234"); 
        nuevoUsuario.setEmail("integracion@test.com");
        nuevoUsuario.setTipoUsuario(Usuario.TipoUsuario.PARTICULAR);
        
        ResponseEntity<Long> responseUsuario = restTemplate.postForEntity(baseUrl + "/guardarUsuario", nuevoUsuario, Long.class);

        assertThat(responseUsuario.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Long idUsuario = responseUsuario.getBody();

        assertThat(idUsuario).isNotNull();

        ResponseEntity<Usuario[]> responseGetUsuarios = restTemplate.getForEntity(baseUrl + "/obtenerUsuarios", Usuario[].class);

        assertThat(responseGetUsuarios.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseGetUsuarios.getBody()).isNotEmpty();

        Calendario calendarioInicial = new Calendario();

        calendarioInicial.setNombre("Calendario Original");

        restTemplate.postForEntity(baseUrl + "/guardarCalendario/" + idUsuario, calendarioInicial, Long.class);

        Calendario calendarioModificado = new Calendario();

        calendarioModificado.setNombre("Calendario Modificado Integracion");
        
        ResponseEntity<Calendario> responseGetCalendario = restTemplate.getForEntity(baseUrl + "/obtenerCalendario/" + idUsuario, Calendario.class);

        assertThat(responseGetCalendario.getStatusCode()).isEqualTo(HttpStatus.OK);

        Long idCalendario = responseGetCalendario.getBody().getId();

        HttpEntity<Calendario> requestUpdateCalendario = new HttpEntity<>(calendarioModificado);

        ResponseEntity<Calendario> responsePutCalendario = restTemplate.exchange(
                baseUrl + "/modificarCalendario/" + idCalendario,
                HttpMethod.PUT,
                requestUpdateCalendario,
                Calendario.class);

        assertThat(responsePutCalendario.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responsePutCalendario.getBody().getNombre()).isEqualTo("Calendario Modificado Integracion");

        ResponseEntity<Void> responseDelete = restTemplate.exchange(
                baseUrl + "/eliminarUsuario/" + idUsuario,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /**
     * @brief Comprueba el flujo completo de tareas y categorías.
     */
    @Test
    void testFlujoCompletoTareaYCategoria() {

        Usuario usuario = new Usuario();

        usuario.setUsername("usuarioParaTarea");
        usuario.setPassword("1234");
        usuario.setEmail("tarea_integracion@test.com");
        usuario.setTipoUsuario(Usuario.TipoUsuario.PARTICULAR);

        Long idUsuario = restTemplate.postForEntity(baseUrl + "/guardarUsuario", usuario, Long.class).getBody();

        Categoria categoria = new Categoria();

        categoria.setNombre("Categoria de Integracion");
        
        ResponseEntity<Long> responseCategoria = restTemplate.postForEntity(
                baseUrl + "/guardarCategoria/" + idUsuario,
                categoria,
                Long.class);

        assertThat(responseCategoria.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Long idCategoria = responseCategoria.getBody();

        categoria.setId(idCategoria);

        Tarea tarea = new Tarea();

        tarea.setTitulo("Tarea de Integracion");
        tarea.setDescripcion("Descripcion de prueba");
        tarea.setFechaInicio(LocalDateTime.now());
        tarea.setFechaFin(LocalDateTime.now().plusDays(1));
        tarea.setCategoria(categoria);
        
        String urlGuardarTarea = baseUrl + "/guardarTarea?idUsuarios=" + idUsuario;

        ResponseEntity<Long> responseTarea = restTemplate.postForEntity(urlGuardarTarea, tarea, Long.class);

        assertThat(responseTarea.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Long idTarea = responseTarea.getBody();

        assertThat(idTarea).isNotNull();

        ResponseEntity<Tarea[]> responseTareasUsuario = restTemplate.getForEntity(
                baseUrl + "/obtenerTareasPorUsuario/" + idUsuario,
                Tarea[].class);

        assertThat(responseTareasUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseTareasUsuario.getBody()).hasSizeGreaterThan(0);

        Tarea tareaModificada = new Tarea();

        tareaModificada.setTitulo("Tarea Modificada");
        tareaModificada.setDescripcion("Descripcion de prueba");
        tareaModificada.setFechaInicio(LocalDateTime.now());
        tareaModificada.setFechaFin(LocalDateTime.now().plusDays(1));
        tareaModificada.setCategoria(categoria);

        HttpEntity<Tarea> requestUpdateTarea = new HttpEntity<>(tareaModificada);
        
        ResponseEntity<Tarea> responsePutTarea = restTemplate.exchange(
                baseUrl + "/modificarTarea/" + idTarea,
                HttpMethod.PUT,
                requestUpdateTarea,
                Tarea.class);

        assertThat(responsePutTarea.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responsePutTarea.getBody().getTitulo()).isEqualTo("Tarea Modificada");

        ResponseEntity<Void> responseDeleteTarea = restTemplate.exchange(
                baseUrl + "/eliminarTarea/" + idTarea,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertThat(responseDeleteTarea.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        restTemplate.exchange(
                baseUrl + "/eliminarUsuario/" + idUsuario,
                HttpMethod.DELETE,
                null,
                Void.class);
    }

    /**
     * @brief Comprueba el error al crear calendarios duplicados.
     */
    @Test
    void testGuardarCalendarioConFalloPorDuplicado() {

        Usuario usuario = new Usuario();

        usuario.setUsername("userCalendario");
        usuario.setPassword("1234"); 
        usuario.setEmail("calendario_error@test.com");
        usuario.setTipoUsuario(Usuario.TipoUsuario.PARTICULAR);

        Long idUsuario = restTemplate.postForEntity(baseUrl + "/guardarUsuario", usuario, Long.class).getBody();

        Calendario nuevoCalendario = new Calendario();

        nuevoCalendario.setNombre("Calendario Extra");

        restTemplate.postForEntity(
                baseUrl + "/guardarCalendario/" + idUsuario,
                nuevoCalendario,
                Long.class);
        
        ResponseEntity<String> responseError = restTemplate.postForEntity(
                baseUrl + "/guardarCalendario/" + idUsuario,
                nuevoCalendario,
                String.class);
        
        assertThat(responseError.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        
        restTemplate.exchange(
                baseUrl + "/eliminarUsuario/" + idUsuario,
                HttpMethod.DELETE,
                null,
                Void.class);
    }
}