package com.example.restservice.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @brief Entidad que representa una categoría de tareas.
 * 
 * Las categorías permiten clasificar las tareas del sistema
 * y pueden estar asociadas a un usuario concreto.
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    /**
     * @brief Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Nombre de la categoría.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * @brief Color asociado a la categoría.
     * 
     * Se utiliza para identificar visualmente las tareas.
     */
    @Column(nullable = true)
    private String color; // Ej: "#FF0000" para tareas urgentes

    /**
     * @brief Usuario propietario de la categoría.
     */
    // // Relación opcional: Si quieres que las categorías sean propias de cada usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Para evitar bucles infinitos al devolver JSON
    private Usuario usuario;

    /**
     * @brief Lista de tareas asociadas a la categoría.
     */
    // Relación bidireccional (opcional): Para saber qué tareas tienen esta categoría
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Tarea> tareas;

    /**
     * @brief Constructor vacío requerido por JPA.
     */
    // Constructores vacíos y con parámetros
    public Categoria() {}

    /**
     * @brief Constructor parametrizado de categoría.
     * 
     * @param nombre nombre de la categoría.
     * @param color color asociado.
     * @param usuario usuario propietario.
     */
    public Categoria(String nombre, String color, Usuario usuario) {
        this.nombre = nombre;
        this.color = color;
        // this.usuario = usuario;
    }

    // Getters y Setters...

    /**
     * @brief Obtiene el identificador de la categoría.
     * 
     * @return identificador de la categoría.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Establece el identificador de la categoría.
     * 
     * @param id identificador de la categoría.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Obtiene el nombre de la categoría.
     * 
     * @return nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @brief Establece el nombre de la categoría.
     * 
     * @param nombre nombre de la categoría.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @brief Obtiene el color asociado a la categoría.
     * 
     * @return color de la categoría.
     */
    public String getColor() {
        return color;
    }

    /**
     * @brief Establece el color asociado a la categoría.
     * 
     * @param color color de la categoría.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @brief Obtiene el usuario propietario de la categoría.
     * 
     * @return usuario propietario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @brief Establece el usuario propietario de la categoría.
     * 
     * @param usuario usuario propietario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}