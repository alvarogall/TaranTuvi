/*
User: jesus
*/

package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dto.*;
import es.uma.taw.tarantuvi.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/editor")
public class Editor extends BaseController {
    @Autowired
    protected PeliculaService peliculaService;
    @Autowired
    protected ActuacionService actuacionService;
    @Autowired
    protected PersonaService personaService;
    @Autowired
    protected ProductoraService productoraService;
    @Autowired
    protected IdiomaHabladoService idiomaHabladoService;
    @Autowired
    protected GeneroPeliculaService generoPeliculaService;
    @Autowired
    protected TrabajoService trabajoService;
    @Autowired
    protected PaisRodajeService paisRodajeService;
    @Autowired
    protected GeneroPersonaService generoPersonaService;
    @Autowired
    protected NacionalidadService nacionalidadService;

    @GetMapping("/")
    public String vistaEditor(HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else{
            return "Editor/inicio";
        }
    }

    @GetMapping("/peliculas")
    public String vistaPeliculas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else{
            List<Pelicula> peliculas = this.peliculaService.listarPeliculas();
            model.addAttribute("peliculas", peliculas);
            return "Editor/peliculas";
        }
    }

    @GetMapping("/actores")
    public String vistaActores(Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else{
            List<Actor> personas = this.personaService.listarPersonas();
            List<Actuacion> actuaciones = this.actuacionService.listarActuaciones();
            List<Pelicula> peliculas = this.peliculaService.listarPeliculas();
            List<GeneroPelicula> generos = this.generoPeliculaService.listarGenerosPeliculas();
            model.addAttribute("personas", personas);
            model.addAttribute("generos", generos);
            model.addAttribute("peliculas", peliculas);
            model.addAttribute("actuaciones", actuaciones);
            return "Editor/actores";
        }
    }

    @PostMapping("/peliculas/editar")
    public String doEditarPelicula(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else{
            Pelicula entidad = this.peliculaService.buscarPelicula(id);

            List<Actuacion> actuacionesFiltradas = new ArrayList<>();
            if (entidad.getActuacionList() != null) {
                actuacionesFiltradas.addAll(entidad.getActuacionList());
            }
            // Construir conjunto de claves de las ya añadidas
            List<String> clavesAct = new ArrayList<>();
            for (Actuacion a : actuacionesFiltradas) {
                String clave = a.getPersonaid().getId() + "|" + a.getPersonaje();
                if (!clavesAct.contains(clave)) {
                    clavesAct.add(clave);
                }
            }
            // Añadir luego las que falten
            for (Actuacion a : this.actuacionService.listarActuaciones()) {
                String clave = a.getPersonaid().getId() + "|" + a.getPersonaje();
                if (!clavesAct.contains(clave)) {
                    clavesAct.add(clave);
                    actuacionesFiltradas.add(a);
                }
            }

            List<Trabajo> crewFiltrado = new ArrayList<>();
            if (entidad.getTrabajoList() != null) {
                crewFiltrado.addAll(entidad.getTrabajoList());
            }
            List<String> clavesTrab = new ArrayList<>();
            for (Trabajo t : crewFiltrado) {
                String clave = t.getPersonaid().getId() + "|" + t.getTrabajonombre();
                if (!clavesTrab.contains(clave)) {
                    clavesTrab.add(clave);
                }
            }
            for (Trabajo t : this.trabajoService.listarTrabajos()) {
                String clave = t.getPersonaid().getId() + "|" + t.getTrabajonombre();
                if (!clavesTrab.contains(clave)) {
                    clavesTrab.add(clave);
                    crewFiltrado.add(t);
                }
            }

            List<Productora> productoras  = this.productoraService.listarProductoras();
            List<IdiomaHablado> idiomas      = this.idiomaHabladoService.listarIdiomasHablados();
            List<GeneroPelicula> generos      = this.generoPeliculaService.listarGenerosPeliculas();
            List<PaisRodaje> paisesRodaje = this.paisRodajeService.listarPaisesRodaje();

            model.addAttribute("pelicula",     entidad);
            model.addAttribute("actuaciones",  actuacionesFiltradas);
            model.addAttribute("crewList",     crewFiltrado);
            model.addAttribute("productoras",  productoras);
            model.addAttribute("idiomas",      idiomas);
            model.addAttribute("generos",      generos);
            model.addAttribute("paisesRodaje", paisesRodaje);

            return "Editor/pelicula";
        }
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
            for(Actuacion a : pelicula.getActuacionList()) {
                this.actuacionService.borrarActuacion(a.getId());
            }
            for(Trabajo t : pelicula.getTrabajoList()) {
                this.trabajoService.borrarTrabajo(t.getId());
            }
        }
        this.peliculaService.borrarPelicula(id);
        return "redirect:/editor/peliculas";
    }

    @PostMapping("/actores/editar")
    public String doEditarActor(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else{
            Actor persona = this.personaService.buscarPersona(id);

            model.addAttribute("actor", persona);
            model.addAttribute("peliculas", this.peliculaService.listarPeliculas());
            model.addAttribute("actuaciones", this.actuacionService.listarActuaciones());
            model.addAttribute("generosPersona", this.generoPersonaService.listarGenerosPersonas());
            model.addAttribute("nacionalidades", this.nacionalidadService.listarNacionalidades());

            return "Editor/actor";
        }
    }

    @PostMapping("/actores/confirmarCambios")
    public String doConfirmarCambiosActores(@ModelAttribute Actor dtoActor) {
        this.personaService.guardarActor(dtoActor);
        return "redirect:/editor/actores";
    }

    @PostMapping("actores/borrar")
    public String doBorrarActor(@RequestParam("id") Integer id){
        for(Actuacion a : this.actuacionService.listarActuaciones()){
            if(Objects.equals(a.getPersonaid().getId(), id)){
                this.actuacionService.borrarActuacion(a.getId());
            }
        }
        this.personaService.borrarPersona(id);
        return "redirect:/editor/actores";
    }
}
