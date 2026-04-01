package com.example.restservice.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.example.restservice.Dao.*;
import com.example.restservice.Entity.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GestDatosServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private CategoriaDAO categoriaDAO;

    @Mock
    private TareaDAO tareaDAO;

    @Mock
    private CalendarioDAO calendarioDAO;

    @InjectMocks
    private GestDatosService gestDatosService;

    
    @Test
    void cargarUsuarios_deberiaDevolverLista() {
        when(usuarioDAO.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));

        List<Usuario> resultado = gestDatosService.cargarUsuarios();

        assertThat(resultado).hasSize(2);
    }

   
    @Test
    void guardarUsuario_ok() {
        Usuario usuario = new Usuario();
        usuario.setUsername("test");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);

        when(usuarioDAO.save(usuario)).thenReturn(usuarioGuardado);

        Long id = gestDatosService.guardarUsuario(usuario);

        assertThat(id).isEqualTo(1L);
    }

    
    @Test
    void guardarUsuario_null_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarUsuario(null);
        });
    }

   
    @Test
    void guardarTarea_null_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarTarea(null, List.of());
        });
    }

    
    @Test
    void guardarTarea_fechasInvalidas_deberiaLanzarExcepcion() {
        Tarea tarea = new Tarea();
        tarea.setFechaInicio(LocalDateTime.now());
        tarea.setFechaFin(LocalDateTime.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarTarea(tarea, List.of());
        });
    }

    
    @Test
    void obtenerTareasPorUsuario_ok() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaDAO.findByUsuarios_Id(1L)).thenReturn(List.of(new Tarea()));

        List<Tarea> resultado = gestDatosService.obtenerTareasPorUsuario(1L);

        assertThat(resultado).hasSize(1);
    }

    
    @Test
    void obtenerTareasPorUsuario_usuarioNoExiste() {
        when(usuarioDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.obtenerTareasPorUsuario(1L);
        });
    }

   
    @Test
    void guardarCategoria_null_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarCategoria(null);
        });
    }
}