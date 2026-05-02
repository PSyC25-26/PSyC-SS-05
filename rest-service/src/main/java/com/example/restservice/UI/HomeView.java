package com.example.restservice.UI; // Cambia esto por tu paquete real

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("") 
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 titulo = new H1("Bienvenido a la Aplicación");

        HorizontalLayout botones = new HorizontalLayout();

        // Botón para ir al Login
        Button btnLogin = new Button("Iniciar Sesión", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Botón para ir al Registro
        Button btnRegistro = new Button("Registrar Usuario", e -> {
            UI.getCurrent().navigate(RegistroView.class);
        });

        // Verificación simple: si ya hay una sesión, mostrar un botón para ir a tu app
        if (VaadinSession.getCurrent().getAttribute("usuarioId") != null) {
            Button btnIrApp = new Button("Ir a mi panel", e -> {
                // AQUÍ: Pon el nombre de la clase de TU vista principal
                // UI.getCurrent().navigate(TuClasePrincipal.class);
            });
            botones.add(btnIrApp);
        } else {
            botones.add(btnLogin, btnRegistro);
        }

        add(titulo, botones);
    }
}