package com.example.restservice.Dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.Entity.Categoria;

/**
 * @brief Interfaz DAO para la gestión de categorías.
 * 
 * Proporciona operaciones de acceso a datos relacionadas
 * con la entidad Categoria.
 */
@Repository
public interface CategoriaDAO extends CrudRepository<Categoria, Long> {

    /**
     * @brief Obtiene las categorías asociadas a un usuario.
     * 
     * @param idUsuario identificador del usuario.
     * @return lista de categorías del usuario.
     */
    List<Categoria> findByUsuario_Id(Long idUsuario);
}