package com.example.restservice.Cliente;

import java.util.List;

import com.example.restservice.Entity.Usuario;
import org.springframework.web.client.RestTemplate;

public class GestDatosCliente {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GestDatosCliente(String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public List<Usuario> obtenerUsuarios() {
        return restTemplate.getForObject(
                baseUrl + "/gestDatos/obtenerUsuarios",
                List.class
        );
    }
}