package com.example.restservice.UI;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import org.vaadin.stefan.fullcalendar.Entry;
import java.time.LocalDate;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import com.example.restservice.Service.GestDatosService;
import com.example.restservice.Entity.Tarea;
import java.util.List;

@PageTitle("Inicio")
@Route(value = "", layout = MainLayout.class) 
public class InicioView extends VerticalLayout {

    //AÑADIMOS EL SERVICIO AL CONSTRUCTOR
    public InicioView(GestDatosService gestDatosService) {
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setSizeFull(); 
        
        FullCalendar calendar = FullCalendarBuilder.create().build();
        
        calendar.setOption(FullCalendar.Option.LOCALE, Locale.of("es", "ES"));
        calendar.setOption("weekNumbers", false);
        calendar.setSizeFull(); 

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

        //CARGAR TAREAS REALES DE LA BASE DE DATOS ---
        List<Tarea> misTareas = gestDatosService.cargarTareas();
        
        for (Tarea t : misTareas) {
            Entry entry = new Entry();
            
            entry.setTitle(t.getTitulo()); 
            entry.setStart(t.getFechaInicio());
            entry.setEnd(t.getFechaFin());
            entry.setColor("#0078d7"); 
            if (t.getCategoria() != null && t.getCategoria().getColor() != null) {
                entry.setColor(t.getCategoria().getColor());
            } else {
                // Si no tiene categoría o la categoría no tiene color, le ponemos un color neutro
                entry.setColor("#bdc3c7"); 
            }
            calendar.getEntryProvider().asInMemory().addEntries(entry);
        }
        
        add(calendar);
    }
}