package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.ActuacionRepository;
import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dao.ValoracionRepository;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
    public String vistaAnalista() {
        return "Analista/inicioAnalista";
    }

    @GetMapping("/analista/ranking")
    public String rankingAnalista(Model model) {
        List<Object[]> ranking = valoracionRepository.getRankingPeliculas();
        for (Object[] fila : ranking) {
            System.out.println("Pelicula: " + fila[0] + ", Nota media: " + fila[1]);
        }
        model.addAttribute("ranking", ranking);
        return "Analista/vistaRankingAnalista";
    }

    @GetMapping("/analista/actores")
    public String actoresAnalista(Model model) {
        List<Object[]> tasaFemenina = actuacionRepository.getFemalePercentagePerMovie();
        model.addAttribute("tasaFemenina", tasaFemenina);

        List<Object[]> tasaFemeninaGlobal = actuacionRepository.getFemalePercentageGlobal();
        model.addAttribute("tasaFemeninaGlobal", tasaFemeninaGlobal);

        Long totalActores = actuacionRepository.getActorCount();
        List<Object[]> tasaNacionalidad = actuacionRepository.getFemalePercentageGlobal();
        model.addAttribute("tasaNacionalidad", tasaNacionalidad);
        model.addAttribute("totalActores", totalActores);

        return "Analista/vistaActoresAnalista";
    }


}
