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
        Tarea tareaGuardada = tareaDAO.save(tarea);
        return tareaGuardada.getId();
    }

    public long guardarCalendario(Calendario calendario){
        if (calendario == null) {
            throw new IllegalArgumentException("El calendario no puede ser nulo");
        }
        Calendario calendarioGuardado = calendarioDAO.save(calendario);
        return calendarioGuardado.getId();
    }

    @Transactional
    public void eliminarTarea (Long idUsuario, Long idTarea){
        Usuario usuario = usuarioDAO.findById(idUsuario).
            orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        Tarea tarea = tareaDAO.findById(idTarea).
            orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        
        for (Usuario u : tarea.getUsuarios()){
            if (!u.getId().equals(idUsuario)){
                throw new IllegalArgumentException("El usuario no tiene permiso para eliminar esta tarea");
            }
        }

        usuario.getTareas().remove(tarea);
    }

    public List <Tarea> obtenerTareasPorUsuario (Long idUsuario){
        Usuario usuario = usuarioDAO.findById(idUsuario).
            orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        List <Tarea> tareas = tareaDAO.findByUsuario(idUsuario);
        
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
}