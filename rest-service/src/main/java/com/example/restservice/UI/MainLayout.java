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

        DrawerToggle toggleBtn = new DrawerToggle();
        toggleBtn.setId("menu-toggle-btn");

        HorizontalLayout header = new HorizontalLayout(toggleBtn, logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

        VerticalLayout menu = new VerticalLayout();

        // Todos los enlaces con sus IDs correctos para los tests
        menu.add(
            createMenuLink("Inicio",         "menu-link-inicio",         InicioView.class),
            createMenuLink("Tareas",         "menu-link-tareas",         TareaView.class),
            createMenuLink("Categorías",     "menu-link-categorias",     CategoriaView.class),  // <-- ID para el test
            createMenuLink("Configuración",  "menu-link-configuracion",  InicioView.class)
        );
        
        addToDrawer(menu);
    }

    private RouterLink createMenuLink(String viewName, String id, Class<? extends com.vaadin.flow.component.Component> viewClass) {
        RouterLink link = new RouterLink(viewName, viewClass);
        link.setId(id);
        return link;
    }
}