package com.example.restservice.Service;

import org.springframework.stereotype.Service;
import com.example.restservice.Entity.*;
import com.vaadin.flow.data.provider.DataProvider;

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

    

    public long guardarTarea(Tarea tarea, List<Long> idUsuarios){ // <-- Cambiado a List<Long>
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }

        if (tarea.getFechaInicio() != null && tarea.getFechaFin() != null && tarea.getFechaInicio().isAfter(tarea.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        if (tarea.getUsuarios() == null) {
            tarea.setUsuarios(new ArrayList<>());
        }

        for (Long id : idUsuarios) {
            Usuario usuario = usuarioDAO.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + id + " no encontrado"));
            
            tarea.getUsuarios().add(usuario); // Añadimos cada usuario a la tarea
        }

        Tarea tareaGuardada = tareaDAO.save(tarea);
        return tareaGuardada.getId();
    }

    public long guardarCalendario(Calendario calendario, Long idUsuario) {
        if (calendario == null) {
            throw new IllegalArgumentException("El calendario no puede ser nulo");
        }

        Usuario propietario = usuarioDAO.findById(idUsuario).
            orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Calendario calendarioExistente = calendarioDAO.findByPropietarioId(idUsuario);
        if (calendarioExistente != null) {
            throw new IllegalArgumentException("Este usuario ya tiene un calendario asignado. Solo se permite uno.");
        }

        calendario.setPropietario(propietario);
        Calendario calendarioGuardado = calendarioDAO.save(calendario);
        return calendarioGuardado.getId();
    }

    public long guardarCategoria(Categoria categoria, Long idUsuario) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }
        // Buscamos el usuario en la BD y se lo asignamos a la categoría
        Usuario propietario = usuarioDAO.findById(idUsuario).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        categoria.setUsuario(propietario);
        
        Categoria categoriaGuardada = categoriaDAO.save(categoria);
        return categoriaGuardada.getId();
    }

    public List<Categoria> obtenerCategoriasPorUsuario(Long idUsuario) {
        return categoriaDAO.findByUsuario_Id(idUsuario);
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

    public Categoria obtenerCategoriaPorTarea (Long idTarea){
        Tarea tarea = tareaDAO.findById(idTarea).
            orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        
        Categoria categoria = tarea.getCategoria();
        return categoria;
    }


    @Transactional
    public void eliminarUsuario (Long idUsuario){
        Usuario usuario = usuarioDAO.findById(idUsuario).
            orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<Tarea> tareasDelUsuario = tareaDAO.findByUsuarios_Id(idUsuario);

        for (Tarea tarea : tareasDelUsuario) {
            tarea.getUsuarios().removeIf(u -> u.getId().equals(idUsuario));
            
            
            if (tarea.getUsuarios().isEmpty()) {
                tareaDAO.delete(tarea);
            } else {
                tareaDAO.save(tarea); 
            }
        }

        usuarioDAO.delete(usuario);
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
        tarea.setId(idTarea);
        tarea.setTitulo(tareaModificada.getTitulo());
        tarea.setDescripcion(tareaModificada.getDescripcion());
        tarea.setFechaInicio(tareaModificada.getFechaInicio());
        tarea.setFechaFin(tareaModificada.getFechaFin());
        tarea.setCategoria(tareaModificada.getCategoria());

        tareaDAO.save(tarea);
        return tarea;
    }

    public List<Tarea> cargarTareas() {
        return (List<Tarea>) tareaDAO.findAll();
    }

    public List<Categoria> cargarCategorias() {
        return (List<Categoria>) categoriaDAO.findAll();
    }

    public Calendario modificarCalendario(Long idCalendario, Calendario calendarioModificado){
        Calendario calendario = calendarioDAO.findById(idCalendario).
            orElseThrow(() -> new IllegalArgumentException("Calendario no encontrado"));
        
        calendario.setNombre(calendarioModificado.getNombre());

        calendarioDAO.save(calendario);
        return calendario;
    }
    // En GestDatosService.java
    public List<Tarea> listarTodasLasTareas() {
        return (List<Tarea>) tareaDAO.findAll();
    }

    public void guardarTarea(Tarea tarea) {
        tareaDAO.save(tarea);
    }

    public void eliminarTarea(Tarea tarea) {
        tareaDAO.delete(tarea);
    }

    public DataProvider<Tarea, Void> getAllTareas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTareas'");
    }
    
    public Usuario guardarUsuario(Usuario usuario) {
        // Añadimos la comprobación para que pase el test
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        
        // Usamos tu UsuarioDAO real
        return usuarioDAO.save(usuario);
    }

    public Usuario autenticarUsuario(String username, String password) {
        // Usamos tu usuarioDAO para traer todos los usuarios
        Iterable<Usuario> todosLosUsuarios = usuarioDAO.findAll();
        
        for (Usuario u : todosLosUsuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u; // Coinciden usuario y contraseña
            }
        }
        return null; // No coinciden
    }
    public List<Tarea> obtenerTareasPorCategoria(Categoria categoria) {
        if (categoria == null) {
            return listarTodasLasTareas();
        }
        return tareaDAO.findByCategoria(categoria);
    }
    
}