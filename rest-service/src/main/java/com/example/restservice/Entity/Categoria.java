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

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = true)
    private String color; // Ej: "#FF0000" para tareas urgentes

    // // Relación opcional: Si quieres que las categorías sean propias de cada usuario
    // @ManyToOne
    // @JoinColumn(name = "usuario_id", nullable = false)
    // @JsonIgnore // Para evitar bucles infinitos al devolver JSON
    // private Usuario usuario;

    // Relación bidireccional (opcional): Para saber qué tareas tienen esta categoría
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Tarea> tareas;

    // Constructores vacíos y con parámetros
    public Categoria() {}

    public Categoria(String nombre, String color, Usuario usuario) {
        this.nombre = nombre;
        this.color = color;
        // this.usuario = usuario;
    }

    // Getters y Setters...
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    // public Usuario getUsuario() {
    //     return usuario;
    // }
    // public void setUsuario(Usuario usuario) {
    //     this.usuario = usuario;
    // }
}