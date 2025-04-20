package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Editor {
    @Autowired
    protected PruebaRepository pruebaRepository;

    @GetMapping("/")
    public String vistaEditor() {
        return "inicioEditor";
    }

    @GetMapping("/peliculas")
    public String vistaPeliculas() {
        return "vistaPeliculasEditor";
    }

    @GetMapping("/actores")
    public String vistaActores() {
        return "vistaActoresEditor";
    }

}
