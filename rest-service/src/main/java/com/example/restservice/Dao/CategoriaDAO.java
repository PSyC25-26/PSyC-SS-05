package com.example.restservice.Dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.restservice.Entity.Categoria;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDAO extends CrudRepository<Categoria, Long> {
}
