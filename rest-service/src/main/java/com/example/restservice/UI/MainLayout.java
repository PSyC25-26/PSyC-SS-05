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
        H1 logo = new H1("PSyC Quality Manager");
        logo.getStyle()
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

        //Sidebar con elementos (vacios de momento)
        VerticalLayout menu = new VerticalLayout();
        
        menu.add( //todas llevan a inicio de momento
            createMenuLink("Inicio", InicioView.class),
            createMenuLink("Tareas", TareaView.class),
            createMenuLink("Categorías", CategoriaView.class),
            createMenuLink("Configuración", InicioView.class)
        );

        addToDrawer(menu);
    }

    //para crear los elementos del sidebar
    private RouterLink createMenuLink(String viewName, Class<? extends com.vaadin.flow.component.Component> viewClass) {
        return new RouterLink(viewName, viewClass);
    }
}