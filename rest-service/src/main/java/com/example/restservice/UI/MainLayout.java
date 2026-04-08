package com.example.restservice.UI;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        crearCabecera();
        crearMenuLateral();
    }

    private void crearCabecera() {
        H1 logo = new H1("PSyC-SS-05");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)")
                       .set("margin", "0");

        HorizontalLayout cabecera = new HorizontalLayout(new DrawerToggle(), logo);
        cabecera.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        cabecera.setWidthFull();
        cabecera.addClassNames("py-0", "px-m");

        addToNavbar(cabecera);
    }

    private void crearMenuLateral() {
        // Enlace a la vista de inicio
        RouterLink inicioLink = new RouterLink("Inicio", InicioView.class);
        
        VerticalLayout menu = new VerticalLayout(inicioLink);
        addToDrawer(menu);
    }
}