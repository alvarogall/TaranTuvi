package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.ActuacionRepository;
import es.uma.taw.tarantuvi.dao.ValoracionRepository;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.ui.Analista.AnalistaPeliculasForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class Analista {

    @Autowired
    private ValoracionRepository valoracionRepository;
    @Autowired
    private ActuacionRepository actuacionRepository;

    @GetMapping("/analista")
    public String vistaAnalista(Model model) {
        model.addAttribute("paginaActual", "inicio");
        return "Analista/inicioAnalista";
    }

    @GetMapping("/analista/ranking")
    public String rankingAnalista(Model model) {
        model.addAttribute("paginaActual", "peliculas");

        List<Object[]> listaPeliculas = this.valoracionRepository.getPeliculasConNota();
        model.addAttribute("peliculas", listaPeliculas);
        model.addAttribute("filtro", new AnalistaPeliculasForm());
        return "Analista/vistaRankingAnalista";
    }

    @GetMapping("/analista/actores")
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
