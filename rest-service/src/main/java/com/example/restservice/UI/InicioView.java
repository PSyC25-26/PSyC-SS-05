package com.example.restservice.UI;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// La ruta "" indica que es la raíz (http://localhost:8080/)
// El layout = MainLayout.class incrusta esta vista en la estructura creada antes
@Route(value = "", layout = MainLayout.class)
@PageTitle("Inicio | Sistema de Calidad")
public class InicioView extends VerticalLayout {
}