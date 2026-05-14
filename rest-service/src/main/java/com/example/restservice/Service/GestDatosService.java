package com.example.restservice.Service;

import org.springframework.stereotype.Service;
import com.example.restservice.Entity.*;
import com.vaadin.flow.data.provider.DataProvider;

import java.util.List;
import java.util.ArrayList;
import jakarta.transaction.Transactional;

import com.example.restservice.Dao.*;


/**
 * @brief Servicio principal encargado de gestionar la lógica
 * de negocio del sistema.
 * 
 * Esta clase coordina las operaciones relacionadas con usuarios,
 * tareas, categorías y calendarios.
 */
@Service
public class GestDatosService {

    private final UsuarioDAO usuarioDAO;
    private final CategoriaDAO categoriaDAO;
    private final TareaDAO tareaDAO;
    private final CalendarioDAO calendarioDAO;

    /**
     * @brief Constructor del servicio.
     * 
     * @param usuarioDAO acceso a datos de usuarios.
     * @param categoriaDAO acceso a datos de categorías.
     * @param tareaDAO acceso a datos de tareas.
     * @param calendarioDAO acceso a datos de calendarios.
     */
    public GestDatosService(UsuarioDAO usuarioDAO, CategoriaDAO categoriaDAO, TareaDAO tareaDAO, CalendarioDAO calendarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.categoriaDAO = categoriaDAO;
        this.tareaDAO = tareaDAO;
        this.calendarioDAO = calendarioDAO;
    }

    
    /**
     * @brief Guarda una tarea asociada a varios usuarios.
     * 
     * @param tarea tarea a almacenar.
     * @param idUsuarios lista de identificadores de usuarios.
     * @return identificador de la tarea creada.
     */
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

    /**
     * @brief Guarda un calendario asociado a un usuario.
     * 
     * @param calendario calendario a almacenar.
     * @param idUsuario identificador del usuario propietario.
     * @return identificador del calendario creado.
     */
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

    /**
     * @brief Guarda una nueva categoría.
     * 
     * @param categoria categoría a almacenar.
     * @param idUsuario identificador del usuario propietario.
     * @return identificador de la categoría creada.
     */
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

    /**
     * @brief Obtiene las categorías asociadas a un usuario.
     * 
     * @param idUsuario identificador del usuario.
     * @return lista de categorías.
     */
    public List<Categoria> obtenerCategoriasPorUsuario(Long idUsuario) {
        return categoriaDAO.findByUsuario_Id(idUsuario);
    }


    /**
     * @brief Elimina una tarea del sistema.
     * 
     * @param idTarea identificador de la tarea.
     */
    @Transactional
    public void eliminarTarea (Long idTarea){

        List <Usuario> usuarios = usuarioDAO.findByTareas_Id(idTarea);

        for (Usuario usuario : usuarios){
            usuario.getTareas().removeIf(tarea -> tarea.getId().equals(idTarea));
        }
        
        tareaDAO.deleteById(idTarea);
    }

    /**
     * @brief Obtiene las tareas asociadas a un usuario.
     * 
     * @param idUsuario identificador del usuario.
     * @return lista de tareas del usuario.
     */
    public List <Tarea> obtenerTareasPorUsuario (Long idUsuario){

        Usuario usuario = usuarioDAO.findById(idUsuario).
            orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        List <Tarea> tareas = tareaDAO.findByUsuarios_Id(idUsuario);
        
        return tareas; 
    }

    /**
     * @brief Obtiene la categoría asociada a una tarea.
     * 
     * @param idTarea identificador de la tarea.
     * @return categoría asociada.
     */
    public Categoria obtenerCategoriaPorTarea (Long idTarea){

        Tarea tarea = tareaDAO.findById(idTarea).
            orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        
        Categoria categoria = tarea.getCategoria();
        return categoria;
    }


    /**
     * @brief Elimina un usuario del sistema.
     * 
     * @param idUsuario identificador del usuario.
     */
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

    /**
     * @brief Carga todos los usuarios almacenados.
     * 
     * @return lista de usuarios.
     */
    public List <Usuario> cargarUsuarios(){

        ArrayList <Usuario> usuariosCargados = new ArrayList<Usuario>();

        Iterable <Usuario> usuariosBD = usuarioDAO.findAll();

        for (Usuario usuario : usuariosBD){
            usuariosCargados.add(usuario);
        }

        return usuariosCargados;
    }

    /**
     * @brief Obtiene las tareas asociadas a un usuario.
     * 
     * @param idUsuario identificador del usuario.
     * @return lista de tareas.
     */
    public List <Tarea> cargarTareasPorUsuario (Long idUsuario){

        List <Tarea> tareas = tareaDAO.findByUsuarios_Id(idUsuario);

        return tareas;
    }

    /**
     * @brief Obtiene el calendario asociado a un usuario.
     * 
     * @param idUsuario identificador del usuario.
     * @return calendario asociado.
     */
    public Calendario cargarCalendarioPorUsuario (long idUsuario){

        Calendario calendario = calendarioDAO.findByPropietarioId(idUsuario);

        return calendario;
    }

    /**
     * @brief Modifica una tarea existente.
     * 
     * @param idTarea identificador de la tarea.
     * @param tareaModificada nuevos datos de la tarea.
     * @return tarea modificada.
     */
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

    /**
     * @brief Obtiene todas las tareas almacenadas.
     * 
     * @return lista de tareas.
     */
    public List<Tarea> cargarTareas() {
        return (List<Tarea>) tareaDAO.findAll();
    }

    /**
     * @brief Obtiene todas las categorías almacenadas.
     * 
     * @return lista de categorías.
     */
    public List<Categoria> cargarCategorias() {
        return (List<Categoria>) categoriaDAO.findAll();
    }

    /**
     * @brief Modifica un calendario existente.
     * 
     * @param idCalendario identificador del calendario.
     * @param calendarioModificado nuevos datos del calendario.
     * @return calendario modificado.
     */
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
    
    /**
     * @brief Guarda un usuario en la base de datos.
     * 
     * @param usuario usuario a almacenar.
     * @return usuario guardado.
     */
    public Usuario guardarUsuario(Usuario usuario) {

        // Añadimos la comprobación para que pase el test
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        
        // Usamos tu UsuarioDAO real
        return usuarioDAO.save(usuario);
    }

    /**
     * @brief Autentica un usuario en el sistema.
     * 
     * @param username nombre de usuario.
     * @param password contraseña del usuario.
     * @return usuario autenticado o null si no existe.
     */
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

    /**
     * @brief Obtiene tareas filtradas por categoría.
     * 
     * @param categoria categoría utilizada como filtro.
     * @return lista de tareas asociadas a la categoría.
     */
    public List<Tarea> obtenerTareasPorCategoria(Categoria categoria) {

        if (categoria == null) {
            return listarTodasLasTareas();
        }

        return tareaDAO.findByCategoria(categoria);
    }
    
}