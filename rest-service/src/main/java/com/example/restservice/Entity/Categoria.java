package com.example.restservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = true)
    private String color; // Ej: "#FF0000" para tareas urgentes

    @Column(nullable = true)
    private String icono;

    // Relación opcional: Si quieres que las categorías sean propias de cada usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    @JsonIgnore // Para evitar bucles infinitos al devolver JSON
    private Usuario usuario;

    // Relación bidireccional (opcional): Para saber qué tareas tienen esta categoría
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Tarea> tareas;

    // Constructores vacíos y con parámetros
    public Categoria() {}

    public Categoria(String nombre, String color, String icono) {
        this.nombre = nombre;
        this.color = color;
        this.icono = icono;
    }

    // Getters y Setters...
    // ...
}