package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Calendario;


import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CalendarioDAO extends CrudRepository<Calendario, Long> {
    //Esto se pone para que se pueda buscar el calendario por el id del propietario, 
    // es decir, el usuario
    Calendario findByPropietarioId(Long idUsuario);
}
