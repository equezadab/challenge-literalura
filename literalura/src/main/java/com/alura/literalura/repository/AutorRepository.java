package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.librosAutor")
    List<Autor> findAllConLibros();

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.librosAutor " +
            "WHERE (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :fecha) " +
            "AND a.fechaNacimiento <= :fecha")
    List<Autor> findAllConLibrosVivos(@Param("fecha") int fecha);

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    Optional<Autor> findByNombre(String nombre);
}
