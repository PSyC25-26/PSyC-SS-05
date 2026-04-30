package com.example.restservice.UI;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import org.vaadin.stefan.fullcalendar.Entry;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import com.example.restservice.Service.GestDatosService;
import com.example.restservice.Entity.Tarea;
import com.example.restservice.Entity.Categoria;
import java.util.List;

@PageTitle("Inicio")
@Route(value = "", layout = MainLayout.class) 
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
        filtroCategoriaCombo.setItems(gestDatosService.cargarCategorias());
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

        List<Tarea> misTareas;
        if (categoriaFiltro == null) {
            misTareas = gestDatosService.cargarTareas(); 
        } else {
            misTareas = gestDatosService.obtenerTareasPorCategoria(categoriaFiltro); 
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