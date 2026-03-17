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


@RestController
@RequestMapping("/gestDatos")
public class GestDatosController {
  private final GestDatosService gestDatosService;

  public GestDatosController(GestDatosService gestDatosService) {
    this.gestDatosService = gestDatosService;
  }

  @PostMapping("/guardarUsuario")
  public ResponseEntity <Long> guardarUsuario (@RequestBody Usuario usuario) {
    try{
      Long idUsuario = gestDatosService.guardarUsuario(usuario);
      return new ResponseEntity<>(idUsuario, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  
  @PostMapping("/guardarTarea")
  public ResponseEntity <Long> guardarTarea (@RequestBody Tarea tarea) {
    try{
      Long idTarea = gestDatosService.guardarTarea(tarea);
      return new ResponseEntity<>(idTarea, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  
  @PostMapping("/guardarCalendario")
  public ResponseEntity <Long> guardarCalelndario (@RequestBody Calendario calendario) {
    try{
      Long idCalendario = gestDatosService.guardarCalendario(calendario);
      return new ResponseEntity<>(idCalendario, HttpStatus.CREATED);
    }
    catch (IllegalArgumentException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
  
  @DeleteMapping("/eliminarUsuario/{idUsuario}")
  public ResponseEntity <Void> eliminarUsuario (@PathVariable ("idUsuario") Long idUsuario) {
    try{
      gestDatosService.eliminarUsuario(idUsuario);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    catch (IllegalArgumentException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/eliminarTarea/{idTarea}")
  public ResponseEntity <Void> eliminarTarea (@PathVariable ("idTarea") Long idTarea) {
    try{
      gestDatosService.eliminarTarea(idTarea);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    catch (IllegalArgumentException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/obtenerUsuarios")
  public ResponseEntity <List<Usuario>> obtenerUsuarios(){
    try{
      List <Usuario> usuarios = gestDatosService.cargarUsuarios();
      return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping ("/obtenerTareasPorUsuario/{idUsuario}")
  public ResponseEntity <List<Tarea>> obtenerTareasPorUsuario(@PathVariable ("idUsuario") Long idUsuario){
    try{
      List <Tarea> tareas = gestDatosService.cargarTareasPorUsuario(idUsuario);
      return new ResponseEntity<>(tareas, HttpStatus.OK);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping ("/obtenerCalendario/{idUsuario}")
  public ResponseEntity <Calendario> obtenerCalendarioPorUsuario(@PathVariable ("idUsuario") Long idUsuario){
    try{
      Calendario calendario = gestDatosService.cargarCalendarioPorUsuario(idUsuario);
      return new ResponseEntity<>(calendario, HttpStatus.OK);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping ("/modificarTarea/{idTarea}")
  public ResponseEntity <Tarea> modificarTarea(@PathVariable ("idTarea") Long idTarea, @RequestBody Tarea tareaModificada){
    try{
      Tarea tarea = gestDatosService.modificarTarea(idTarea, tareaModificada);
      return new ResponseEntity<>(tarea, HttpStatus.OK);
    }
    catch (IllegalArgumentException e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}