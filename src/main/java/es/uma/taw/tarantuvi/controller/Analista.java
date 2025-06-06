/**
 * @author Alejandro Cueto
 */

package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dto.*;
import es.uma.taw.tarantuvi.dto.Analista.AnalistaFiltroAnios;
import es.uma.taw.tarantuvi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/analista")
public class Analista extends BaseController {

    @Autowired
    protected PeliculaService peliculaService;
    @Autowired
    protected IdiomaHabladoService  idiomaHabladoService;
    @Autowired
    protected GeneroPeliculaService generoPeliculaService;
    @Autowired
    protected ActuacionService actuacionService;
    @Autowired
    protected ProductoraService productoraService;
    @Autowired
    protected PaisRodajeService paisRodajeService;
    @Autowired
    protected TrabajoService trabajoService;
    @Autowired
    protected DepartamentoService departamentoService;


    @GetMapping("/")
    public String vistaAnalista(Model model) {
        model.addAttribute("paginaActual", "inicio");
        return "Analista/inicioAnalista";
    }

    @GetMapping("/peliculas")
    public String rankingAnalista(Model model, @ModelAttribute("filtro") AnalistaFiltroAnios filtro) {
        model.addAttribute("paginaActual", "peliculas");

        List<Pelicula> listaPeliculasFiltradas = this.peliculaService.buscarPeliculasPorGeneroIdioma(filtro.getGenero(), filtro.getIdioma(), filtro.getOrdenCampo(), filtro.getOrdenTipo());;
        model.addAttribute("peliculasFiltradas", listaPeliculasFiltradas);

        List<Pelicula> listaPeliculas = this.peliculaService.listarPeliculas();
        model.addAttribute("peliculas", listaPeliculas);

        model.addAttribute("anios", filtro.getAnios() != null ? filtro.getAnios() : 25);

        List<IdiomaHablado> idiomas = this.idiomaHabladoService.listarIdiomasHablados();
        List<GeneroPelicula> generos = this.generoPeliculaService.listarGenerosPeliculas();
        model.addAttribute("idiomas", idiomas);
        model.addAttribute("generos", generos);

        return "Analista/vistaPeliculasAnalista";
    }

    @GetMapping("/actores")
    public String actoresAnalista(@ModelAttribute("orden") AnalistaFiltroAnios orden, Model model) {
        model.addAttribute("paginaActual", "actores");

        model.addAttribute("orden", orden);

        List<Object[]> tasaFemeninaGlobal = this.actuacionService.obtenerTasaFemeninaGlobal();
        model.addAttribute("tasaFemeninaGlobal", tasaFemeninaGlobal);

        List<Object[]> datos = actuacionService.obtenerTasaFemeninaPorPelicula(orden.getOrdenCampo(), orden.getOrdenTipo());
        model.addAttribute("tasaFemenina", datos);

        List<Object[]> tasaNacionalidad = this.actuacionService.obtenerTasaPorNacionalidad(orden.getOrdenCampoAuxiliar(), orden.getOrdenTipoAuxiliar());
        model.addAttribute("tasaNacionalidad", tasaNacionalidad);

        List<Object[]> numeroGeneros = this.actuacionService.obtenerNumeroGeneros();
        model.addAttribute("numeroGeneros", numeroGeneros);

        Long totalActores = this.actuacionService.obtenerTotalActores();
        model.addAttribute("totalActores", totalActores);

        List<Object[]> topPeliculas = this.peliculaService.obtenerMejorPelicula();
        model.addAttribute("pelicula", topPeliculas);

        Pelicula pelicula = (Pelicula) topPeliculas.get(0)[3];
        List<Actuacion> listaActoresMejorPelicula= this.actuacionService.obtenerActoresDePelicula(pelicula.getId());
        model.addAttribute("listaActoresMejorPelicula", listaActoresMejorPelicula);


        return "Analista/vistaActoresAnalista";
    }

    @GetMapping("/productoras")
    public String productorasAnalista(@ModelAttribute("orden") AnalistaFiltroAnios orden, Model model) {
        model.addAttribute("paginaActual", "productoras");

        List<Productora> productorasNotaMedia = this.productoraService.obtenerProductorasConNotasMediasYFiltrosOrdenadas(orden.getCantidadMinima(), orden.getCantidadMaxima(), orden.getOrdenCampo(), orden.getOrdenTipo());
        model.addAttribute("productorasNotaMedia", productorasNotaMedia);
        model.addAttribute("totalPeliculas", this.productoraService.contarPeliculasAsociadasProductora());

        List<PaisRodaje> paisesRodaje = this.paisRodajeService.listarPaisesRodajeOrdenados(orden.getOrdenCampoAuxiliar(), orden.getOrdenTipoAuxiliar());
        model.addAttribute("paisesRodaje", paisesRodaje);
        model.addAttribute("totalPaisesRodaje", this.paisRodajeService.contarPeliculasAsociadasPaisRodaje());

        model.addAttribute("departamentos", this.departamentoService.listarDepartamentos());
        model.addAttribute("totalTrabajadores", this.trabajoService.listarTrabajos().size());

        return "Analista/vistaProductorasAnalista";
    }


    @GetMapping("/valoraciones")
    public String valoracionesAnalista(Model model) {
        model.addAttribute("paginaActual", "valoraciones");

        model.addAttribute("generosNotaMedia", this.generoPeliculaService.obtenerNotaMediaPorGeneroPelicula());

        model.addAttribute("generosNumValoraciones", this.generoPeliculaService.obtenerNumeroValoracionesPorGeneroPelicula());

        model.addAttribute("topPeliculas", this.peliculaService.obtener10MejoresPelicula());

        return "Analista/vistaValoracionesAnalista";
    }


}
