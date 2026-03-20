package com.example.restservice.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; 


    // Cada usuario tiene un calendario, y cada calendario tiene un usuario
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "propietario_id", nullable = false, unique = true)
    private Usuario propietario;

    
    // getters y setters
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
    public Usuario getPropietario() {
        return propietario;
    }
    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

}