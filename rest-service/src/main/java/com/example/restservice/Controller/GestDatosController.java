package com.example.restservice.Controller;
import java.util.concurrent.atomic.AtomicLong;
import com.example.restservice.Entity.*;
import com.example.restservice.Dao.*;
import com.example.restservice.Dto.UsuarioDTO;
import com.example.restservice.Dto.TareaDTO;
import com.example.restservice.Dto.CalendarioDTO;
import com.example.restservice.Dto.CategoriaDTO;
import com.example.restservice.Service.GestDatosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @brief Controlador REST para la gestión de usuarios, tareas,
 * categorías y calendarios.
 * 
 * Esta clase expone los endpoints necesarios para realizar
 * operaciones CRUD sobre las distintas entidades del sistema.
 */
@RestController
@RequestMapping("/gestDatos")
public class GestDatosController {
  private final GestDatosService gestDatosService;
  private static final Logger logger = LoggerFactory.getLogger(GestDatosController.class);
  /**
   * @brief Constructor del controlador.
   * 
   * @param gestDatosService servicio encargado de la lógica de negocio.
   */
  public GestDatosController(GestDatosService gestDatosService) {
    this.gestDatosService = gestDatosService;
  }

  /**
   * @brief Guarda un nuevo usuario en la base de datos.
   * 
   * Este método recibe un usuario mediante una petición HTTP POST
   * y delega la operación al servicio correspondiente.
   * 
   * @param usuario usuario recibido en la petición.
   * @return identificador del usuario creado.
   */
  @PostMapping("/guardarUsuario")
  public ResponseEntity<Long> guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    try {
      Usuario usuarioEntity = new Usuario();

      usuarioEntity.setUsername(usuarioDTO.getUsername());
      usuarioEntity.setEmail(usuarioDTO.getEmail());
      usuarioEntity.setPassword(usuarioDTO.getPassword());
      usuarioEntity.setTipoUsuario(usuarioDTO.getTipoUsuario());

      Usuario usuarioGuardado = gestDatosService.guardarUsuario(usuarioEntity);

      return new ResponseEntity<>(usuarioGuardado.getId(), HttpStatus.CREATED);
      
    } catch (IllegalArgumentException e) {
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  
  /**
   * @brief Guarda una nueva tarea.
   * 
   * La tarea se asocia a los usuarios cuyos identificadores
   * se reciben en la petición.
   * 
   * @param idUsuarios lista de identificadores de usuarios.
   * @param tarea tarea que se desea almacenar.
   * @return identificador de la tarea creada.
   */
  @PostMapping("/guardarTarea")
  public ResponseEntity <Long> guardarTarea (@RequestParam("idUsuarios") List<Long> idUsuarios, @RequestBody TareaDTO tareaDTO) { // <-- 1. Usamos el DTO
    try{
      
      Tarea tareaEntity = new Tarea();
      
      tareaEntity.setTitulo(tareaDTO.getTitulo());
      tareaEntity.setDescripcion(tareaDTO.getDescripcion());
      tareaEntity.setFechaInicio(tareaDTO.getFechaInicio());
      tareaEntity.setFechaFin(tareaDTO.getFechaFin());
      
      Categoria categoria = new Categoria();
      categoria.setId(tareaDTO.getIdCategoria());
      tareaEntity.setCategoria(categoria);

      Long idTarea = gestDatosService.guardarTarea(tareaEntity, idUsuarios);

      return new ResponseEntity<>(idTarea, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  
  /**
   * @brief Guarda un calendario asociado a un usuario.
   * 
   * @param idUsuario identificador del usuario propietario.
   * @param calendario calendario a almacenar.
   * @return identificador del calendario creado.
   */
  @PostMapping("/guardarCalendario/{idUsuario}")
  public ResponseEntity<Long> guardarCalendario(@PathVariable("idUsuario") Long idUsuario, @RequestBody CalendarioDTO calendarioDTO) { // <-- 1. Recibe el DTO
    try {
      Calendario calendarioEntity = new Calendario();

      calendarioEntity.setNombre(calendarioDTO.getNombre());

      Long idCalendario = gestDatosService.guardarCalendario(calendarioEntity, idUsuario);

      return new ResponseEntity<>(idCalendario, HttpStatus.CREATED);
    } 
    catch (IllegalArgumentException e) {
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } 
    catch (Exception e) {
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  /**
   * @brief Guarda una categoría asociada a un usuario.
   * 
   * @param idUsuario identificador del usuario.
   * @param categoria categoría que se desea almacenar.
   * @return identificador de la categoría creada.
   */
  @PostMapping("/guardarCategoria/{idUsuario}")
  public ResponseEntity<Long> guardarCategoria(@PathVariable("idUsuario") Long idUsuario, @RequestBody CategoriaDTO categoriaDTO) { // <-- 1. Recibe el DTO
    try {
      Categoria categoriaEntity = new Categoria();
      
      categoriaEntity.setNombre(categoriaDTO.getNombre());
      categoriaEntity.setColor(categoriaDTO.getColor());

      Long idCategoria = gestDatosService.guardarCategoria(categoriaEntity, idUsuario);

      return new ResponseEntity<>(idCategoria, HttpStatus.CREATED);

    } catch (IllegalArgumentException e) {
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    } catch (Exception e) {
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  
  /**
   * @brief Elimina un usuario del sistema.
   * 
   * @param idUsuario identificador del usuario a eliminar.
   * @return respuesta HTTP sin contenido.
   */
  @DeleteMapping("/eliminarUsuario/{idUsuario}")
  public ResponseEntity <Void> eliminarUsuario (@PathVariable ("idUsuario") Long idUsuario) {
    try{
      gestDatosService.eliminarUsuario(idUsuario);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    catch (IllegalArgumentException e){
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @brief Elimina una tarea existente.
   * 
   * @param idTarea identificador de la tarea a eliminar.
   * @return respuesta HTTP sin contenido.
   */
  @DeleteMapping("/eliminarTarea/{idTarea}")
  public ResponseEntity <Void> eliminarTarea (@PathVariable ("idTarea") Long idTarea) {
    try{
      gestDatosService.eliminarTarea(idTarea);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    catch (IllegalArgumentException e){
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e );
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @brief Obtiene todos los usuarios registrados.
   * 
   * @return lista de usuarios almacenados.
   */
  @GetMapping("/obtenerUsuarios")
  public ResponseEntity <List<Usuario>> obtenerUsuarios(){
    try{
      List <Usuario> usuarios = gestDatosService.cargarUsuarios();
      return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @brief Obtiene las tareas asociadas a un usuario.
   * 
   * @param idUsuario identificador del usuario.
   * @return lista de tareas del usuario.
   */
  @GetMapping ("/obtenerTareasPorUsuario/{idUsuario}")
  public ResponseEntity <List<Tarea>> obtenerTareasPorUsuario(@PathVariable ("idUsuario") Long idUsuario){
    try{
      List <Tarea> tareas = gestDatosService.cargarTareasPorUsuario(idUsuario);
      return new ResponseEntity<>(tareas, HttpStatus.OK);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @brief Obtiene el calendario asociado a un usuario.
   * 
   * @param idUsuario identificador del usuario.
   * @return calendario del usuario.
   */
  @GetMapping ("/obtenerCalendario/{idUsuario}")
  public ResponseEntity <Calendario> obtenerCalendarioPorUsuario(@PathVariable ("idUsuario") Long idUsuario){
    try{
      Calendario calendario = gestDatosService.cargarCalendarioPorUsuario(idUsuario);
      return new ResponseEntity<>(calendario, HttpStatus.OK);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @brief Obtiene la categoría asociada a una tarea.
   * 
   * @param idTarea identificador de la tarea.
   * @return categoría asociada a la tarea.
   */
  @GetMapping ("/obtenerCategoriaPorTarea/{idTarea}")
  public ResponseEntity <Categoria> obtenerCategoriaPorTarea(@PathVariable ("idTarea") Long idTarea){
    try{
      Categoria categoria = gestDatosService.obtenerCategoriaPorTarea(idTarea);
      return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e );
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @brief Modifica una tarea existente.
   * 
   * @param idTarea identificador de la tarea.
   * @param tareaModificada nuevos datos de la tarea.
   * @return tarea modificada.
   */
  @PutMapping ("/modificarTarea/{idTarea}")
  public ResponseEntity <Tarea> modificarTarea(@PathVariable ("idTarea") Long idTarea, @RequestBody TareaDTO tareaDTO){ 
    try{
      
      Tarea tareaEntity = new Tarea();
      
      tareaEntity.setTitulo(tareaDTO.getTitulo());
      tareaEntity.setDescripcion(tareaDTO.getDescripcion());
      tareaEntity.setFechaInicio(tareaDTO.getFechaInicio());
      tareaEntity.setFechaFin(tareaDTO.getFechaFin());

      Categoria categoria = new Categoria();
      categoria.setId(tareaDTO.getIdCategoria());
      tareaEntity.setCategoria(categoria);

      Tarea tarea = gestDatosService.modificarTarea(idTarea, tareaEntity);

      return new ResponseEntity<>(tarea, HttpStatus.OK);
    }
    catch (IllegalArgumentException e){
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e );
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  /**
   * @brief Modifica un calendario existente.
   * 
   * @param idCalendario identificador del calendario.
   * @param calendarioModificado nuevos datos del calendario.
   * @return calendario modificado.
   */
  @PutMapping ("/modificarCalendario/{idCalendario}")
  public ResponseEntity <Calendario> modificarCalendario(@PathVariable ("idCalendario") Long idCalendario, @RequestBody CalendarioDTO calendarioDTO){
    try{
      // Creamos la entidad y le pasamos el nombre
      Calendario calendarioEntity = new Calendario();
      calendarioEntity.setNombre(calendarioDTO.getNombre());

      Calendario calendario = gestDatosService.modificarCalendario(idCalendario, calendarioEntity);

      return new ResponseEntity<>(calendario, HttpStatus.OK);
    }
    catch (IllegalArgumentException e){
      logger.error("Se ha producido un error de argumento ilegal", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      logger.error("Se ha producido un error inesperado", e );
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}