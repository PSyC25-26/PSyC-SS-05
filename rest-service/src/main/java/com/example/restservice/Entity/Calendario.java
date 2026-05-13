package com.example.restservice.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * @brief Entidad que representa un calendario del sistema.
 * 
 * Cada calendario pertenece a un único usuario propietario
 * y almacena la información relacionada con la organización
 * de tareas.
 */
@Entity
public class Calendario {

    /**
     * @brief Identificador único del calendario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Nombre del calendario.
     */
    private String nombre; 


    /**
     * @brief Usuario propietario del calendario.
     * 
     * Cada usuario dispone de un único calendario asociado.
     */
    // Cada usuario tiene un calendario, y cada calendario tiene un usuario
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "propietario_id", nullable = false, unique = true)
    private Usuario propietario;

    
    // getters y setters

    /**
     * @brief Obtiene el identificador del calendario.
     * 
     * @return identificador del calendario.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Establece el identificador del calendario.
     * 
     * @param id identificador del calendario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Obtiene el nombre del calendario.
     * 
     * @return nombre del calendario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @brief Establece el nombre del calendario.
     * 
     * @param nombre nombre del calendario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @brief Obtiene el usuario propietario del calendario.
     * 
     * @return usuario propietario.
     */
    public Usuario getPropietario() {
        return propietario;
    }

    /**
     * @brief Establece el usuario propietario del calendario.
     * 
     * @param propietario usuario propietario.
     */
    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

}