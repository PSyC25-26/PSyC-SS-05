package com.example.restservice.UI;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 logo = new H1("PSyC Quality Manager");
        logo.getStyle()
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "0");

        DrawerToggle toggleBtn = new DrawerToggle();
        toggleBtn.setId("menu-toggle-btn");

        // --- NUEVO: Botón de Cerrar Sesión ---
        Button btnLogout = new Button("Cerrar Sesión", e -> {
            // 1. Limpiamos el ID del usuario de la sesión
            VaadinSession.getCurrent().setAttribute("usuarioId", null);
            
            // 2. Cerramos la sesión de Vaadin por completo
            VaadinSession.getCurrent().close();
            
            // 3. Redirigimos explícitamente a la ruta del login (con la barra por delante)
            UI.getCurrent().getPage().setLocation("/login"); 
        });
        btnLogout.addThemeVariants(ButtonVariant.LUMO_ERROR); // Le damos color rojo para que destaque
        btnLogout.getStyle().set("margin-left", "auto"); // Empuja el botón a la derecha del todo

        // Añadimos el botón al header
        HorizontalLayout header = new HorizontalLayout(toggleBtn, logo, btnLogout);
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