package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Tarea;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.restservice.Entity.Categoria;
@Repository
public interface TareaDAO extends CrudRepository<Tarea, Long> {
    // Esto se pone para que se pueda buscar las tareas por el id del usuario, es decir, el propietario de las tareas
    List<Tarea> findByUsuarios_Id(Long idUsuario);

    List<Tarea> findByCategoria(Categoria categoria);
    
}
    
