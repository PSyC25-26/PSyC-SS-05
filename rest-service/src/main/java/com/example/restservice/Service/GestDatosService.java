package com.example.restservice.Service;

import org.springframework.stereotype.Service;
import com.example.restservice.Entity.*;
import java.util.List;
import java.util.ArrayList;
import jakarta.transaction.Transactional;

import com.example.restservice.Dao.*;


@Service
public class GestDatosService {
    private final UsuarioDAO usuarioDAO;
    private final CategoriaDAO categoriaDAO;
    private final TareaDAO tareaDAO;
    private final CalendarioDAO calendarioDAO;

    public GestDatosService(UsuarioDAO usuarioDAO, CategoriaDAO categoriaDAO, TareaDAO tareaDAO, CalendarioDAO calendarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.categoriaDAO = categoriaDAO;
        this.tareaDAO = tareaDAO;
        this.calendarioDAO = calendarioDAO;
    }

    public Long guardarUsuario(Usuario usuario){
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        Usuario usuarioGuardado = usuarioDAO.save(usuario);
        return usuarioGuardado.getId();
    }

    public long guardarTarea(Tarea tarea){
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }

        if (tarea.getFechaInicio() != null && tarea.getFechaFin() != null && tarea.getFechaInicio().isAfter(tarea.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        Tarea tareaGuardada = tareaDAO.save(tarea);
        return tareaGuardada.getId();
    }

    public long guardarCalendario(Calendario calendario){
        if (calendario == null) {
            throw new IllegalArgumentException("El calendario no puede ser nulo");
        }
        Usuario propietario = calendario.getPropietario();
        if (propietario == null || propietario.getId() == null) {
            throw new IllegalArgumentException("El propietario del calendario no puede ser nulo y debe tener un ID válido");
        }

        Calendario calendarioGuardado = calendarioDAO.save(calendario);
        return calendarioGuardado.getId();
    }

    @Transactional
    public void eliminarTarea (Long idTarea){
        List <Usuario> usuarios = usuarioDAO.findByTareas_Id(idTarea);
        for (Usuario usuario : usuarios){
            usuario.getTareas().removeIf(tarea -> tarea.getId().equals(idTarea));
        }
        
        tareaDAO.deleteById(idTarea);
        
    }

    public List <Tarea> obtenerTareasPorUsuario (Long idUsuario){
        Usuario usuario = usuarioDAO.findById(idUsuario).
            orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        List <Tarea> tareas = tareaDAO.findByUsuarios_Id(idUsuario);
        
        return tareas; 
    }

    public void eliminarUsuario (Long idUsuario){
        if (idUsuario == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }

        if(usuarioDAO.existsById(idUsuario)){
            usuarioDAO.deleteById(idUsuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    public List <Usuario> cargarUsuarios(){
        ArrayList <Usuario> usuariosCargados = new ArrayList<Usuario>();
        Iterable <Usuario> usuariosBD = usuarioDAO.findAll();
        for (Usuario usuario : usuariosBD){
            usuariosCargados.add(usuario);
        }
        return usuariosCargados;
    }

    public List <Tarea> cargarTareasPorUsuario (Long idUsuario){
        List <Tarea> tareas = tareaDAO.findByUsuarios_Id(idUsuario);
        return tareas;
    }

    public Calendario cargarCalendarioPorUsuario (long idUsuario){
        Calendario calendario = calendarioDAO.findByPropietarioId(idUsuario);
        return calendario;
    }

    public Tarea modificarTarea (Long idTarea, Tarea tareaModificada){
        Tarea tarea = tareaDAO.findById(idTarea).
            orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        // Actualizar los campos de la tarea existente con los valores de tareaModificada
        tarea.setTitulo(tareaModificada.getTitulo());
        tarea.setDescripcion(tareaModificada.getDescripcion());
        tarea.setFechaInicio(tareaModificada.getFechaInicio());
        tarea.setFechaFin(tareaModificada.getFechaFin());
        tarea.setCategoria(tareaModificada.getCategoria());
        tareaDAO.save(tarea);
        return tarea;
    }
}