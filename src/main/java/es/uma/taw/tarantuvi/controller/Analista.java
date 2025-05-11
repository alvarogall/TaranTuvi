package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.entity.*;
import es.uma.taw.tarantuvi.dto.Analista.AnalistaFiltroAnios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/analista")
public class Analista {

    @Autowired
    private ValoracionRepository valoracionRepository;
    @Autowired
    private ActuacionRepository actuacionRepository;
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    IdiomaHabladoRepository idiomaHabladoRepository;
    @Autowired
    GeneroPeliculaRepository generoPeliculaRepository;
    @Autowired
    ProductoraRepository productoraRepository;
    @Autowired
    PaisRodajeRepository  paisRodajeRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;
    @Autowired
    TrabajoRepository trabajoRepository;

    @GetMapping("/")
    public String vistaAnalista(Model model) {
        model.addAttribute("paginaActual", "inicio");
        return "Analista/inicioAnalista";
    }

    @GetMapping("/peliculas")
    public String rankingAnalista(Model model, @ModelAttribute("filtro") AnalistaFiltroAnios filtro) {
        model.addAttribute("paginaActual", "peliculas");

        List<PeliculaEntity> listaPeliculasFiltradas = this.peliculaRepository.findPeliculasByFiltros(filtro.getGenero(), filtro.getIdioma());;

        if (filtro.getOrdenCampo() != null && filtro.getOrdenTipo() != null) {
            Comparator<PeliculaEntity> comparator = switch (filtro.getOrdenCampo()) {
                case "fecha" -> Comparator.comparing(PeliculaEntity::getFechaestreno);
                case "duracion" -> Comparator.comparing(PeliculaEntity::getDuracion);
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(filtro.getOrdenTipo())) {
                    comparator = comparator.reversed();
                }
                listaPeliculasFiltradas.sort(comparator);
            }
        }
        model.addAttribute("peliculasFiltradas", listaPeliculasFiltradas);

        int anios = (filtro.getAnios() != null) ? filtro.getAnios() : 25;

        List<Object[]> notas = this.valoracionRepository.getPeliculaConMayorNotaMedia() ;
        List<PeliculaEntity> listaPeliculas = this.peliculaRepository.findAll();
        model.addAttribute("peliculas", listaPeliculas);
        model.addAttribute("notas", notas);
        model.addAttribute("anios", anios);

        List<IdiomaHabladoEntity> idiomas = this.idiomaHabladoRepository.findAll();
        List<GeneroPeliculaEntity> generos = this.generoPeliculaRepository.findAll();
        model.addAttribute("idiomas", idiomas);
        model.addAttribute("generos", generos);

        return "Analista/vistaPeliculasAnalista";
    }

    @GetMapping("/actores")
    public String actoresAnalista(Model model) {
        model.addAttribute("paginaActual", "actores");

        List<Object[]> tasaFemenina = this.actuacionRepository.getFemalePercentagePerMovie();
        model.addAttribute("tasaFemenina", tasaFemenina);

        List<Object[]> tasaFemeninaGlobal = this.actuacionRepository.getFemalePercentageGlobal();
        model.addAttribute("tasaFemeninaGlobal", tasaFemeninaGlobal);

        List<Object[]> numeroGeneros = this.actuacionRepository.getFemaleMaleCounts();
        model.addAttribute("numeroGeneros", numeroGeneros);

        Long totalActores = this.actuacionRepository.getActorCount();
        List<Object[]> tasaNacionalidad = this.actuacionRepository.getCountryCount();
        model.addAttribute("tasaNacionalidad", tasaNacionalidad);
        model.addAttribute("totalActores", totalActores);

        Object[] pelicula = this.valoracionRepository.getPeliculaConMayorNotaMedia().get(0);
        model.addAttribute("pelicula", pelicula);

        PeliculaEntity peliculaID = (PeliculaEntity) pelicula[0];
        List<ActuacionEntity> listaActoresMejorPelicula= this.actuacionRepository.getActoresPelicula(peliculaID.getId());
        model.addAttribute("listaActoresMejorPelicula", listaActoresMejorPelicula);


        return "Analista/vistaActoresAnalista";
    }

    @GetMapping("/productoras")
    public String productorasAnalista(Model model) {
        model.addAttribute("paginaActual", "productoras");


        List<Object[]> productorasNotaMedia = this.productoraRepository.getProductorasConNotaMedia();
        int totalPeliculas = this.productoraRepository.countPeliculasAsociadasProductora();
        model.addAttribute("productorasNotaMedia", productorasNotaMedia);
        model.addAttribute("totalPeliculas", totalPeliculas);

        List<PaisRodajeEntity> paisesRodaje = this.paisRodajeRepository.findAll();
        int totalPaisesRodaje = this.paisRodajeRepository.countPeliculasAsociadasPaisRodaje();
        model.addAttribute("paisesRodaje", paisesRodaje);
        model.addAttribute("totalPaisesRodaje", totalPaisesRodaje);

        List<DepartamentoEntity> departamentos = this.departamentoRepository.findAll();
        int totalTrabajadores = this.trabajoRepository.findAll().size();
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("totalTrabajadores", totalTrabajadores);
        return "Analista/vistaProductorasAnalista";
    }

    @GetMapping("/valoraciones")
    public String valoracionesAnalista(Model model) {
        model.addAttribute("paginaActual", "valoraciones");

        List<Object[]> generosNotaMedia = this.generoPeliculaRepository.findNotaMediaPorGenero();
        model.addAttribute("generosNotaMedia", generosNotaMedia);

        return "Analista/vistaValoracionesAnalista";
    }


}
