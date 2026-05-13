package com.example.restservice.Entity;


import java.time.LocalDateTime;


import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @brief Entidad que representa una tarea del sistema.
 * 
 * Una tarea contiene información relacionada con su título,
 * descripción, fechas, categoría y usuarios asociados.
 */
@Entity
public class Tarea {

    /**
     * @brief Identificador único de la tarea.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Título de la tarea.
     */
    @Column(nullable = false)
    private String titulo;

    /**
     * @brief Descripción detallada de la tarea.
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * @brief Fecha y hora de inicio de la tarea.
     */
    @Column (nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * @brief Fecha y hora de finalización de la tarea.
     */
    @Column (nullable = false)
    private LocalDateTime fechaFin;

    /**
     * @brief Categoría asociada a la tarea.
     */
    @ManyToOne
    // Se pone el nullable = false para que no se pueda crear una tarea sin una categoria asignada, es decir, que la categoria sea obligatoria.
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    /**
     * @brief Lista de usuarios asociados a la tarea.
     */
    @ManyToMany
    @JoinTable(
        // Esto se pone para que se pueda buscar los usuarios por el id de la tarea, 
        // es decir, los usuarios que tienen esa tarea asignada.
        //El inverse es para que se pueda buscar las tareas por el id del usuario, 
        // es decir, el propietario de las tareas
        name = "tarea_usuario",
        joinColumns = @JoinColumn(name = "tarea_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnoreProperties("tareas")
    private List<Usuario> usuarios;

    /**
     * @brief Constructor vacío requerido por JPA.
     */
    public Tarea() {}

    /**
     * @brief Constructor parametrizado de tarea.
     * 
     * @param id identificador de la tarea.
     * @param titulo título de la tarea.
     * @param descripcion descripción de la tarea.
     * @param categoria categoría asociada.
     * @param usuarios lista de usuarios asociados.
     * @param fechaInicio fecha de inicio.
     * @param fechaFin fecha de finalización.
     */
    public Tarea(Long id, String titulo, String descripcion, Categoria categoria, List<Usuario> usuarios, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuarios = usuarios;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters

    /**
     * @brief Obtiene el identificador de la tarea.
     * 
     * @return identificador de la tarea.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Establece el identificador de la tarea.
     * 
     * @param id identificador de la tarea.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Obtiene el título de la tarea.
     * 
     * @return título de la tarea.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @brief Establece el título de la tarea.
     * 
     * @param titulo título de la tarea.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @brief Obtiene la descripción de la tarea.
     * 
     * @return descripción de la tarea.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @brief Obtiene la fecha de inicio de la tarea.
     * 
     * @return fecha de inicio.
     */
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @brief Establece la fecha de inicio de la tarea.
     * 
     * @param fechaInicio fecha de inicio.
     */
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @brief Establece la descripción de la tarea.
     * 
     * @param descripcion descripción de la tarea.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @brief Obtiene la categoría asociada a la tarea.
     * 
     * @return categoría asociada.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @brief Establece la categoría de la tarea.
     * 
     * @param categoria categoría asociada.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @brief Obtiene los usuarios asociados a la tarea.
     * 
     * @return lista de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @brief Establece los usuarios asociados a la tarea.
     * 
     * @param usuarios lista de usuarios.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @brief Obtiene la fecha de finalización de la tarea.
     * 
     * @return fecha de finalización.
     */
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    /**
     * @brief Establece la fecha de finalización de la tarea.
     * 
     * @param fechaFin fecha de finalización.
     */
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    
}