package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.restservice.Entity.Calendario;
import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Usuario;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GestDatosControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/gestDatos";
    }

    @Test
    void testFlujoCompletoUsuarioYCalendario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername("usuarioIntegracion");
        
        ResponseEntity<Long> responseUsuario = restTemplate.postForEntity(baseUrl + "/guardarUsuario", nuevoUsuario, Long.class);
        assertThat(responseUsuario.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long idUsuario = responseUsuario.getBody();
        assertThat(idUsuario).isNotNull();

        ResponseEntity<Usuario[]> responseGetUsuarios = restTemplate.getForEntity(baseUrl + "/obtenerUsuarios", Usuario[].class);
        assertThat(responseGetUsuarios.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseGetUsuarios.getBody()).isNotEmpty();

        Calendario calendarioModificado = new Calendario();
        calendarioModificado.setNombre("Calendario Modificado Integracion");
        
        ResponseEntity<Calendario> responseGetCalendario = restTemplate.getForEntity(baseUrl + "/obtenerCalendario/" + idUsuario, Calendario.class);
        assertThat(responseGetCalendario.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long idCalendario = responseGetCalendario.getBody().getId();

        HttpEntity<Calendario> requestUpdateCalendario = new HttpEntity<>(calendarioModificado);
        ResponseEntity<Calendario> responsePutCalendario = restTemplate.exchange(
                baseUrl + "/modificarCalendario/" + idCalendario, HttpMethod.PUT, requestUpdateCalendario, Calendario.class);
        assertThat(responsePutCalendario.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responsePutCalendario.getBody().getNombre()).isEqualTo("Calendario Modificado Integracion");

        ResponseEntity<Void> responseDelete = restTemplate.exchange(
                baseUrl + "/eliminarUsuario/" + idUsuario, HttpMethod.DELETE, null, Void.class);
        assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testFlujoCompletoTareaYCategoria() {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuarioParaTarea");
        Long idUsuario = restTemplate.postForEntity(baseUrl + "/guardarUsuario", usuario, Long.class).getBody();

        Categoria categoria = new Categoria();
        ResponseEntity<Long> responseCategoria = restTemplate.postForEntity(baseUrl + "/guardarCategoria", categoria, Long.class);
        assertThat(responseCategoria.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long idCategoria = responseCategoria.getBody();

        Tarea tarea = new Tarea();
        tarea.setTitulo("Tarea de Integracion");
        tarea.setDescripcion("Descripcion de prueba");
        
        String urlGuardarTarea = baseUrl + "/guardarTarea?idUsuarios=" + idUsuario;
        ResponseEntity<Long> responseTarea = restTemplate.postForEntity(urlGuardarTarea, tarea, Long.class);
        assertThat(responseTarea.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long idTarea = responseTarea.getBody();
        assertThat(idTarea).isNotNull();

        ResponseEntity<Tarea[]> responseTareasUsuario = restTemplate.getForEntity(baseUrl + "/obtenerTareasPorUsuario/" + idUsuario, Tarea[].class);
        assertThat(responseTareasUsuario.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseTareasUsuario.getBody()).hasSizeGreaterThan(0);

        Tarea tareaModificada = new Tarea();
        tareaModificada.setTitulo("Tarea Modificada");
        HttpEntity<Tarea> requestUpdateTarea = new HttpEntity<>(tareaModificada);
        
        ResponseEntity<Tarea> responsePutTarea = restTemplate.exchange(
                baseUrl + "/modificarTarea/" + idTarea, HttpMethod.PUT, requestUpdateTarea, Tarea.class);
        assertThat(responsePutTarea.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responsePutTarea.getBody().getTitulo()).isEqualTo("Tarea Modificada");

        // 6. Eliminar Tarea
        ResponseEntity<Void> responseDeleteTarea = restTemplate.exchange(
                baseUrl + "/eliminarTarea/" + idTarea, HttpMethod.DELETE, null, Void.class);
        assertThat(responseDeleteTarea.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        restTemplate.exchange(baseUrl + "/eliminarUsuario/" + idUsuario, HttpMethod.DELETE, null, Void.class);
    }
    
    @Test
    void testGuardarCalendarioConFalloPorDuplicado() {
        Usuario usuario = new Usuario();
        usuario.setUsername("userCalendario");
        Long idUsuario = restTemplate.postForEntity(baseUrl + "/guardarUsuario", usuario, Long.class).getBody();

        Calendario nuevoCalendario = new Calendario();
        nuevoCalendario.setNombre("Calendario Extra");

        ResponseEntity<Long> responseError = restTemplate.postForEntity(baseUrl + "/guardarCalendario/" + idUsuario, nuevoCalendario, Long.class);
        
        assertThat(responseError.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        
        
        restTemplate.exchange(baseUrl + "/eliminarUsuario/" + idUsuario, HttpMethod.DELETE, null, Void.class);
    }
}