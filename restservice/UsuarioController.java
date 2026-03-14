package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  // @GetMapping("/greeting")
  // public Usuario greeting(@RequestParam(defaultValue = "World") String name) {
  //   return new Usuario(counter.incrementAndGet(), template.formatted(name));
  // }
}