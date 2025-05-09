package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.ActuacionRepository;
import es.uma.taw.tarantuvi.dao.ValoracionRepository;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.dto.Analista.AnalistaFiltroAnios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/analista")
public class Analista {

    @Autowired
    private ValoracionRepository valoracionRepository;
    @Autowired
    private ActuacionRepository actuacionRepository;

    @GetMapping("/")
    public String vistaAnalista(Model model) {
        model.addAttribute("paginaActual", "inicio");
        return "Analista/inicioAnalista";
    }

    @GetMapping("/ranking")
    public String rankingAnalista(Model model, @ModelAttribute("filtro") AnalistaFiltroAnios filtro) {
        model.addAttribute("paginaActual", "peliculas");

        int anios = (filtro.getAnios() != null) ? filtro.getAnios() : 25;

        List<Object[]> listaPeliculas = this.valoracionRepository.getPeliculaConMayorNotaMedia();
        model.addAttribute("peliculas", listaPeliculas);
        model.addAttribute("anios", anios);
        return "Analista/vistaRankingAnalista";
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


}
