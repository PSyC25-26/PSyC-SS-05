package com.example.restservice.UI;

import com.example.restservice.Entity.Usuario;
import com.example.restservice.Service.GestDatosService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * @class RegistroView
 * @brief Vista de registro de usuarios.
 * 
 * Permite crear nuevos usuarios dentro del sistema.
 */
@Route("registro")
public class RegistroView extends VerticalLayout {

    /**
     * @brief Constructor de la vista RegistroView.
     * 
     * @param gestDatosService servicio de gestión de datos.
     */
    public RegistroView(GestDatosService gestDatosService) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField usernameField = new TextField("Nombre de usuario");

        EmailField emailField = new EmailField("Correo electrónico");

        PasswordField passwordField = new PasswordField("Contraseña");

        Button btnRegistrar = new Button("Registrarse", e -> {
            
            // 1. COMPROBAR QUE NINGÚN CAMPO ESTÁ VACÍO
            if (usernameField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty()) {

                Notification.show("Por favor, rellena todos los campos.");

                return;
            }

            Usuario nuevoUsuario = new Usuario();

            nuevoUsuario.setUsername(usernameField.getValue());
            nuevoUsuario.setEmail(emailField.getValue());
            nuevoUsuario.setPassword(passwordField.getValue());
            nuevoUsuario.setTipoUsuario(Usuario.TipoUsuario.PARTICULAR);
            
            try {

                gestDatosService.guardarUsuario(nuevoUsuario);

                Notification.show("Usuario registrado correctamente");

                UI.getCurrent().navigate(LoginView.class);

            } catch (Exception ex) {

                // Si la BD sigue quejándose (ej: el email YA existe de verdad)
                Notification.show("Error: Ese nombre de usuario o email ya existe.");

                ex.printStackTrace();
            }
        });
        
        btnRegistrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnVolver = new Button("Volver al inicio", e -> UI.getCurrent().navigate(HomeView.class));

        add(usernameField, emailField, passwordField, btnRegistrar, btnVolver);
    }
}