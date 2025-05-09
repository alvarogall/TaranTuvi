package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.entity.*;
import es.uma.taw.tarantuvi.dto.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/editor")
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
    @Autowired
    private TrabajoRepository trabajoRepository;
    @Autowired
    private PaisRodajeRepository paisRodajeRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
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
        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        model.addAttribute("peliculas", peliculas);
        return "Editor/peliculas";
    }

    @GetMapping("/actores")
    public String vistaActores(Model model) {
        List<PersonaEntity> personas = personaRepository.findAll();
        List<ActuacionEntity> actuaciones = actuacionRepository.findAll();
        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        List<GeneroPeliculaEntity> generos = generoPeliculaRepository.findAll();
        model.addAttribute("personas", personas);
        model.addAttribute("generos", generos);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("actuaciones", actuaciones);
        return "Editor/actores";
    }

    @PostMapping("/peliculas/editar")
    public String doEditarPelicula(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        PeliculaEntity pelicula = this.peliculaRepository.findById(id).orElse(new PeliculaEntity());

        List<ActuacionEntity> actuaciones = this.actuacionRepository.findAll();
        List<ProductoraEntity> productoras = this.productoraRepository.findAll();
        List<IdiomaHabladoEntity> idiomas = this.idiomaHabladoRepository.findAll();
        List<GeneroPeliculaEntity> generos = this.generoPeliculaRepository.findAll();
        List<PaisRodajeEntity> paisesRodaje = this.paisRodajeRepository.findAll();
        List<TrabajoEntity> trabajos = this.trabajoRepository.findAll();

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("actuaciones", actuaciones);
        model.addAttribute("productoras", productoras);
        model.addAttribute("idiomas", idiomas);
        model.addAttribute("generos", generos);
        model.addAttribute("paisesRodaje", paisesRodaje);
        model.addAttribute("trabajos", trabajos);

        return "Editor/pelicula";
    }

    @PostMapping("/peliculas/confirmarCambios")
    public String doConfirmarCambiosPelicula(@RequestParam("id") Integer id, @ModelAttribute() Pelicula pelicula, Model model) {

        PeliculaEntity p = peliculaRepository.findById(id).orElse(new PeliculaEntity());
        List<Integer> cast = pelicula.getCast();
        List<Integer> crew = pelicula.getCrew();
        List<Integer> productoras  = pelicula.getProductoras();
        List<Integer> paisesRodaje = pelicula.getPaisesRodaje();
        List<Integer> idiomas = pelicula.getIdiomas();
        List<Integer> generos  = pelicula.getGeneros();

        // Asignar valores simples
        p.setTitulooriginal(pelicula.getNombre());
        p.setFechaestreno(LocalDate.parse(pelicula.getFecha()));
        p.setDuracion(Integer.parseInt(pelicula.getDuracion().trim()));
        p.setDescripcion(pelicula.getDescripcion());
        p.setRecaudacion(BigDecimal.valueOf(Long.parseLong(pelicula.getRecaudacion().trim())));
        p.setEstado(pelicula.getEstado());
        p.setPresupuesto(BigDecimal.valueOf(Long.parseLong(pelicula.getPresupuesto().trim())));
        p.setUrlcaratula(pelicula.getUrlCaratula());
        p.setPaginaweb(pelicula.getPaginaweb());
        p.setEslogan(pelicula.getEslogan());

        // Limpiar listas relacionadas

        if(id != -1){
            for(ActuacionEntity a : p.getActuacionList()){
                a.setPeliculaId(null);
            }

            for(TrabajoEntity t : p.getTrabajoList()){
                t.setPeliculaId(null);
            }
        }
        p.setActuacionList(new ArrayList<>());
        p.setTrabajoList(new ArrayList<>());
        p.setGeneroPeliculaList(new ArrayList<>());
        p.setProductoraList(new ArrayList<>());
        p.setPaisRodajeList(new ArrayList<>());
        p.setIdiomaHabladoList(new ArrayList<>());

        // Cast
        if (cast != null && !cast.isEmpty()) {
            for (Integer castId : cast) {
                ActuacionEntity a = actuacionRepository.findById(castId).orElse(null);
                if (a != null) {
                    a.setPeliculaId(p);
                    p.getActuacionList().add(a);
                }
            }
        }

        // Crew
        if (crew != null && !crew.isEmpty()) {
            for (Integer crewId : crew) {
                TrabajoEntity trabajo = this.trabajoRepository.findById(crewId).orElse(null);
                if (trabajo != null) {
                    trabajo.setPeliculaId(p);
                    p.getTrabajoList().add(trabajo);
                }
            }
        }

        // Idiomas
        if (idiomas != null && !idiomas.isEmpty()) {
            for (Integer idiomaId : idiomas) {
                idiomaHabladoRepository.findById(idiomaId).ifPresent(i -> p.getIdiomaHabladoList().add(i));
            }
        }

        /*
        System.out.println("Idiomas recibidos: " + idiomas);
        System.out.println("Cast recibido: " + cast);
        System.out.println("Crew recibido: " + crew);
        */

        // Productoras
        if (productoras != null) {
            for (Integer productoraId : productoras) {
                productoraRepository.findById(productoraId).ifPresent(pe -> p.getProductoraList().add(pe));
            }
        }

        // Países de rodaje
        if (paisesRodaje != null) {
            for (Integer paisId : paisesRodaje) {
                paisRodajeRepository.findById(paisId).ifPresent(pe -> p.getPaisRodajeList().add(pe));
            }
        }

        // Géneros
        if (generos != null) {
            for (Integer generoId : generos) {
                generoPeliculaRepository.findById(generoId).ifPresent(g -> p.getGeneroPeliculaList().add(g));
            }
        }

        /*
        for(ActuacionEntity a : pelicula.getActuacionList()){
            System.out.println(a.getPersonaid().getNombre() + " - " + a.getPersonaje());
        }
        this.peliculaRepository.save(pelicula);
        for(ActuacionEntity a : pelicula.getActuacionList()){
            System.out.println(a.getPersonaid().getNombre() + " - " + a.getPersonaje());
        }
        */

        this.peliculaRepository.save(p);
        return "redirect:/editor/peliculas";
    }


    @PostMapping("peliculas/borrar")
    public String doBorrarPelicula(@RequestParam("id") Integer id){
        this.peliculaRepository.deleteById(id);
        return "redirect:/editor/peliculas";
    }

    @PostMapping("/actores/editar")
    public String doEditarActor(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model){
        PersonaEntity persona = personaRepository.findById(id).orElse(null);

        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        List<ActuacionEntity> actuaciones = actuacionRepository.findAll();
        List<GeneroPeliculaEntity> generos = generoPeliculaRepository.findAll();

        model.addAttribute("persona", persona);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("actuaciones", actuaciones);
        model.addAttribute("generos", generos);

        return "Editor/actor";
    }

    @PostMapping("/actores/confirmarCambios")
    public String doConfirmarCambiosActores(
            @RequestParam(value = "id", defaultValue = "-1") Integer id,
            @RequestParam("url") String url,
            @RequestParam("nombre") String nombre,
            @RequestParam("genero") String genero,
            @RequestParam("nacionalidad") String nacionalidad,
            @RequestParam("peliculas") List<Integer> peliculas,
            @RequestParam("personajes") List<Integer> personajes,
            @RequestParam("generos") List<Integer> generos,
            Model model){

            PersonaEntity persona = personaRepository.findById(id).orElse(new PersonaEntity());
            GeneroPersonaEntity generoPersona = this.generoPersonaRepository.findByNombre(genero.trim());
            NacionalidadEntity nacionalidadPersona = this.nacionalidadRepository.findByNombre(nacionalidad.trim());

            persona.setUrlfoto(url);
            persona.setNombre(nombre);
            persona.setGeneropersonaid(generoPersona);
            persona.setNacionalidadid(nacionalidadPersona);

            for(Integer p : peliculas){
                PeliculaEntity pelicula = this.peliculaRepository.findById(p).orElse(null);
                for(ActuacionEntity act : persona.getActuacionList()){
                    if(act.getPeliculaid().equals(pelicula.getId())){
                        act.setPersonaId(null);
                        act.setPeliculaId(null);
                    }
                }
            }
            
            persona.setActuacionList(new ArrayList<>());

            if(peliculas != null){
                for(Integer peliculaId : peliculas){
                    PeliculaEntity pelicula = peliculaRepository.findById(peliculaId).orElse(null);
                    if(pelicula != null){
                        for(ActuacionEntity act : pelicula.getActuacionList()){
                            act.setPersonaId(persona);

                            persona.getActuacionList().add(act);
                        }
                    }
                }
            }

        this.personaRepository.save(persona);
        return "redirect:/editor/actores";
    }

    @PostMapping("actores/borrar")
    public String doBorrarActor(@RequestParam("id") Integer id){
        for(ActuacionEntity a : this.actuacionRepository.findAll()){
            if(a.getPersonaid().getId() == id){
                this.actuacionRepository.delete(a);
            }
        }
        this.personaRepository.deleteById(id);
        return "redirect:/editor/actores";
    }
}
