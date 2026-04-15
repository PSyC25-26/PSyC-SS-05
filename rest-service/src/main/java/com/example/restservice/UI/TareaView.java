package com.example.restservice.UI;

import com.example.restservice.Entity.Tarea;
import com.example.restservice.Service.GestDatosService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Gestión de Tareas")
@Route(value = "tareas", layout = MainLayout.class)
public class TareaView extends VerticalLayout {

    private final GestDatosService service;
    private final Grid<Tarea> grid = new Grid<>(Tarea.class);

    // Componentes del apartado de AÑADIR
    private final TextField tituloField = new TextField("Título de la tarea");
    private final TextField descripcionField = new TextField("Descripción");
    private final DateTimePicker fechaInicioField = new DateTimePicker("Fecha y hora de Inicio");
    private final DateTimePicker fechaFinField = new DateTimePicker("Fecha y hora de Fin");
    private final Button guardarBtn = new Button("Añadir Tarea");

    // Componentes del apartado de EDITAR
    private final ComboBox<Tarea> selectorTareaEditar = new ComboBox<>("Selecciona la tarea a editar");
    private final TextField editTituloField = new TextField("Título");
    private final TextField editDescripcionField = new TextField("Descripción");
    private final DateTimePicker editFechaInicioField = new DateTimePicker("Inicio");
    private final DateTimePicker editFechaFinField = new DateTimePicker("Fin");
    private final Button guardarCambiosBtn = new Button("Guardar Cambios");

    // Componentes del apartado de ELIMINAR
    private final ComboBox<Tarea> selectorTareaEliminar = new ComboBox<>("Selecciona la tarea a eliminar");
    private final Button eliminarBtn = new Button("Eliminar Tarea");

    @Autowired
    public TareaView(GestDatosService service) {
        this.service = service;

        setSizeFull();
        setAlignItems(Alignment.CENTER); 

        // ==========================================
        // 1. CONFIGURAR EL APARTADO DE AÑADIR
        // ==========================================
        FormLayout formAñadir = new FormLayout(tituloField, descripcionField, fechaInicioField, fechaFinField, guardarBtn);
        formAñadir.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formAñadir.setMaxWidth("800px");
        guardarBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        formAñadir.setColspan(guardarBtn, 2); 
        guardarBtn.addClickListener(e -> guardarNuevaTarea());

        // ==========================================
        // 2. CONFIGURAR EL APARTADO DE EDITAR
        // ==========================================
        selectorTareaEditar.setItemLabelGenerator(Tarea::getTitulo);
        selectorTareaEditar.setWidth("400px");

        FormLayout formEditar = new FormLayout(editTituloField, editDescripcionField, editFechaInicioField, editFechaFinField);
        formEditar.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formEditar.setMaxWidth("800px");

        guardarCambiosBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY); // Botón verde
        
        // Desactivamos los campos hasta que se seleccione una tarea
        desactivarCamposEdicion();

        // Lógica: Cuando seleccionas una tarea, sus datos llenan el formulario
        selectorTareaEditar.addValueChangeListener(event -> {
            Tarea tareaSeleccionada = event.getValue();
            if (tareaSeleccionada != null) {
                editTituloField.setValue(tareaSeleccionada.getTitulo());
                editDescripcionField.setValue(tareaSeleccionada.getDescripcion());
                editFechaInicioField.setValue(tareaSeleccionada.getFechaInicio());
                editFechaFinField.setValue(tareaSeleccionada.getFechaFin());
                activarCamposEdicion();
            } else {
                desactivarCamposEdicion();
            }
        });

        guardarCambiosBtn.addClickListener(e -> guardarCambiosTarea());
        
        VerticalLayout layoutEditar = new VerticalLayout(selectorTareaEditar, formEditar, guardarCambiosBtn);
        layoutEditar.setPadding(false);
        layoutEditar.setAlignItems(Alignment.CENTER);

        // ==========================================
        // 3. CONFIGURAR EL APARTADO DE ELIMINAR
        // ==========================================
        selectorTareaEliminar.setItemLabelGenerator(Tarea::getTitulo);
        selectorTareaEliminar.setWidth("400px");

        eliminarBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        eliminarBtn.addClickListener(e -> eliminarTareaSeleccionada());

        HorizontalLayout layoutEliminar = new HorizontalLayout(selectorTareaEliminar, eliminarBtn);
        layoutEliminar.setDefaultVerticalComponentAlignment(Alignment.BASELINE); 

        // ==========================================
        // 4. CONFIGURAR LA TABLA (GRID)
        // ==========================================
        grid.setColumns("id", "titulo", "descripcion", "fechaInicio", "fechaFin"); 
        actualizarDatosPantalla(); 

        // ==========================================
        // 5. AÑADIR TODO A LA PANTALLA VISUAL
        // ==========================================
        add(
            new H2("Crear nueva tarea"), formAñadir, 
            new Hr(), 
            new H2("Editar tarea"), layoutEditar,
            new Hr(), 
            new H2("Eliminar tarea"), layoutEliminar, 
            new Hr(), 
            grid
        );
    }

    // --- MÉTODOS DE AÑADIR ---
    private void guardarNuevaTarea() {
        if (tituloField.isEmpty() || descripcionField.isEmpty() || fechaInicioField.isEmpty() || fechaFinField.isEmpty()) {
            Notification.show("Rellena todos los campos para crear.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setTitulo(tituloField.getValue());
        nuevaTarea.setDescripcion(descripcionField.getValue());
        nuevaTarea.setFechaInicio(fechaInicioField.getValue()); 
        nuevaTarea.setFechaFin(fechaFinField.getValue());       

        service.guardarTarea(nuevaTarea); 
        Notification.show("¡Tarea guardada!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        tituloField.clear(); descripcionField.clear(); fechaInicioField.clear(); fechaFinField.clear();
        actualizarDatosPantalla();
    }

    // --- MÉTODOS DE EDITAR ---
    private void guardarCambiosTarea() {
        Tarea tareaModificada = selectorTareaEditar.getValue();
        
        if (tareaModificada == null || editTituloField.isEmpty() || editDescripcionField.isEmpty() || editFechaInicioField.isEmpty() || editFechaFinField.isEmpty()) {
            Notification.show("Revisa que no haya campos vacíos al editar.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Actualizamos los valores del objeto existente
        tareaModificada.setTitulo(editTituloField.getValue());
        tareaModificada.setDescripcion(editDescripcionField.getValue());
        tareaModificada.setFechaInicio(editFechaInicioField.getValue());
        tareaModificada.setFechaFin(editFechaFinField.getValue());

        // Al guardar un objeto que ya tiene ID, Spring hace un UPDATE automáticamente
        service.guardarTarea(tareaModificada);
        
        Notification.show("¡Cambios guardados correctamente!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        
        selectorTareaEditar.clear();
        desactivarCamposEdicion();
        actualizarDatosPantalla();
    }

    private void activarCamposEdicion() {
        editTituloField.setEnabled(true);
        editDescripcionField.setEnabled(true);
        editFechaInicioField.setEnabled(true);
        editFechaFinField.setEnabled(true);
        guardarCambiosBtn.setEnabled(true);
    }

    private void desactivarCamposEdicion() {
        editTituloField.clear(); editDescripcionField.clear(); editFechaInicioField.clear(); editFechaFinField.clear();
        editTituloField.setEnabled(false);
        editDescripcionField.setEnabled(false);
        editFechaInicioField.setEnabled(false);
        editFechaFinField.setEnabled(false);
        guardarCambiosBtn.setEnabled(false);
    }

    // --- MÉTODOS DE ELIMINAR ---
    private void eliminarTareaSeleccionada() {
        Tarea tareaABorrar = selectorTareaEliminar.getValue();
        if (tareaABorrar == null) {
            Notification.show("Selecciona una tarea para eliminar.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        service.eliminarTarea(tareaABorrar);
        Notification.show("¡Tarea eliminada!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        selectorTareaEliminar.clear();
        actualizarDatosPantalla();
    }

    // --- MÉTODOS AUXILIARES ---
    private void actualizarDatosPantalla() {
        // Recargar los datos en todos los sitios donde se muestran
        grid.setItems(service.listarTodasLasTareas());
        selectorTareaEliminar.setItems(service.listarTodasLasTareas());
        selectorTareaEditar.setItems(service.listarTodasLasTareas());
    }
}