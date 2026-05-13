package com.example.restservice.Controller;
import java.util.concurrent.atomic.AtomicLong;
import com.example.restservice.Entity.*;
import com.example.restservice.Dao.*;
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
  public ResponseEntity <Long> guardarUsuario (@RequestBody Usuario usuario) {
    try{
      // 1. Guardamos el usuario y recogemos el objeto Usuario devuelto
      Usuario usuarioGuardado = gestDatosService.guardarUsuario(usuario);
      
      // 2. Le pedimos el ID a ese usuario guardado
      Long idUsuario = usuarioGuardado.getId();
      
      return new ResponseEntity<>(idUsuario, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
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
  @PostMapping("/guardarTarea") // <-- Quitamos el /{idUsuario}
  public ResponseEntity <Long> guardarTarea (@RequestParam("idUsuarios") List<Long> idUsuarios, @RequestBody Tarea tarea) {
    try{
      // Pasamos la lista de idUsuarios al servicio
      Long idTarea = gestDatosService.guardarTarea(tarea, idUsuarios);
      return new ResponseEntity<>(idTarea, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
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
  public ResponseEntity <Long> guardarCalendario (@PathVariable("idUsuario") Long idUsuario, @RequestBody Calendario calendario) {
    try{
      // Le pasamos el calendario y el id del usuario al servicio
      Long idCalendario = gestDatosService.guardarCalendario(calendario, idUsuario);
      return new ResponseEntity<>(idCalendario, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace(); 
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
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
  public ResponseEntity<Long> guardarCategoria(@PathVariable("idUsuario") Long idUsuario, @RequestBody Categoria categoria) {
    try {
      // Ahora sí recibimos el idUsuario por la URL
      Long idCategoria = gestDatosService.guardarCategoria(categoria, idUsuario);
      return new ResponseEntity<>(idCategoria, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      e.printStackTrace();
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
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
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
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
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
      e.printStackTrace();
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
      e.printStackTrace();
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
      e.printStackTrace();
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
      e.printStackTrace();
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
  public ResponseEntity <Tarea> modificarTarea(@PathVariable ("idTarea") Long idTarea, @RequestBody Tarea tareaModificada){
    try{
      Tarea tarea = gestDatosService.modificarTarea(idTarea, tareaModificada);
      return new ResponseEntity<>(tarea, HttpStatus.OK);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
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
  public ResponseEntity <Calendario> modificarCalendario(@PathVariable ("idCalendario") Long idCalendario, @RequestBody Calendario calendarioModificado){
    try{
      Calendario calendario = gestDatosService.modificarCalendario(idCalendario, calendarioModificado);
      return new ResponseEntity<>(calendario, HttpStatus.OK);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}