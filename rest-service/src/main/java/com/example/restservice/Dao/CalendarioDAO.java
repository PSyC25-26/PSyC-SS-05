package com.example.restservice.Dao;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Calendario;

import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @brief Interfaz DAO para la gestión de calendarios.
 * 
 * Proporciona operaciones de acceso a datos relacionadas
 * con la entidad Calendario.
 */
@Repository
public interface CalendarioDAO extends CrudRepository<Calendario, Long> {

    /**
     * @brief Busca un calendario a partir del identificador
     * del usuario propietario.
     * 
     * @param idUsuario identificador del usuario propietario.
     * @return calendario asociado al usuario.
     */

    //Esto se pone para que se pueda buscar el calendario por el id del propietario, 
    // es decir, el usuario
    Calendario findByPropietarioId(Long idUsuario);
}