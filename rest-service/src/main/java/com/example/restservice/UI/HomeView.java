package com.example.restservice.UI; 

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

/**
 * @class HomeView
 * @brief Vista principal de la aplicación.
 * 
 * Permite acceder al inicio de sesión y al registro
 * de usuarios. Si existe una sesión iniciada,
 * muestra acceso al panel principal.
 */
@Route("") 
public class HomeView extends VerticalLayout {

    /**
     * @brief Constructor de la vista HomeView.
     */
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
                
            });

            botones.add(btnIrApp);

        } else {

            botones.add(btnLogin, btnRegistro);

        }

        add(titulo, botones);
    }
}