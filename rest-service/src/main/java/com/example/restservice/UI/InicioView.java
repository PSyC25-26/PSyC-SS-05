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

@PageTitle("Inicio")
@Route(value = "", layout = MainLayout.class) 
public class InicioView extends VerticalLayout {

    public InicioView() {
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setSizeFull(); 
        
        FullCalendar calendar = FullCalendarBuilder.create().build();
        
        // ¡Aquí está la corrección! Option está dentro de FullCalendar
        calendar.setOption(FullCalendar.Option.LOCALE, Locale.of("es", "ES"));
        calendar.setOption("weekNumbers", false);
        calendar.setSizeFull(); 
        Map<String, Object> header = new HashMap<>();
        // Lado izquierdo: botones para ir atrás, adelante y volver a 'hoy'
        header.put("left", "prev,next today");
        // Centro: El título que muestra el mes/semana actual
        header.put("center", "title");
        // Lado derecho: botones para cambiar de vista (Mes, Semana, Día)
        header.put("right", "timeGridDay,timeGridWeek,dayGridMonth");
        
        calendar.setOption("headerToolbar", header);

        Map<String, Object> timeFormat = new HashMap<>();
        timeFormat.put("hour", "numeric");
        timeFormat.put("minute", "2-digit");
        timeFormat.put("hour12", false); // Esto fuerza el modo 24h en lugar de AM/PM

        // Aplicamos el formato al eje Y (las horas de la izquierda)
        calendar.setOption("slotLabelFormat", timeFormat);
        // Aplicamos el formato al texto que sale dentro del evento
        calendar.setOption("eventTimeFormat", timeFormat);
        
        Entry entry = new Entry();
        entry.setTitle("Auditoría de Calidad");
        entry.setStart(LocalDate.now().atTime(10, 0));
        entry.setEnd(LocalDate.now().atTime(12, 0));
        entry.setColor("#0078d7"); 
        
        calendar.getEntryProvider().asInMemory().addEntries(entry);
        
        add(calendar);
    }
}