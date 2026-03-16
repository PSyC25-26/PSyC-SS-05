package com.example.restservice.Controller;
import java.util.concurrent.atomic.AtomicLong;
import com.example.restservice.Entity.*;
import com.example.restservice.Dao.*;
import com.example.restservice.Service.GestDatosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
  
  




  // @GetMapping("/greeting")
  // public Usuario greeting(@RequestParam(defaultValue = "World") String name) {
  //   return new Usuario(counter.incrementAndGet(), template.formatted(name));
  // }
}