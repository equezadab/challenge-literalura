package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LibroDTO;
import com.alura.literalura.dto.ResultadosLibrosDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConvierteDatos convierteDatos;
    private static final String BASE_URL = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = "\n===========================\n"
                    + "📚  Biblioteca Virtual 📚\n"
                    + "===========================\n"
                    + "1️ - Buscar libro por título\n"
                    + "2️ - Listar libros registrados\n"
                    + "3️ - Listar autores registrados\n"
                    + "4️ - Listar autores vivos por año\n"
                    + "5️ - Listar libros por idioma\n"
                    + "0️ - Salir\n"
                    + "===========================\n"
                    + "🔎  Elige una opción: ";;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarlibro();
                    break;
                case 2:
                    listarLibrosregistrados();
                    break;
                case 3:
                    listarautoresregistrados();
                    break;
                case 4:
                    listarautoresvivos();
                    break;
                case 5:
                    listalibroidioma();
                    break;
                case 0:
                    System.out.println("Cerrando...");
                    break;
                default:
                    System.out.println("Opción inválida, por favor seleccione una opción válida.");
            }
        }
    }
    private void buscarlibro() {
        System.out.print(" Ingrese el titulo del libro: ");
        String tituloLibro = teclado.nextLine();

        String url = "https://gutendex.com/books/?search=" + tituloLibro.replace(" ", "%20");
        String json = consumoAPI.obtenerDatos(url);
        ResultadosLibrosDTO resultados = convierteDatos.convertirDatos(json, ResultadosLibrosDTO.class);
        List<LibroDTO> librosDTO = resultados.resultados();

        System.out.printf("\n");

        if(librosDTO.isEmpty()){
            System.out.println("Libro no encontrado\n");
            return;
        }

        Optional<Libro> optionalLibro = libroService.listarLibroTitulo(resultados.resultados().stream()
                .findFirst().get().titulo());
        if(optionalLibro.isPresent()){
            System.out.println("El libro \'" + optionalLibro.get().getTitulo() + "\' ya se encuentra en la base de datos.");
        }else{

            Libro libroNuevo = new Libro(resultados);

            AutorDTO primerAutorDTO = librosDTO.stream().findFirst().get().autores().get(0);
            Optional<Autor> autor = autorService.obtenerAutorPorNombre(primerAutorDTO.nombre());
            if(!autor.isPresent()){
                Autor autorNuevo = new Autor(resultados);
                autorService.agregarAutor(autorNuevo);

                libroNuevo.setAutor(autorNuevo);

                libroService.agregarLibro(libroNuevo);
                System.out.println(libroNuevo);
                System.out.println("Libro guardado correctamente.");
            }
        }

        System.out.printf("\n");
    }


    private void listarLibrosregistrados() {
        List<Libro> libros = libroService.listarLibros();

        if(libros.isEmpty()){
            System.out.println("La lista de libros esta vacia.");
            return;
        }
        libros.forEach(l -> System.out.println(l));
    }

    private void listarautoresregistrados() {
        List<Autor> autores = autorService.listarAutores();

        if(autores.isEmpty()){
            System.out.println("No hay datos registrados.");
            return;
        }
        autores.forEach(l -> System.out.println(l));
    }

    private void listarautoresvivos(){
        System.out.print("🔎 Escriba el año que desea buscar: ");
        int fecha = teclado.nextInt();

        List<Autor> autoresVivos = autorService.listarAutoresVivos(fecha);

        System.out.printf("\n");

        if(autoresVivos.isEmpty()){
            System.out.println("No hay autores vivos en la fecha: " + fecha + ".\n");
            return;
        }

        autoresVivos.forEach(a -> {
                    System.out.println("╔═══════════════════════════════════╗");
                    System.out.println("║ 👤 ** Autor: " + a.getNombre() + " **     ║");
                    System.out.println("╠═══════════════════════════════════╣");
                    System.out.println("║ 📅 Fecha de Nacimiento: " + a.getFechaNacimiento() + "        ║");
                    System.out.println("║ ✝️ Fecha de Muerte: " + a.getFechaFallecimiento() + "    ║");
                    System.out.println("╠═══════════════════════════════════╣");
                    System.out.println("║ 📚 Libros publicados:             ║");
                    a.getLibrosAutor().forEach(libro ->
                            System.out.println("║   - " + libro.getTitulo() + "                ║")
                    );
                    System.out.println("╚═══════════════════════════════════╝\n");

                    System.out.printf("\n");
                });
        System.out.printf("\n");
    }

    private void listalibroidioma() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║  🌍 ** LENGUAJES DISPONIBLES **   ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("║ es - Español                      ║");
        System.out.println("║ en - Inglés                       ║");
        System.out.println("║ fr - Francés                      ║");
        System.out.println("║ pt - Portugués                    ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("╚═══════════════════════════════════╝\n");

        System.out.print("🔎 Selecciona un idioma: ");
        String idioma = teclado.nextLine().toLowerCase();
        System.out.printf("\n");

        switch (idioma) {
            case "es":
                System.out.println(" Seleccionaste Español.");
                break;
            case "en":
                System.out.println(" Seleccionaste Inglés.");
                break;
            case "fr":
                System.out.println(" Seleccionaste Francés.");
                break;
            case "pt":
                System.out.println(" Seleccionaste Portugués.");
                break;
            default:
                System.out.println(" La opción no es válida. Por favor, elige un idioma válido.\n");
                return;
        }

        List<Libro> libros = libroService.listarLibrosPorIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println(" No hay libros en ese idioma.\n");
            return;
        }

        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║  📖 ** LIBROS DISPONIBLES EN " + idioma.toUpperCase() + " **║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println();

        libros.forEach(libro -> System.out.println("║  - " + libro.getTitulo()));

        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("╚═══════════════════════════════════╝\n");
    }

}
