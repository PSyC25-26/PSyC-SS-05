package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Usuario;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @brief Interfaz DAO para la gestión de usuarios.
 * 
 * Proporciona operaciones de acceso a datos relacionadas
 * con la entidad Usuario.
 */
@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

    /**
     * @brief Obtiene los usuarios asociados a una tarea.
     * 
     * @param idTarea identificador de la tarea.
     * @return lista de usuarios asociados a la tarea.
     */

    //Esto se pone para que se pueda buscar los usuarios por el id de la tarea, 
    // es decir, los usuarios que tienen esa tarea asignada
    List<Usuario> findByTareas_Id(Long idTarea);
}