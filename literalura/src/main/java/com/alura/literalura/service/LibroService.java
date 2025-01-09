package com.alura.literalura.service;

import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    public Libro agregarLibro(Libro libro){
        return libroRepository.save(libro);
    }

    public Optional<Libro> listarLibroTitulo(String titulo){
        return libroRepository.findByTituloIgnoreCase(titulo);
    }

    public Optional<Libro> listarLibroId(Long id){
        return libroRepository.findById(id);
    }

}
