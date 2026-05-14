package com.example.restservice.Cliente;

import java.util.List;

import com.example.restservice.Entity.Usuario;
import org.springframework.web.client.RestTemplate;

/**
 * @brief Cliente REST para acceder a los servicios
 * proporcionados por la aplicación.
 * 
 * Esta clase permite consumir los endpoints del controlador
 * mediante peticiones HTTP utilizando RestTemplate.
 */
public class GestDatosCliente {

    /**
     * Objeto utilizado para realizar peticiones HTTP.
     */
    private final RestTemplate restTemplate;

    /**
     * URL base del servicio REST.
     */
    private final String baseUrl;

    /**
     * @brief Constructor de la clase GestDatosCliente.
     * 
     * Inicializa el cliente REST con la URL base del servidor.
     * 
     * @param baseUrl dirección base de la API REST.
     */
    public GestDatosCliente(String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    /**
     * @brief Obtiene la lista de usuarios registrados.
     * 
     * Realiza una petición GET al endpoint correspondiente
     * para recuperar todos los usuarios almacenados.
     * 
     * @return lista de usuarios.
     */
    public List<Usuario> obtenerUsuarios() {
        return restTemplate.getForObject(
                baseUrl + "/gestDatos/obtenerUsuarios",
                List.class
        );
    }
}