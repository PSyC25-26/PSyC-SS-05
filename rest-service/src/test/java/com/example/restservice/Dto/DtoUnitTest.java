package com.example.restservice.Dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.restservice.Dto.UsuarioDTO;
import com.example.restservice.Dto.TareaDTO;
import com.example.restservice.Dto.CalendarioDTO;
import com.example.restservice.Dto.CategoriaDTO;
import com.example.restservice.Entity.Usuario.TipoUsuario;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.example.restservice.Entity.Usuario.TipoUsuario;

/**
 * @class DtoUnitTest
 * @brief Clase de pruebas unitarias para garantizar la cobertura de los Data Transfer Objects (DTOs).
 * * Invoca todos los métodos getter y setter de los DTOs para asegurar que la transferencia
 * de datos entre la API y la persistencia sea correcta y esté completamente cubierta por tests.
 */
class DtoUnitTest {

    /**
     * @brief Prueba los métodos Getter y Setter de UsuarioDTO.
     */
    @Test
    void testUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setUsername("usuarioTest");
        usuarioDTO.setEmail("test@deusto.es");
        usuarioDTO.setPassword("securePass123");
        usuarioDTO.setTipoUsuario(TipoUsuario.PARTICULAR);

        assertNotNull(usuarioDTO);
        assertEquals("usuarioTest", usuarioDTO.getUsername());
        assertEquals("test@deusto.es", usuarioDTO.getEmail());
        assertEquals("securePass123", usuarioDTO.getPassword());
        assertEquals(TipoUsuario.PARTICULAR, usuarioDTO.getTipoUsuario());
    }

    /**
     * @brief Prueba los métodos Getter y Setter de TareaDTO.
     */
    @Test
    void testTareaDTO() {
        TareaDTO tareaDTO = new TareaDTO();
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = LocalDateTime.now().plusDays(2);

        tareaDTO.setTitulo("Estudiar Calidad");
        tareaDTO.setDescripcion("Repasar patrones de diseño y SonarCloud");
        tareaDTO.setFechaInicio(inicio);
        tareaDTO.setFechaFin(fin);
        tareaDTO.setIdCategoria(5L);

        assertNotNull(tareaDTO);
        assertEquals("Estudiar Calidad", tareaDTO.getTitulo());
        assertEquals("Repasar patrones de diseño y SonarCloud", tareaDTO.getDescripcion());
        assertEquals(inicio, tareaDTO.getFechaInicio());
        assertEquals(fin, tareaDTO.getFechaFin());
        assertEquals(5L, tareaDTO.getIdCategoria());
    }

    /**
     * @brief Prueba los métodos Getter y Setter de CalendarioDTO.
     */
    @Test
    void testCalendarioDTO() {
        CalendarioDTO calendarioDTO = new CalendarioDTO();
        
        calendarioDTO.setNombre("Calendario Académico 2026");

        assertNotNull(calendarioDTO);
        assertEquals("Calendario Académico 2026", calendarioDTO.getNombre());
    }

    /**
     * @brief Prueba los métodos Getter y Setter de CategoriaDTO.
     */
    @Test
    void testCategoriaDTO() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        
        categoriaDTO.setNombre("Trabajos Universidad");
        categoriaDTO.setColor("#00FF00");

        assertNotNull(categoriaDTO);
        assertEquals("Trabajos Universidad", categoriaDTO.getNombre());
        assertEquals("#00FF00", categoriaDTO.getColor());
    }
}