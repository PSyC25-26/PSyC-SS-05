package com.example.restservice.Dto;

public class CategoriaDTO {

    private String nombre;
    private String color;

    // Constructor vacío
    public CategoriaDTO() {}

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}