package com.alura.literalura.model;

import com.alura.literalura.dto.ResultadosLibrosDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private int cantidadDescargas;
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;


    public Libro(){}

    public Libro(ResultadosLibrosDTO libroLista) {
        this.titulo = libroLista.resultados().stream().findFirst().get().titulo();
        this.idioma = libroLista.resultados().stream().findFirst().get().idioma().stream().findFirst().get();
        this.cantidadDescargas = libroLista.resultados().stream().findFirst().get().cantidadDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(int cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString(){
        return "\n╔═══════════════════════════════════╗\n" +
                "║             ** LIBRO **            ║\n" +
                "╠═══════════════════════════════════╣\n" +
                "║ Título:       " + titulo + "\n" +
                "║ Autor:        " + (autor != null ? autor.getNombre() : "N/A") + "\n" +
                "║ Lenguaje:     " + idioma + "\n" +
                "║ Descargas:    " + cantidadDescargas + "\n" +
                "╠═══════════════════════════════════╣\n" +
                "╚═══════════════════════════════════╝\n";
    }
}
