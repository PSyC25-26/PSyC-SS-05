package com.example.restservice.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @brief Entidad que representa un usuario del sistema.
 * 
 * La clase Usuario almacena la información relacionada
 * con los usuarios registrados en la aplicación,
 * incluyendo credenciales, calendario y tareas asociadas.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * @brief Enumeración de tipos de usuario.
     */
    public enum TipoUsuario {
        PARTICULAR,
        EMPRESA
    }

    /**
     * @brief Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Nombre de usuario utilizado para autenticación.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * @brief Dirección de correo electrónico del usuario.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * @brief Contraseña del usuario.
     */
    @Column(nullable = false)
    private String password;

    /**
     * @brief Calendario asociado al usuario.
     */
    @OneToOne(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Calendario calendario;

    /**
     * @brief Lista de tareas asociadas al usuario.
     */
    @ManyToMany(mappedBy = "usuarios")
    @JsonIgnoreProperties("usuarios")
    private List<Tarea> tareas;

    /**
     * @brief Tipo de usuario registrado.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    /**
     * @brief Constructor vacío requerido por JPA.
     */
    public Usuario() {}

    /**
     * @brief Constructor parametrizado de usuario.
     * 
     * @param id identificador del usuario.
     * @param username nombre de usuario.
     * @param email correo electrónico.
     * @param tipoUsuario tipo de usuario.
     * @param tareas lista de tareas asociadas.
     */
    public Usuario(Long id, String username, String email, TipoUsuario tipoUsuario, List<Tarea> tareas) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.tareas = tareas;
    }

    /**
     * @brief Obtiene el identificador del usuario.
     * 
     * @return identificador del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Establece el identificador del usuario.
     * 
     * @param id identificador del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Obtiene el nombre de usuario.
     * 
     * @return nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @brief Establece el nombre de usuario.
     * 
     * @param username nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @brief Obtiene el correo electrónico.
     * 
     * @return correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Obtiene las tareas asociadas al usuario.
     * 
     * @return lista de tareas.
     */
    public List<Tarea> getTareas() {
        return tareas;
    }

    /**
     * @brief Establece el correo electrónico.
     * 
     * @param email correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @brief Obtiene el tipo de usuario.
     * 
     * @return tipo de usuario.
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @brief Establece el tipo de usuario.
     * 
     * @param tipoUsuario tipo de usuario.
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * @brief Establece las tareas asociadas al usuario.
     * 
     * @param tareas lista de tareas.
     */
    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    /**
     * @brief Obtiene el calendario del usuario.
     * 
     * @return calendario asociado.
     */
    public Calendario getCalendario() {
        return calendario;
    }

    /**
     * @brief Establece el calendario del usuario.
     * 
     * @param calendario calendario asociado.
     */
    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    /**
     * @brief Obtiene la contraseña del usuario.
     * 
     * @return contraseña almacenada.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @brief Establece la contraseña del usuario.
     * 
     * @param password contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}