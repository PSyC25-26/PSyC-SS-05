package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Tarea;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaDAO extends CrudRepository<Tarea, Long> {
    List<Tarea> findByUsuario(Long idUsuario);
}
