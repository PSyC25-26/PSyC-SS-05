package com.example.restservice.Dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.Entity.Categoria;

@Repository
public interface CategoriaDAO extends CrudRepository<Categoria, Long> {
    List<Categoria> findByUsuario_Id(Long idUsuario);
}
