package com.example.restservice.UI;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Inicio")
@Route(value = "", layout = MainLayout.class) // Ruta vacía para que sea la principal
public class InicioView extends VerticalLayout {

    public InicioView() {
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        add(
            new Paragraph("Aqui se visualizara el calendario")//ELIMINAR ESTO CUANDO SE META EL CALENDARIO
        );
    }
}