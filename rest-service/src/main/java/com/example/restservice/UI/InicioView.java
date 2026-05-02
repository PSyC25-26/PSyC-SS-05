package com.example.restservice.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.example.restservice.Entity.Categoria;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Service.GestDatosService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Inicio")
@Route(value = "inicio", layout = MainLayout.class) 
public class InicioView extends VerticalLayout {

    private final FullCalendar calendar;
    private final GestDatosService gestDatosService;

    public InicioView(GestDatosService gestDatosService) {
        this.gestDatosService = gestDatosService;
        
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setSizeFull(); 
        
        // 1. CONFIGURAR EL FILTRO DE CATEGORÍAS
        ComboBox<Categoria> filtroCategoriaCombo = new ComboBox<>("Filtrar por categoría");
        Long usuarioId = (Long) com.vaadin.flow.server.VaadinSession.getCurrent().getAttribute("usuarioId");
        if (usuarioId != null) {
            filtroCategoriaCombo.setItems(gestDatosService.obtenerCategoriasPorUsuario(usuarioId));
        }
        filtroCategoriaCombo.setItemLabelGenerator(Categoria::getNombre);
        filtroCategoriaCombo.setClearButtonVisible(true);
        filtroCategoriaCombo.setWidth("300px");
        
        // ASIGNAMOS ID PARA TESTS
        filtroCategoriaCombo.setId("filtro-categoria"); //

        // 2. CONFIGURAR EL CALENDARIO
        calendar = FullCalendarBuilder.create().build();
        calendar.setOption(FullCalendar.Option.LOCALE, Locale.of("es", "ES"));
        calendar.setOption("weekNumbers", false);
        calendar.setSizeFull(); 
        
        // ASIGNAMOS ID PARA TESTS
        calendar.setId("calendario-principal"); //

        Map<String, Object> header = new HashMap<>();
        header.put("left", "prev,next today");
        header.put("center", "title");
        header.put("right", "dayGridMonth,timeGridWeek,timeGridDay");
        calendar.setOption("headerToolbar", header);

        Map<String, Object> timeFormat = new HashMap<>();
        timeFormat.put("hour", "numeric");
        timeFormat.put("minute", "2-digit");
        timeFormat.put("hour12", false);
        calendar.setOption("slotLabelFormat", timeFormat);
        calendar.setOption("eventTimeFormat", timeFormat);

        filtroCategoriaCombo.addValueChangeListener(event -> {
            Categoria categoriaSeleccionada = event.getValue();
            actualizarCalendario(categoriaSeleccionada);
        });

        actualizarCalendario(null);
        
        add(filtroCategoriaCombo, calendar);
    }

    private void actualizarCalendario(Categoria categoriaFiltro) {
            calendar.getEntryProvider().asInMemory().removeAllEntries();
            
            // 1. Recuperamos el usuario logueado
            Long usuarioId = (Long) VaadinSession.getCurrent().getAttribute("usuarioId");
            if (usuarioId == null) return; // Por seguridad, si no hay sesión no hace nada
    
            // 2. Obtenemos SOLO las tareas de este usuario
            List<Tarea> misTareas = gestDatosService.obtenerTareasPorUsuario(usuarioId);
    
            // 3. Si además ha elegido filtrar por una categoría, filtramos la lista
            if (categoriaFiltro != null) {
                misTareas = misTareas.stream()
                    .filter(t -> t.getCategoria() != null && t.getCategoria().getId().equals(categoriaFiltro.getId()))
                    .toList(); // En Java 16+ se usa toList() o collect(Collectors.toList())
            }
            
            for (Tarea t : misTareas) {
                Entry entry = new Entry();
                entry.setTitle(t.getTitulo());
                entry.setStart(t.getFechaInicio());
                entry.setEnd(t.getFechaFin());
                
                if (t.getCategoria() != null && t.getCategoria().getColor() != null) {
                    entry.setColor(t.getCategoria().getColor());
                } else {
                    entry.setColor("#bdc3c7");
                }
                calendar.getEntryProvider().asInMemory().addEntries(entry);
            }
            calendar.getEntryProvider().refreshAll();
        }
}