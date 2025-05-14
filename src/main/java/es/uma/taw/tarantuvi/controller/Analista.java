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

import java.math.BigDecimal;
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

        List<Object[]> listaPeliculasFiltradas = this.peliculaRepository.findPeliculasByFiltros(filtro.getGenero(), filtro.getIdioma());;

        if (filtro.getOrdenCampo() != null && filtro.getOrdenTipo() != null) {
            Comparator<Object[]> comparator = switch (filtro.getOrdenCampo()) {
                case "fecha" -> Comparator.comparing(a -> ((PeliculaEntity) a[0]).getFechaestreno());
                case "duracion" -> Comparator.comparing(a -> ((PeliculaEntity) a[0]).getDuracion());
                case "nota" -> Comparator.comparing(a -> (a[1] != null ? (Double) a[1] : 0.0));
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

        List<PeliculaEntity> listaPeliculas = this.peliculaRepository.findAll();
        model.addAttribute("peliculas", listaPeliculas);
        model.addAttribute("anios", anios);

        List<IdiomaHabladoEntity> idiomas = this.idiomaHabladoRepository.findAll();
        List<GeneroPeliculaEntity> generos = this.generoPeliculaRepository.findAll();
        model.addAttribute("idiomas", idiomas);
        model.addAttribute("generos", generos);

        return "Analista/vistaPeliculasAnalista";
    }

    @GetMapping("/actores")
    public String actoresAnalista(@ModelAttribute("orden") AnalistaFiltroAnios orden, Model model) {
        model.addAttribute("paginaActual", "actores");

        model.addAttribute("orden", orden);

        List<Object[]> tasaFemeninaGlobal = this.actuacionRepository.getFemalePercentageGlobal();
        model.addAttribute("tasaFemeninaGlobal", tasaFemeninaGlobal);

        List<Object[]> datos = actuacionRepository.getFemalePercentagePerMovie();


            if (orden.getOrdenCampo() != null && orden.getOrdenTipo() != null) {
                Comparator<Object[]> comparator = switch (orden.getOrdenCampo()) {
                    case "titulo" -> Comparator.comparing(a -> (String) a[0]);
                    case "actrices" -> Comparator.comparing(a -> (Double) a[1]);
                    case "actores" -> Comparator.comparing(a -> (Double) a[2]);
                    default -> null;
                };

                if (comparator != null) {
                    if ("DESC".equalsIgnoreCase(orden.getOrdenTipo())) {
                        comparator = comparator.reversed();
                    }
                    datos.sort(comparator);
                }
            }
        model.addAttribute("tasaFemenina", datos);


        List<Object[]> tasaNacionalidad = this.actuacionRepository.getCountryCount();

            if (orden.getOrdenCampoAuxiliar() != null && orden.getOrdenTipoAuxiliar() != null) {
                Comparator<Object[]> comparator = switch (orden.getOrdenCampoAuxiliar()) {
                    case "nacionalidad" -> Comparator.comparing(a -> (String) a[0]);
                    case "cantidad" -> Comparator.comparing(a -> (Long) a[1]);
                    default -> null;
                };

                if (comparator != null) {
                    if ("DESC".equalsIgnoreCase(orden.getOrdenTipoAuxiliar())) {
                        comparator = comparator.reversed();
                    }
                    tasaNacionalidad.sort(comparator);
                }
            }

        model.addAttribute("tasaNacionalidad", tasaNacionalidad);

        List<Object[]> numeroGeneros = this.actuacionRepository.getFemaleMaleCounts();
        model.addAttribute("numeroGeneros", numeroGeneros);

        Long totalActores = this.actuacionRepository.getActorCount();


        model.addAttribute("totalActores", totalActores);

        Object[] pelicula = this.valoracionRepository.getPeliculaConMayorNotaMedia().get(0);
        List<Object[]> topPeliculas = peliculaRepository.findPeliculasOrdenadasPorNotaYValoraciones();
        topPeliculas = topPeliculas.stream().limit(1).toList();
        model.addAttribute("pelicula", topPeliculas);

        Object[] peliculaObjeto = (Object[]) topPeliculas.get(0);
        PeliculaEntity peliculaID = (PeliculaEntity) pelicula[0];
        List<ActuacionEntity> listaActoresMejorPelicula= this.actuacionRepository.getActoresPelicula(peliculaID.getId());
        model.addAttribute("listaActoresMejorPelicula", listaActoresMejorPelicula);


        return "Analista/vistaActoresAnalista";
    }

    @GetMapping("/productoras")
    public String productorasAnalista(@ModelAttribute("orden") AnalistaFiltroAnios orden, Model model) {
        model.addAttribute("paginaActual", "productoras");

        Double presupuestoMin = null, presupuestoMax = null, recaudacionMin = null, recaudacionMax = null;
        if(orden.getOrdenCampo() != null) {
        switch (orden.getOrdenCampo()){
            case "presupuesto" -> {
                presupuestoMin = orden.getCantidadMinima(); presupuestoMax = orden.getCantidadMaxima();
            }
            case "recaudacion" -> {
                recaudacionMin = orden.getCantidadMinima(); recaudacionMax = orden.getCantidadMaxima();
            }
            }
        }
        List<Object[]> productorasNotaMedia = this.productoraRepository.getProductorasConNotasMediasYFiltros(presupuestoMin, presupuestoMax, recaudacionMin, recaudacionMax);
        int totalPeliculas = this.productoraRepository.countPeliculasAsociadasProductora();

        if (orden.getOrdenCampo() != null && orden.getOrdenTipo() != null) {
            Comparator<Object[]> comparator = switch (orden.getOrdenCampo()) {
                case "productora" -> Comparator.comparing(a -> ((ProductoraEntity) a[0]).getProductoranombre());
                case "peliculas" -> Comparator.comparing(a -> ((ProductoraEntity) a[0]).getPeliculaList().size());
                case "nota" -> Comparator.comparing(a -> (a[1] != null ? (Double) a[1] : 0.0));
                case "presupuesto" -> Comparator.comparing(a -> (a[2] != null ? (Double) a[2] : 0.0));
                case "recaudacion" -> Comparator.comparing(a ->(a[3] != null ? (Double) a[3] : 0.0));
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(orden.getOrdenTipo())) {
                    comparator = comparator.reversed();
                }
                productorasNotaMedia.sort(comparator);
            }
        }

        model.addAttribute("productorasNotaMedia", productorasNotaMedia);
        model.addAttribute("totalPeliculas", totalPeliculas);


        List<PaisRodajeEntity> paisesRodaje = this.paisRodajeRepository.findAll();
        int totalPaisesRodaje = this.paisRodajeRepository.countPeliculasAsociadasPaisRodaje();

        if (orden.getOrdenCampoAuxiliar() != null && orden.getOrdenTipoAuxiliar() != null) {
            Comparator<PaisRodajeEntity> comparator = switch (orden.getOrdenCampoAuxiliar()) {
                case "pais" -> Comparator.comparing(PaisRodajeEntity :: getPaisrodajenombre);
                case "peliculas" -> Comparator.comparing(p -> p.getPeliculaList().size());
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(orden.getOrdenTipoAuxiliar())) {
                    comparator = comparator.reversed();
                }
                paisesRodaje.sort(comparator);
            }
        }

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

        List<Object[]> generosNumValoraciones = this.generoPeliculaRepository.findNumeroValoracionesPorGenero();
        model.addAttribute("generosNumValoraciones", generosNumValoraciones);

        List<Object[]> topPeliculas = peliculaRepository.findPeliculasOrdenadasPorNotaYValoraciones();
        topPeliculas = topPeliculas.stream().limit(10).toList();
        model.addAttribute("topPeliculas", topPeliculas);

        return "Analista/vistaValoracionesAnalista";
    }


}
