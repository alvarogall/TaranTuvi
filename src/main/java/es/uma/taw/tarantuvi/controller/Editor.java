package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    private TrabajoRepository trabajoRepository;
    @Autowired
    private PaisRodajeRepository paisRodajeRepository;

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
    public String doEditarPelicula(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
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

    @PostMapping("peliculas/confirmarCambios")
    public String doConfirmarCambios(
            @RequestParam(value = "id", defaultValue = "-1") Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("fecha") String fecha,
            @RequestParam("duracion") String duracion,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("crew") String crew,
            @RequestParam("cast") String cast,
            @RequestParam("productoras") String productoras,
            @RequestParam("paisesRodaje") String paisesRodaje,
            @RequestParam("idiomas") String idiomas,
            @RequestParam("generos") String generos,
            @RequestParam("recaudacion") String recaudacion,
            @RequestParam("estado") String estado,
            @RequestParam("presupuesto") String presupuesto,
            @RequestParam("url") String url,
            @RequestParam("paginaweb") String paginaweb,
            @RequestParam("eslogan") String eslogan,
            Model model) {

        PeliculaEntity pelicula = peliculaRepository.findById(id).orElse(new PeliculaEntity());

        pelicula.setTitulooriginal(nombre);
        pelicula.setFechaestreno(LocalDate.parse(fecha));
        pelicula.setDuracion(Integer.parseInt(duracion.trim()));
        pelicula.setDescripcion(descripcion);
        pelicula.setRecaudacion(BigDecimal.valueOf(Long.parseLong(recaudacion.trim())));
        pelicula.setEstado(estado);
        pelicula.setPresupuesto(BigDecimal.valueOf(Long.parseLong(presupuesto.trim())));
        pelicula.setUrlcaratula(url);
        pelicula.setPaginaweb(paginaweb);
        pelicula.setEslogan(eslogan);

        // Inicializar o limpiar listas
        if (pelicula.getActuacionList() != null) {
            pelicula.getActuacionList().clear();
        } else {
            pelicula.setActuacionList(new ArrayList<>());
        }

        if (pelicula.getTrabajoList() != null) {
            pelicula.getTrabajoList().clear();
        } else {
            pelicula.setTrabajoList(new ArrayList<>());
        }

        if (pelicula.getGeneroPeliculaList() != null) {
            pelicula.getGeneroPeliculaList().clear();
        } else {
            pelicula.setGeneroPeliculaList(new ArrayList<>());
        }

        // Añadir actores
        for (String linea : cast.split("\n")) {
            String[] partes = linea.split(" - ");
            if (partes.length == 2) {
                String nombrePersona = partes[0].trim();
                String personaje = partes[1].trim();

                PersonaEntity persona = personaRepository.findByNombre(nombrePersona);
                if (persona != null) {
                    ActuacionEntity actuacion = new ActuacionEntity();
                    actuacion.setPeliculaId(pelicula);
                    actuacion.setPersonaId(persona);
                    actuacion.setPersonaje(personaje);
                    pelicula.getActuacionList().add(actuacion);
                }
            }
        }

        // Añadir trabajos (crew)
        for (String linea : crew.split("\n")) {
            String[] partes = linea.split(" - ");
            if (partes.length == 2) {
                String nombrePersona = partes[0].trim();
                String trabajoNombre = partes[1].trim();

                PersonaEntity persona = personaRepository.findByNombre(nombrePersona);
                if (persona != null) {
                    TrabajoEntity trabajo = new TrabajoEntity();
                    trabajo.setPeliculaId(pelicula);
                    trabajo.setPersonaId(persona);
                    trabajo.setTrabajonombre(trabajoNombre);
                    pelicula.getTrabajoList().add(trabajo);
                }
            }
        }

        // Añadir géneros
        for (String linea : generos.split("\n")) {
            String generoNombre = linea.trim();
            if (!generoNombre.isEmpty()) {
                GeneroPeliculaEntity genero = generoPeliculaRepository.findByNombre(generoNombre);
                if (genero != null && !pelicula.getGeneroPeliculaList().contains(genero)) {
                    pelicula.getGeneroPeliculaList().add(genero);
                }
            }
        }

        peliculaRepository.save(pelicula);

        return "redirect:/peliculas";
    }

    @PostMapping("peliculas/borrar")
    public String doBorrar(@RequestParam("id") Integer id){
        this.peliculaRepository.deleteById(id);
        return "redirect:/peliculas";
    }

}
