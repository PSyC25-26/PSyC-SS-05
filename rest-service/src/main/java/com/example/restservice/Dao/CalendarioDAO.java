package com.example.restservice.Dao;
import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Calendario;


import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioDAO extends CrudRepository<Calendario, Long> {
    
}
