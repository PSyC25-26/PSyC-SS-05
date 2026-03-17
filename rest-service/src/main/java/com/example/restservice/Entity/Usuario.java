package com.example.restservice.Entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    public enum TipoUsuario {
        PARTICULAR,
        EMPRESA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "usuarios")
    private List<Tarea> tareas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    public Usuario() {}

    public Usuario(Long id, String username, String email, TipoUsuario tipoUsuario, List<Tarea> tareas) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.tareas = tareas;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}