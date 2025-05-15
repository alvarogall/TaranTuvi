/*
User: jesus
*/

package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.entity.*;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.service.PeliculaService;
import es.uma.taw.tarantuvi.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/editor")
public class Editor {
    @Autowired
    protected PeliculaService peliculaService;
    @Autowired
    private ActuacionRepository actuacionRepository;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private ProductoraRepository productoraRepository;
    @Autowired
    private IdiomaHabladoRepository idiomaHabladoRepository;
    @Autowired
    private GeneroPeliculaRepository generoPeliculaRepository;
    @Autowired
    private TrabajoRepository trabajoRepository;
    @Autowired
    private PaisRodajeRepository paisRodajeRepository;
    @Autowired
    private GeneroPersonaRepository generoPersonaRepository;
    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @GetMapping("/")
    public String vistaEditor() {
        return "Editor/inicio";
    }

    @GetMapping("/peliculas")
    public String vistaPeliculas(Model model) {
        List<Pelicula> peliculas = this.peliculaService.listarPeliculas();
        model.addAttribute("peliculas", peliculas);
        return "Editor/peliculas";
    }

    @GetMapping("/actores")
    public String vistaActores(Model model) {
        List<Actor> personas = this.personaService.listarPersonas();
        List<ActuacionEntity> actuaciones = actuacionRepository.findAll();
        List<Pelicula> peliculas = this.peliculaService.listarPeliculas();
        List<GeneroPeliculaEntity> generos = generoPeliculaRepository.findAll();
        model.addAttribute("personas", personas);
        model.addAttribute("generos", generos);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("actuaciones", actuaciones);
        return "Editor/actores";
    }

    @PostMapping("/peliculas/editar")
    public String doEditarPelicula(@RequestParam(value = "id", defaultValue = "-1") Integer id,
                                   Model model) {

        Pelicula entidad = this.peliculaService.buscarPelicula(id);

        List<ActuacionEntity> actuacionesFiltradas = new ArrayList<>();
        if (entidad.getActuacionList() != null) {
            actuacionesFiltradas.addAll(entidad.getActuacionList());
        }
        // Construir conjunto de claves de las ya añadidas
        List<String> clavesAct = new ArrayList<>();
        for (ActuacionEntity a : actuacionesFiltradas) {
            String clave = a.getPersonaid().getId() + "|" + a.getPersonaje();
            if (!clavesAct.contains(clave)) {
                clavesAct.add(clave);
            }
        }
        // Añadir luego las que falten
        for (ActuacionEntity a : actuacionRepository.findAll()) {
            String clave = a.getPersonaid().getId() + "|" + a.getPersonaje();
            if (!clavesAct.contains(clave)) {
                clavesAct.add(clave);
                actuacionesFiltradas.add(a);
            }
        }

        List<TrabajoEntity> crewFiltrado = new ArrayList<>();
        if (entidad.getTrabajoList() != null) {
            crewFiltrado.addAll(entidad.getTrabajoList());
        }
        List<String> clavesTrab = new ArrayList<>();
        for (TrabajoEntity t : crewFiltrado) {
            String clave = t.getPersonaid().getId() + "|" + t.getTrabajonombre();
            if (!clavesTrab.contains(clave)) {
                clavesTrab.add(clave);
            }
        }
        for (TrabajoEntity t : trabajoRepository.findAll()) {
            String clave = t.getPersonaid().getId() + "|" + t.getTrabajonombre();
            if (!clavesTrab.contains(clave)) {
                clavesTrab.add(clave);
                crewFiltrado.add(t);
            }
        }

        List<ProductoraEntity>     productoras  = productoraRepository.findAll();
        List<IdiomaHabladoEntity>  idiomas      = idiomaHabladoRepository.findAll();
        List<GeneroPeliculaEntity> generos      = generoPeliculaRepository.findAll();
        List<PaisRodajeEntity>     paisesRodaje = paisRodajeRepository.findAll();

        model.addAttribute("pelicula",     entidad);
        model.addAttribute("actuaciones",  actuacionesFiltradas);
        model.addAttribute("crewList",     crewFiltrado);
        model.addAttribute("productoras",  productoras);
        model.addAttribute("idiomas",      idiomas);
        model.addAttribute("generos",      generos);
        model.addAttribute("paisesRodaje", paisesRodaje);

        return "Editor/pelicula";
    }

    @PostMapping("/peliculas/confirmarCambios")
    public String doConfirmarCambiosPelicula(@ModelAttribute Pelicula dtoPelicula) {
        this.peliculaService.guardarPelicula(dtoPelicula);

        return "redirect:/editor/peliculas";
    }

    @PostMapping("peliculas/borrar")
    public String doBorrarPelicula(@RequestParam("id") Integer id){
        Pelicula pelicula = this.peliculaService.buscarPelicula(id);
        if(pelicula != null) {
            for(ActuacionEntity a : pelicula.getActuacionList()) {
                this.actuacionRepository.deleteById(a.getId());
            }
            for(TrabajoEntity t : pelicula.getTrabajoList()) {
                this.trabajoRepository.deleteById(t.getId());
            }
        }
        this.peliculaService.borrarPelicula(id);
        return "redirect:/editor/peliculas";
    }

    @PostMapping("/actores/editar")
    public String doEditarActor(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        Actor persona = this.personaService.buscarPersona(id);

        model.addAttribute("actor", persona);
        model.addAttribute("peliculas", this.peliculaService.listarPeliculas());
        model.addAttribute("actuaciones", actuacionRepository.findAll());
        model.addAttribute("generosPeliculas", generoPeliculaRepository.findAll());
        model.addAttribute("generosPersona", generoPersonaRepository.findAll());
        model.addAttribute("nacionalidades", nacionalidadRepository.findAll());

        return "Editor/actor";
    }

    @PostMapping("/actores/confirmarCambios")
    public String doConfirmarCambiosActores(@ModelAttribute Actor dtoActor) {
        this.personaService.guardarActor(dtoActor);

        return "redirect:/editor/actores";
    }

    @PostMapping("actores/borrar")
    public String doBorrarActor(@RequestParam("id") Integer id){
        for(ActuacionEntity a : this.actuacionRepository.findAll()){
            if(Objects.equals(a.getPersonaid().getId(), id)){
                this.actuacionRepository.delete(a);
            }
        }
        this.personaService.borrarPersona(id);
        return "redirect:/editor/actores";
    }
}
