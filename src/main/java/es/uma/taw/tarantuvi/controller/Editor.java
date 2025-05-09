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
import java.util.*;
import java.util.stream.Collectors;

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
    public String doEditarPelicula(@RequestParam(value = "id", defaultValue = "-1") Integer id,
                                   Model model) {
        // 1. Obtener la entidad y convertir a DTO
        PeliculaEntity entidad = peliculaRepository.findById(id)
                .orElse(new PeliculaEntity());
        Pelicula dtoPelicula = entidad.toDto();
        dtoPelicula.setId(entidad.getId());

        // 2. Preparar la lista de actuaciones: arrancar con las ya asignadas
        List<ActuacionEntity> actuacionesFiltradas = new ArrayList<>();
        if (entidad.getActuacionList() != null) {
            actuacionesFiltradas.addAll(entidad.getActuacionList());
        }
        // Construir conjunto de claves de las ya añadidas
        Set<String> clavesAct = actuacionesFiltradas.stream()
                .map(a -> a.getPersonaid().getId() + "|" + a.getPersonaje())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        // Añadir luego las que falten
        for (ActuacionEntity a : actuacionRepository.findAll()) {
            String clave = a.getPersonaid().getId() + "|" + a.getPersonaje();
            if (!clavesAct.contains(clave)) {
                clavesAct.add(clave);
                actuacionesFiltradas.add(a);
            }
        }

        // 3. Preparar la lista de crew: igual, empezando por los ya asignados
        List<TrabajoEntity> crewFiltrado = new ArrayList<>();
        if (entidad.getTrabajoList() != null) {
            crewFiltrado.addAll(entidad.getTrabajoList());
        }
        Set<String> clavesTrab = crewFiltrado.stream()
                .map(t -> t.getPersonaid().getId() + "|" + t.getTrabajonombre())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (TrabajoEntity t : trabajoRepository.findAll()) {
            String clave = t.getPersonaid().getId() + "|" + t.getTrabajonombre();
            if (!clavesTrab.contains(clave)) {
                clavesTrab.add(clave);
                crewFiltrado.add(t);
            }
        }

        // 4. Otras colecciones sin cambios
        List<ProductoraEntity>     productoras  = productoraRepository.findAll();
        List<IdiomaHabladoEntity>  idiomas      = idiomaHabladoRepository.findAll();
        List<GeneroPeliculaEntity> generos      = generoPeliculaRepository.findAll();
        List<PaisRodajeEntity>     paisesRodaje = paisRodajeRepository.findAll();

        // 5. Enviar al modelo
        model.addAttribute("pelicula",     dtoPelicula);
        model.addAttribute("actuaciones",  actuacionesFiltradas);
        model.addAttribute("crewList",     crewFiltrado);
        model.addAttribute("productoras",  productoras);
        model.addAttribute("idiomas",      idiomas);
        model.addAttribute("generos",      generos);
        model.addAttribute("paisesRodaje", paisesRodaje);

        return "Editor/pelicula";
    }

    @PostMapping("/peliculas/confirmarCambios")
    public String doConfirmarCambiosPelicula(@ModelAttribute Pelicula dtoPelicula, Model model) {
        Integer id = dtoPelicula.getId();
        PeliculaEntity pelicula = peliculaRepository.findById(id)
                .orElse(new PeliculaEntity());

        // Asignar valores simples
        pelicula.setTitulooriginal(dtoPelicula.getTitulooriginal());
        pelicula.setFechaestreno(LocalDate.parse(dtoPelicula.getFecha()));
        pelicula.setDuracion(Integer.parseInt(dtoPelicula.getDuracion().trim()));
        pelicula.setDescripcion(dtoPelicula.getDescripcion());
        pelicula.setRecaudacion(BigDecimal.valueOf(
                Long.parseLong(dtoPelicula.getRecaudacion().trim())));
        pelicula.setEstado(dtoPelicula.getEstado());
        pelicula.setPresupuesto(BigDecimal.valueOf(
                Long.parseLong(dtoPelicula.getPresupuesto().trim())));
        pelicula.setUrlcaratula(dtoPelicula.getUrlcaratula());
        pelicula.setPaginaweb(dtoPelicula.getPaginaweb());
        pelicula.setEslogan(dtoPelicula.getEslogan());

        // Otros listados: vacío (volveré a poblarlos íntegros)
        pelicula.setGeneroPeliculaList(new ArrayList<>());
        pelicula.setProductoraList(new ArrayList<>());
        pelicula.setPaisRodajeList(new ArrayList<>());
        pelicula.setIdiomaHabladoList(new ArrayList<>());

        // ——————————————
        // CAST: sólo CLONA si no existe ya persona+personaje
        List<ActuacionEntity> actuales = pelicula.getActuacionList();
        if (actuales == null) {
            actuales = new ArrayList<>();
            pelicula.setActuacionList(actuales);
        }
        if (dtoPelicula.getCast() != null) {
            for (Integer castId : dtoPelicula.getCast()) {
                ActuacionEntity original = actuacionRepository.findById(castId)
                        .orElse(null);
                if (original == null) continue;

                boolean yaExiste = actuales.stream().anyMatch(a ->
                        a.getPersonaid().getId().equals(original.getPersonaid().getId())
                                && a.getPersonaje().equals(original.getPersonaje())
                );
                if (yaExiste) continue;

                ActuacionEntity copia = new ActuacionEntity();
                copia.setPersonaId(original.getPersonaid());
                copia.setGeneropersonaid(original.getGeneropersonaid());
                copia.setPersonaje(original.getPersonaje());
                copia.setOrden(original.getOrden());
                copia.setPeliculaId(pelicula);

                actuacionRepository.save(copia);
                actuales.add(copia);
            }
        }

        // ——————————————
        // CREW: idem, sólo CLONA si no existe persona+trabajonombre
        List<TrabajoEntity> crewActual = pelicula.getTrabajoList();
        if (crewActual == null) {
            crewActual = new ArrayList<>();
            pelicula.setTrabajoList(crewActual);
        }
        if (dtoPelicula.getCrew() != null) {
            for (Integer crewId : dtoPelicula.getCrew()) {
                TrabajoEntity original = trabajoRepository.findById(crewId)
                        .orElse(null);
                if (original == null) continue;

                boolean yaExiste = crewActual.stream().anyMatch(t ->
                        t.getPersonaid().getId().equals(original.getPersonaid().getId())
                                && t.getTrabajonombre().equals(original.getTrabajonombre())
                );
                if (yaExiste) continue;

                TrabajoEntity copia = new TrabajoEntity();
                copia.setPersonaId(original.getPersonaid());
                copia.setTrabajonombre(original.getTrabajonombre());
                copia.setDepartamentoid(original.getDepartamentoid());
                copia.setPeliculaId(pelicula);

                trabajoRepository.save(copia);
                crewActual.add(copia);
            }
        }

        // ——————————————
        // Resto de relaciones (idiomas, productoras, paises, géneros)
        if (dtoPelicula.getIdiomas() != null) {
            for (Integer idiomaId : dtoPelicula.getIdiomas()) {
                idiomaHabladoRepository.findById(idiomaId)
                        .ifPresent(i -> pelicula.getIdiomaHabladoList().add(i));
            }
        }
        if (dtoPelicula.getProductoras() != null) {
            for (Integer pid : dtoPelicula.getProductoras()) {
                productoraRepository.findById(pid)
                        .ifPresent(p -> pelicula.getProductoraList().add(p));
            }
        }
        if (dtoPelicula.getPaisesRodaje() != null) {
            for (Integer pid : dtoPelicula.getPaisesRodaje()) {
                paisRodajeRepository.findById(pid)
                        .ifPresent(p -> pelicula.getPaisRodajeList().add(p));
            }
        }
        if (dtoPelicula.getGeneros() != null) {
            for (Integer gid : dtoPelicula.getGeneros()) {
                generoPeliculaRepository.findById(gid)
                        .ifPresent(g -> pelicula.getGeneroPeliculaList().add(g));
            }
        }

        // Guardar la película con sus relaciones actualizadas
        peliculaRepository.save(pelicula);
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
