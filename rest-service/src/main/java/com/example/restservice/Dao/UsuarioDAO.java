package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Usuario;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
    //Esto se pone para que se pueda buscar los usuarios por el id de la tarea, 
    // es decir, los usuarios que tienen esa tarea asignada
    List<Usuario> findByTareas_Id(Long idTarea);
}
