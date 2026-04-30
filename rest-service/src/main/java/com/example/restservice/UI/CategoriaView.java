package com.example.restservice.UI;

import com.example.restservice.Entity.Categoria;
import com.example.restservice.Service.GestDatosService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@PageTitle("Categorías")
@Route(value = "categorias", layout = MainLayout.class)
public class CategoriaView extends VerticalLayout {

    private final GestDatosService service;
    private final TextField nombreField = new TextField("Nombre de la categoría");
    private final Select<ColorPredefinido> colorSelector = new Select<>();

    private static class ColorPredefinido {
        String nombre;
        String hex;

        public ColorPredefinido(String nombre, String hex) {
            this.nombre = nombre;
            this.hex = hex;
        }
    }

    public CategoriaView(GestDatosService service) {
        this.service = service;
        setAlignItems(Alignment.CENTER);

        nombreField.setId("nombre-categoria");

        List<ColorPredefinido> paletaColores = Arrays.asList(
            // Vibrantes / Llamativos
            new ColorPredefinido("Rojo Fuego",          "#FF3333"),
            new ColorPredefinido("Naranja Neón",         "#FF9933"),
            new ColorPredefinido("Amarillo Eléctrico",   "#FFFF33"),
            new ColorPredefinido("Verde Lima",            "#33FF33"),
            new ColorPredefinido("Cian Brillante",        "#33FFFF"),
            new ColorPredefinido("Magenta",               "#FF33FF"),

            // Tonos Pastel
            new ColorPredefinido("Rosa Pastel",           "#FFB3BA"),
            new ColorPredefinido("Melocotón",             "#FFDFBA"),
            new ColorPredefinido("Amarillo Vainilla",     "#FFFFBA"),
            new ColorPredefinido("Verde Menta",           "#BAFFC9"),
            new ColorPredefinido("Azul Hielo",            "#BAE1FF"),
            new ColorPredefinido("Lavanda Suave",         "#D7BDE2"),

            // Tonos Profundos / Oscuros
            new ColorPredefinido("Rojo Vino",             "#800000"),
            new ColorPredefinido("Verde Bosque",          "#006400"),
            new ColorPredefinido("Azul Marino",           "#000080"),
            new ColorPredefinido("Berenjena",             "#4B0082"),
            new ColorPredefinido("Gris Antracita",        "#2F4F4F"),
            new ColorPredefinido("Marrón Chocolate",      "#8B4513"),

            // Colores Clásicos / Corporativos
            new ColorPredefinido("Azul Cobalto",          "#2E86C1"),
            new ColorPredefinido("Rojo Carmesí",          "#E74C3C"),
            new ColorPredefinido("Verde Esmeralda",       "#27AE60"),
            new ColorPredefinido("Naranja Zanahoria",     "#D35400"),
            new ColorPredefinido("Amatista",              "#9B59B6"),

            // Especiales / Tonos Tierra
            new ColorPredefinido("Ocre",                  "#CC7722"),
            new ColorPredefinido("Turquesa Oscuro",       "#008B8B"),
            new ColorPredefinido("Oro Viejo",             "#DAA520"),
            new ColorPredefinido("Verde Oliva",           "#808000"),
            new ColorPredefinido("Rosa Coral",            "#F08080")
        );

        colorSelector.setLabel("Color para el calendario");
        colorSelector.setItems(paletaColores);
        colorSelector.setValue(paletaColores.get(2));
        colorSelector.setId("color-categoria");

        colorSelector.setRenderer(new ComponentRenderer<>(color -> {
            Div colorBox = new Div();
            colorBox.setWidth("20px");
            colorBox.setHeight("20px");
            colorBox.getStyle().set("background-color", color.hex);
            colorBox.getStyle().set("border-radius", "4px");
            colorBox.getStyle().set("border", "1px solid #ccc");

            Span colorName = new Span(color.nombre);

            HorizontalLayout layout = new HorizontalLayout(colorBox, colorName);
            layout.setAlignItems(Alignment.CENTER);
            layout.setSpacing(true);
            return layout;
        }));

        // Botón de guardar — ID "btn-guardar-categoria" para que el test lo encuentre
        Button btnCrear = new Button("Añadir Categoría", e -> {
            if (nombreField.isEmpty()) {
                Notification.show("El nombre es obligatorio");
                return;
            }

            Categoria nueva = new Categoria();
            nueva.setNombre(nombreField.getValue());
            nueva.setColor(colorSelector.getValue().hex);

            service.guardarCategoria(nueva);
            Notification.show("¡Categoría '" + nueva.getNombre() + "' creada!");

            nombreField.clear();
            colorSelector.setValue(paletaColores.get(2));
        });
        btnCrear.setId("btn-guardar-categoria");  // <-- ID corregido para que coincida con el test

        add(new H2("Crear Nueva Categoría"), new FormLayout(nombreField, colorSelector, btnCrear));

    }
}