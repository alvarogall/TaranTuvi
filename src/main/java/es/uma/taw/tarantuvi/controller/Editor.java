package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Editor {
    @Autowired
    protected PeliculaRepository peliculaRepository;
    @Autowired
    private ActuacionRepository actuacionRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private ProductoraRepository productoraRepository;
    @Autowired
    private IdiomaHabladoRepository idiomaHabladoRepository;
    @Autowired
    private GeneroPeliculaRepository generoPeliculaRepository;

    @GetMapping("/")
    public String vistaEditor() {
        return "inicioEditor";
    }

    @GetMapping("/peliculas")
    public String vistaPeliculas(Model model) {
        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        model.addAttribute("peliculas", peliculas);
        return "vistaPeliculasEditor";
    }

    @GetMapping("/actores")
    public String vistaActores() {
        return "vistaActoresEditor";
    }

    @PostMapping("/peliculas/editar")
    public String vistaEditarPelicula(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        PeliculaEntity pelicula = this.peliculaRepository.findById(id).orElse(new PeliculaEntity());

        List<ActuacionEntity> actuaciones = this.actuacionRepository.findAll();
        List<PersonaEntity> personas = this.personaRepository.findAll();
        List<ProductoraEntity> productoras = this.productoraRepository.findAll();
        List<IdiomaHabladoEntity> idiomas = this.idiomaHabladoRepository.findAll();
        List<GeneroPeliculaEntity> generos = this.generoPeliculaRepository.findAll();

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("actuaciones", actuaciones);
        model.addAttribute("personas", personas);
        model.addAttribute("productoras", productoras);
        model.addAttribute("idiomas", idiomas);
        model.addAttribute("generos", generos);

        return "peliculaEditor";
    }

}
