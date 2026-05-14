package com.example.restservice.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.restservice.Dao.CalendarioDAO;
import com.example.restservice.Dao.CategoriaDAO;
import com.example.restservice.Dao.TareaDAO;
import com.example.restservice.Dao.UsuarioDAO;
import com.example.restservice.Entity.Calendario;
import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Usuario;

/**
 * @class GestDatosServiceTest
 * @brief Pruebas unitarias de la clase GestDatosService.
 * 
 * Verifica el correcto funcionamiento de los métodos
 * del servicio mediante el uso de Mockito.
 */
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

    /**
     * @brief Comprueba que se devuelve la lista de usuarios.
     */
    @Test
    void cargarUsuarios_deberiaDevolverLista() {

        when(usuarioDAO.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));

        List<Usuario> resultado = gestDatosService.cargarUsuarios();

        assertThat(resultado).hasSize(2);
    }

    /**
     * @brief Comprueba el guardado correcto de un usuario.
     */
    @Test
    void guardarUsuario_ok() {

        Usuario usuario = new Usuario();

        usuario.setUsername("test");

        Usuario usuarioGuardado = new Usuario();

        usuarioGuardado.setId(1L);
        
        when(usuarioDAO.save(usuario)).thenReturn(usuarioGuardado);
        
        Usuario resultado = gestDatosService.guardarUsuario(usuario);
        
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    /**
     * @brief Comprueba que se lanza una excepción al guardar un usuario nulo.
     */
    @Test
    void guardarUsuario_null_deberiaLanzarExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarUsuario(null);
        });
    }

    /**
     * @brief Comprueba que no se puede guardar una tarea nula.
     */
    @Test
    void guardarTarea_null_deberiaLanzarExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarTarea(null, List.of());
        });
    }

    /**
     * @brief Comprueba que las fechas inválidas generan excepción.
     */
    @Test
    void guardarTarea_fechasInvalidas_deberiaLanzarExcepcion() {

        Tarea tarea = new Tarea();

        tarea.setFechaInicio(LocalDateTime.now());
        tarea.setFechaFin(LocalDateTime.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarTarea(tarea, List.of());
        });
    }

    /**
     * @brief Comprueba la obtención de tareas de un usuario.
     */
    @Test
    void obtenerTareasPorUsuario_ok() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaDAO.findByUsuarios_Id(1L)).thenReturn(List.of(new Tarea()));

        List<Tarea> resultado = gestDatosService.obtenerTareasPorUsuario(1L);

        assertThat(resultado).hasSize(1);
    }

    /**
     * @brief Comprueba el comportamiento cuando el usuario no existe.
     */
    @Test
    void obtenerTareasPorUsuario_usuarioNoExiste() {

        when(usuarioDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.obtenerTareasPorUsuario(1L);
        });
    }

    /**
     * @brief Comprueba que no se puede guardar una categoría nula.
     */
    @Test
    void guardarCategoria_null_deberiaLanzarExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarCategoria(null, 1L);
        });
    }

    /**
     * @brief Comprueba el comportamiento con una lista vacía de usuarios.
     */
    @Test
    void cargarUsuarios_listaVacia() {

        when(usuarioDAO.findAll()).thenReturn(List.of());

        List<Usuario> resultado = gestDatosService.cargarUsuarios();

        assertThat(resultado).isEmpty();
    }

    /**
     * @brief Comprueba el guardado correcto de una categoría.
     */
    @Test
    void guardarCategoria_ok() {

        Categoria categoria = new Categoria();

        Categoria guardada = new Categoria();

        guardada.setId(1L);
        
        Usuario usuarioMock = new Usuario();

        usuarioMock.setId(1L);

        when(usuarioDAO.findById(1L)).thenReturn(java.util.Optional.of(usuarioMock));
        when(categoriaDAO.save(categoria)).thenReturn(guardada);
        
        Long id = gestDatosService.guardarCategoria(categoria, 1L);
        
        assertThat(id).isEqualTo(1L);
    }

    /**
     * @brief Comprueba el comportamiento cuando un usuario no tiene tareas.
     */
    @Test
    void obtenerTareasPorUsuario_listaVacia() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaDAO.findByUsuarios_Id(1L)).thenReturn(List.of());

        List<Tarea> resultado = gestDatosService.obtenerTareasPorUsuario(1L);

        assertThat(resultado).isEmpty();
    }

    /**
     * @brief Comprueba el guardado de usuario sin nombre.
     */
    @Test
    void guardarUsuario_sinUsername() {

        Usuario usuario = new Usuario();

        Usuario guardado = new Usuario();

        guardado.setId(2L);
        
        when(usuarioDAO.save(usuario)).thenReturn(guardado);
        
        Usuario resultado = gestDatosService.guardarUsuario(usuario);
        
        assertThat(resultado.getId()).isEqualTo(2L);
    }

    /**
     * @brief Comprueba el guardado correcto de un calendario.
     */
    @Test
    void guardarCalendario_ok() {

        Calendario calendario = new Calendario();

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));
        when(calendarioDAO.findByPropietarioId(1L)).thenReturn(null);

        Calendario guardado = new Calendario();

        guardado.setId(1L);

        when(calendarioDAO.save(calendario)).thenReturn(guardado);

        long id = gestDatosService.guardarCalendario(calendario, 1L);

        assertThat(id).isEqualTo(1L);
    }

    /**
     * @brief Comprueba que no se puedan crear calendarios duplicados.
     */
    @Test
    void guardarCalendario_yaExiste() {

        Calendario calendario = new Calendario();

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(calendarioDAO.findByPropietarioId(1L)).thenReturn(new Calendario());

        assertThrows(IllegalArgumentException.class, () -> {
            gestDatosService.guardarCalendario(calendario, 1L);
        });
    }

    /**
     * @brief Comprueba la eliminación de tareas.
     */
    @Test
    void eliminarTarea_ok() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        usuario.setTareas(new java.util.ArrayList<>()); 

        when(usuarioDAO.findByTareas_Id(1L)).thenReturn(List.of(usuario));

        gestDatosService.eliminarTarea(1L);
    }

    /**
     * @brief Comprueba la eliminación de usuarios.
     */
    @Test
    void eliminarUsuario_ok() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        Tarea tarea = new Tarea();

        tarea.setUsuarios(new java.util.ArrayList<>(List.of(usuario))); 

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaDAO.findByUsuarios_Id(1L)).thenReturn(List.of(tarea));

        gestDatosService.eliminarUsuario(1L);
    }

    /**
     * @brief Comprueba la modificación de tareas.
     */
    @Test
    void modificarTarea_ok() {

        Tarea tarea = new Tarea();

        tarea.setId(1L);

        Tarea modificada = new Tarea();

        modificada.setTitulo("nuevo");

        when(tareaDAO.findById(1L)).thenReturn(Optional.of(tarea));

        Tarea resultado = gestDatosService.modificarTarea(1L, modificada);

        assertThat(resultado).isNotNull();
    }

    /**
     * @brief Comprueba la modificación de calendarios.
     */
    @Test
    void modificarCalendario_ok() {

        Calendario calendario = new Calendario();

        calendario.setId(1L);

        Calendario modificado = new Calendario();

        modificado.setNombre("nuevo");

        when(calendarioDAO.findById(1L)).thenReturn(Optional.of(calendario));

        Calendario res = gestDatosService.modificarCalendario(1L, modificado);

        assertThat(res).isNotNull();
    }

    /**
     * @brief Comprueba la obtención de la categoría de una tarea.
     */
    @Test
    void obtenerCategoriaPorTarea_ok() {

        Tarea tarea = new Tarea();

        Categoria categoria = new Categoria();

        tarea.setCategoria(categoria);

        when(tareaDAO.findById(1L)).thenReturn(Optional.of(tarea));

        Categoria res = gestDatosService.obtenerCategoriaPorTarea(1L);

        assertThat(res).isNotNull();
    }

}