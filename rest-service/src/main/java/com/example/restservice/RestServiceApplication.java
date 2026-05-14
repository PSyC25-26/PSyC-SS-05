package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @class RestServiceApplication
 * @brief Clase principal de la aplicación.
 * 
 * Inicializa y ejecuta la aplicación Spring Boot.
 */
@SpringBootApplication
public class RestServiceApplication {

	/**
	 * @brief Método principal de ejecución.
	 * 
	 * @param args argumentos de entrada de la aplicación.
	 */
	public static void main(String[] args) {

		SpringApplication.run(RestServiceApplication.class, args);

	}

}