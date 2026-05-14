package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Tarea;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.restservice.Entity.Categoria;

/**
 * @brief Interfaz DAO para la gestión de tareas.
 * 
 * Proporciona operaciones de acceso a datos relacionadas
 * con la entidad Tarea.
 */
@Repository
public interface TareaDAO extends CrudRepository<Tarea, Long> {

    /**
     * @brief Obtiene las tareas asociadas a un usuario.
     * 
     * @param idUsuario identificador del usuario.
     * @return lista de tareas asociadas al usuario.
     */

    // Esto se pone para que se pueda buscar las tareas por el id del usuario, es decir, el propietario de las tareas
    List<Tarea> findByUsuarios_Id(Long idUsuario);

    /**
     * @brief Obtiene las tareas asociadas a una categoría.
     * 
     * @param categoria categoría utilizada como filtro.
     * @return lista de tareas asociadas a la categoría.
     */
    List<Tarea> findByCategoria(Categoria categoria);
    
}