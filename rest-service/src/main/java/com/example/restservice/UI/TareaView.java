package com.example.restservice.UI;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.restservice.Entity.Categoria;
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
import com.vaadin.flow.server.VaadinSession;

/**
 * @class TareaView
 * @brief Vista de gestión de tareas.
 * 
 * Permite crear, modificar y eliminar tareas
 * asociadas al usuario autenticado.
 */
@PageTitle("Gestión de Tareas")
@Route(value = "tareas", layout = MainLayout.class)
public class TareaView extends VerticalLayout {

    /**
     * Servicio de gestión de datos.
     */
    private final GestDatosService service;

    /**
     * Tabla utilizada para mostrar las tareas.
     */
    private final Grid<Tarea> grid = new Grid<>(Tarea.class);

    // Componentes del apartado de AÑADIR
    private final TextField tituloField = new TextField("Título");
    private final TextField descripcionField = new TextField("Descripción");
    private final DateTimePicker fechaInicioField = new DateTimePicker("Fecha y hora de Inicio");
    private final DateTimePicker fechaFinField = new DateTimePicker("Fecha y hora de Fin");
    private final Button guardarBtn = new Button("Añadir Tarea");
    private final ComboBox<Categoria> categoriaCombo = new ComboBox<>("Categoría");

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

    /**
     * @brief Constructor de la vista TareaView.
     * 
     * @param service servicio de gestión de datos.
     */
    @Autowired
    public TareaView(GestDatosService service) {

        this.service = service;

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        // ==========================================
        // ASIGNACIÓN DE IDs PARA LOS TESTS
        // ==========================================

        // IDs - Sección Añadir
        tituloField.setId("titulo-tarea");
        descripcionField.setId("desc-tarea");
        fechaInicioField.setId("fecha-inicio-tarea");
        fechaFinField.setId("fecha-fin-tarea");
        categoriaCombo.setId("categoria-tarea");
        guardarBtn.setId("btn-guardar-tarea");

        // IDs - Sección Editar
        selectorTareaEditar.setId("selector-editar-tarea");
        editTituloField.setId("edit-titulo-tarea");
        editDescripcionField.setId("edit-desc-tarea");
        editFechaInicioField.setId("edit-fecha-inicio-tarea");
        editFechaFinField.setId("edit-fecha-fin-tarea");
        guardarCambiosBtn.setId("btn-guardar-cambios-tarea");

        // IDs - Sección Eliminar
        selectorTareaEliminar.setId("selector-eliminar-tarea");
        eliminarBtn.setId("btn-eliminar-tarea");

        // ID - Tabla/Grid
        grid.setId("tabla-tareas");

        // ==========================================
        // 1. CONFIGURAR EL APARTADO DE AÑADIR
        // ==========================================

        FormLayout formAñadir = new FormLayout();

        formAñadir.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formAñadir.setMaxWidth("800px");

        Long usuarioId = (Long) com.vaadin.flow.server.VaadinSession.getCurrent().getAttribute("usuarioId");

        if (usuarioId != null) {
            categoriaCombo.setItems(service.obtenerCategoriasPorUsuario(usuarioId));
        }
        
        categoriaCombo.setItemLabelGenerator(Categoria::getNombre);
        categoriaCombo.setRequired(true);

        formAñadir.add(tituloField, descripcionField, fechaInicioField, fechaFinField, categoriaCombo, guardarBtn);

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

        guardarCambiosBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);

        desactivarCamposEdicion();

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

    /**
     * @brief Guarda una nueva tarea.
     */
    private void guardarNuevaTarea() {

        if (tituloField.isEmpty() || categoriaCombo.isEmpty() || descripcionField.isEmpty() || fechaInicioField.isEmpty() || fechaFinField.isEmpty()) {

            Notification.show("Rellena todos los campos para crear.")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);

            return;
        }

        LocalDateTime nuevaInicio = fechaInicioField.getValue();
        LocalDateTime nuevaFin = fechaFinField.getValue();

        if (nuevaInicio.isAfter(nuevaFin) || nuevaInicio.isEqual(nuevaFin)) {

            Notification.show("La fecha de inicio debe ser anterior a la de fin.")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);

            return;
        }

        boolean haySolapamiento = false;

        for (Tarea t : service.cargarTareas()) {

            if (nuevaInicio.isBefore(t.getFechaFin()) && nuevaFin.isAfter(t.getFechaInicio())) {

                haySolapamiento = true;
                break;
            }
        }

        if (haySolapamiento) {

            Notification.show("Error: Ya existe una tarea en este horario.")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);

            return;
        }

        Tarea nuevaTarea = new Tarea();

        nuevaTarea.setTitulo(tituloField.getValue());
        nuevaTarea.setDescripcion(descripcionField.getValue());
        nuevaTarea.setFechaInicio(nuevaInicio);
        nuevaTarea.setFechaFin(nuevaFin);
        nuevaTarea.setCategoria(categoriaCombo.getValue());

        Long usuarioId = (Long) VaadinSession.getCurrent().getAttribute("usuarioId");
        
        if (usuarioId != null) {

            service.guardarTarea(nuevaTarea, java.util.List.of(usuarioId));
            
            Notification.show("¡Tarea guardada!")
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            tituloField.clear();
            descripcionField.clear();
            fechaInicioField.clear();
            fechaFinField.clear();

            actualizarDatosPantalla();

        } else {

            Notification.show("Error: Sesión no válida")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * @brief Guarda los cambios realizados sobre una tarea.
     */
    private void guardarCambiosTarea() {

        Tarea tareaModificada = selectorTareaEditar.getValue();

        if (tareaModificada == null || editTituloField.isEmpty() || editDescripcionField.isEmpty() || editFechaInicioField.isEmpty() || editFechaFinField.isEmpty()) {

            Notification.show("Revisa que no haya campos vacíos al editar.")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);

            return;
        }

        tareaModificada.setTitulo(editTituloField.getValue());
        tareaModificada.setDescripcion(editDescripcionField.getValue());
        tareaModificada.setFechaInicio(editFechaInicioField.getValue());
        tareaModificada.setFechaFin(editFechaFinField.getValue());

        service.guardarTarea(tareaModificada);

        Notification.show("¡Cambios guardados correctamente!")
            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        selectorTareaEditar.clear();

        desactivarCamposEdicion();

        actualizarDatosPantalla();
    }

    /**
     * @brief Activa los campos de edición.
     */
    private void activarCamposEdicion() {

        editTituloField.setEnabled(true);
        editDescripcionField.setEnabled(true);
        editFechaInicioField.setEnabled(true);
        editFechaFinField.setEnabled(true);
        guardarCambiosBtn.setEnabled(true);
    }

    /**
     * @brief Desactiva los campos de edición.
     */
    private void desactivarCamposEdicion() {

        editTituloField.clear();
        editDescripcionField.clear();
        editFechaInicioField.clear();
        editFechaFinField.clear();

        editTituloField.setEnabled(false);
        editDescripcionField.setEnabled(false);
        editFechaInicioField.setEnabled(false);
        editFechaFinField.setEnabled(false);

        guardarCambiosBtn.setEnabled(false);
    }

    /**
     * @brief Elimina la tarea seleccionada.
     */
    private void eliminarTareaSeleccionada() {

        Tarea tareaABorrar = selectorTareaEliminar.getValue();

        if (tareaABorrar == null) {

            Notification.show("Selecciona una tarea para eliminar.")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);

            return;
        }

        service.eliminarTarea(tareaABorrar);

        Notification.show("¡Tarea eliminada!")
            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        selectorTareaEliminar.clear();

        actualizarDatosPantalla();
    }

    /**
     * @brief Actualiza la información mostrada en pantalla.
     */
    private void actualizarDatosPantalla() {

            // 1. Recuperamos quién es el usuario logueado
            Long usuarioId = (Long) VaadinSession.getCurrent().getAttribute("usuarioId");
            
            if (usuarioId != null) {

                // 2. Pedimos SOLO las tareas de este usuario
                List<Tarea> misTareas = service.obtenerTareasPorUsuario(usuarioId);
                
                grid.setItems(misTareas);

                selectorTareaEliminar.setItems(misTareas);

                selectorTareaEditar.setItems(misTareas);
            }
        }
}