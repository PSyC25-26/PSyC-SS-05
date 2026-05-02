package com.example.restservice.UI;

import com.example.restservice.Entity.Usuario;
import com.example.restservice.Service.GestDatosService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView(GestDatosService gestDatosService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);

        loginForm.addLoginListener(e -> {
            Usuario usuario = gestDatosService.autenticarUsuario(e.getUsername(), e.getPassword());
            
            if (usuario != null) {
                VaadinSession.getCurrent().setAttribute("usuarioId", usuario.getId());
                // Navegamos directamente a InicioView
                UI.getCurrent().navigate(InicioView.class); 
            } else {
                loginForm.setError(true);
            }
        });

        Button btnVolver = new Button("Volver al inicio", e -> UI.getCurrent().navigate(HomeView.class));

        add(loginForm, btnVolver);
    }
}