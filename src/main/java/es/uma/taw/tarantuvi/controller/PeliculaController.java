package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pelicula")
public class PeliculaController extends BaseController {
    @Autowired
    protected PeliculaRepository peliculaRepository;

    @GetMapping("/")
    public String doDetallesPelicula(@RequestParam("id") Integer id,
                                     Model model,
                                     HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            PeliculaEntity pelicula = this.peliculaRepository.findById(id).orElse(null);
            model.addAttribute("pelicula", pelicula);

            return "pelicula/pelicula";
        }
    }

    @GetMapping("/listar")
    public String doListar(Model model,
                           HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            List<PeliculaEntity> peliculas = this.peliculaRepository.findAll();
            model.addAttribute("peliculas", peliculas);

            return "pelicula/peliculas";
        }
    }
}
