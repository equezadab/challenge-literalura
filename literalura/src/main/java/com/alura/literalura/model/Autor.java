package com.alura.literalura.model;

import com.alura.literalura.dto.ResultadosLibrosDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int fechaNacimiento;
    private int fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> librosAutor;

    public Autor(){}

    public Autor(ResultadosLibrosDTO libroLista) {
        this.nombre = libroLista.resultados().stream().findFirst().get().autores().stream().findFirst().get().nombre();
        this.fechaNacimiento = libroLista.resultados().stream().findFirst().get().autores().stream().findFirst().get().fechaNacimiento();
        this.fechaFallecimiento = libroLista.resultados().stream().findFirst().get().autores().stream().findFirst().get().fechaFallecimiento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibrosAutor() {
        return librosAutor;
    }

    public void setLibrosAutor(List<Libro> librosAutor) {
        this.librosAutor = librosAutor;
    }

    @Override
    public String toString(){
        return "\n╔═══════════════════════════════════╗\n" +
                "║            ** AUTOR **            ║\n" +
                "╠═══════════════════════════════════╣\n" +
                "║ Nombre:        " + nombre + "\n" +
                "║ Fecha de Nac.: " + fechaNacimiento + "\n" +
                "║ ✝ Fecha de Muerte: " + fechaFallecimiento + "\n" +
                "║ Libros:        " + librosAutor.stream()
                        .map(libro -> libro.getTitulo())
                        .collect(Collectors.joining(", ")) + "\n" +
                "╠═══════════════════════════════════╣\n" +
                "╚═══════════════════════════════════╝\n";
    }
}
