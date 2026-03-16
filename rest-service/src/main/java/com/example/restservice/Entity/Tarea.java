package com.example.restservice.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column (nullable = false)
    private LocalDateTime fechaInicio;

    @Column (nullable = false)
    private LocalDateTime fechaFin;

    // Esto se hace porque una tarea tiene una categoría 
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    //Esto se hace porque una tarea pueden tenerla varios usuarios, y un usuario puede tener varias tareas
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private List<Usuario> usuarios;

    public Tarea() {}

    public Tarea(Long id, String titulo, String descripcion, Categoria categoria, List<Usuario> usuarios) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuarios = usuarios;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
