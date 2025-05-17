/*
User: jesus
*/

package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.dto.ListaPelicula;
import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.entity.*;
import es.uma.taw.tarantuvi.dto.Pelicula;
import jakarta.servlet.http.HttpSession;
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
    protected ListaPeliculaRepository listaPeliculaRepository;
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

        PeliculaEntity entidad = peliculaRepository.findById(id).orElse(new PeliculaEntity());
        Pelicula dtoPelicula = entidad.toDto();
        dtoPelicula.setId(entidad.getId());

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
    public String doConfirmarCambiosPelicula(@ModelAttribute Pelicula dtoPelicula) {
        Integer id = dtoPelicula.getId() == null ? -1 : dtoPelicula.getId();
        PeliculaEntity pelicula = peliculaRepository.findById(id).orElse(new PeliculaEntity());

        pelicula.setTitulooriginal(dtoPelicula.getTitulooriginal());
        pelicula.setFechaestreno(LocalDate.parse(dtoPelicula.getFecha()));
        pelicula.setDuracion(Integer.parseInt(dtoPelicula.getDuracion().trim()));
        pelicula.setDescripcion(dtoPelicula.getDescripcion());
        pelicula.setRecaudacion(new BigDecimal(dtoPelicula.getRecaudacion().trim()));
        pelicula.setPresupuesto(new BigDecimal(dtoPelicula.getPresupuesto().trim()));
        pelicula.setEstado(dtoPelicula.getEstado());
        pelicula.setUrlcaratula(dtoPelicula.getUrlcaratula());
        pelicula.setPaginaweb(dtoPelicula.getPaginaweb());
        pelicula.setEslogan(dtoPelicula.getEslogan());

        pelicula = peliculaRepository.saveAndFlush(pelicula);

        // CAST
        List<ActuacionEntity> castActual = new ArrayList<>(pelicula.getActuacionList());
        List<Integer> castSeleccionado = dtoPelicula.getCast() != null ? dtoPelicula.getCast() : Collections.emptyList();

        // Eliminar los que ya no están marcados
        for (ActuacionEntity act : castActual) {
            if (!castSeleccionado.contains(act.getId())) {
                actuacionRepository.delete(act);
                pelicula.getActuacionList().remove(act);
            }
        }
        // Añadir nuevos seleccionados
        for (Integer castId : castSeleccionado) {
            boolean existe = castActual.stream().anyMatch(a -> a.getId().equals(castId));
            if (!existe) {
                ActuacionEntity original = actuacionRepository.findById(castId).orElse(null);
                if (original != null) {
                    ActuacionEntity copia = new ActuacionEntity();
                    copia.setPersonaId(original.getPersonaid());
                    copia.setGeneropersonaid(original.getGeneropersonaid());
                    copia.setPersonaje(original.getPersonaje());
                    copia.setOrden(original.getOrden());
                    copia.setPeliculaId(pelicula);
                    actuacionRepository.save(copia);
                    pelicula.getActuacionList().add(copia);
                }
            }
        }

        // CREW
        List<TrabajoEntity> crewActual = new ArrayList<>(pelicula.getTrabajoList());
        List<Integer> crewSeleccionado = dtoPelicula.getCrew() != null ? dtoPelicula.getCrew() : Collections.emptyList();

        // Eliminar los no marcados
        for (TrabajoEntity t : crewActual) {
            if (!crewSeleccionado.contains(t.getId())) {
                trabajoRepository.delete(t);
                pelicula.getTrabajoList().remove(t);
            }
        }
        // Añadir nuevos
        for (Integer crewId : crewSeleccionado) {
            boolean existe = crewActual.stream().anyMatch(t -> t.getId().equals(crewId));
            if (!existe) {
                TrabajoEntity original = trabajoRepository.findById(crewId).orElse(null);
                if (original != null) {
                    TrabajoEntity copia = new TrabajoEntity();
                    copia.setPersonaId(original.getPersonaid());
                    copia.setDepartamentoid(original.getDepartamentoid());
                    copia.setTrabajonombre(original.getTrabajonombre());
                    copia.setPeliculaId(pelicula);
                    trabajoRepository.save(copia);
                    pelicula.getTrabajoList().add(copia);
                }
            }
        }

        // Idiomas
        pelicula.getIdiomaHabladoList().clear();
        if (dtoPelicula.getIdiomas() != null) {
            for (Integer idiomaId : dtoPelicula.getIdiomas()) {
                idiomaHabladoRepository.findById(idiomaId).ifPresent(pelicula.getIdiomaHabladoList()::add);
            }
        }

        // Productoras
        pelicula.getProductoraList().clear();
        if (dtoPelicula.getProductoras() != null) {
            for (Integer prodId : dtoPelicula.getProductoras()) {
                productoraRepository.findById(prodId).ifPresent(pelicula.getProductoraList()::add);
            }
        }

        // Países de rodaje
        pelicula.getPaisRodajeList().clear();
        if (dtoPelicula.getPaisesRodaje() != null) {
            for (Integer paisId : dtoPelicula.getPaisesRodaje()) {
                paisRodajeRepository.findById(paisId).ifPresent(pelicula.getPaisRodajeList()::add);
            }
        }
        // Géneros
        pelicula.getGeneroPeliculaList().clear();
        if (dtoPelicula.getGeneros() != null) {
            for (Integer genId : dtoPelicula.getGeneros()) {
                generoPeliculaRepository.findById(genId).ifPresent(pelicula.getGeneroPeliculaList()::add);
            }
        }

        peliculaRepository.save(pelicula);

        return "redirect:/editor/peliculas";
    }

    @PostMapping("peliculas/borrar")
    public String doBorrarPelicula(@RequestParam("id") Integer id){
        PeliculaEntity pelicula = this.peliculaRepository.findById(id).orElse(null);
        if(pelicula != null) {
            for(ActuacionEntity a : pelicula.getActuacionList()) {
                this.actuacionRepository.deleteById(a.getId());
            }
            for(TrabajoEntity t : pelicula.getTrabajoList()) {
                this.trabajoRepository.deleteById(t.getId());
            }
        }
        this.peliculaRepository.deleteById(id);
        return "redirect:/editor/peliculas";
    }

    @PostMapping("/actores/editar")
    public String doEditarActor(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        PersonaEntity persona = personaRepository.findById(id).orElse(new PersonaEntity());
        Actor dtoActor = persona.toDto();
        dtoActor.setId(persona.getId());

        model.addAttribute("actor", dtoActor);
        model.addAttribute("peliculas", peliculaRepository.findAll());
        model.addAttribute("actuaciones", actuacionRepository.findAll());
        model.addAttribute("generosPeliculas", generoPeliculaRepository.findAll());
        model.addAttribute("generosPersona", generoPersonaRepository.findAll());
        model.addAttribute("nacionalidades", nacionalidadRepository.findAll());

        return "Editor/actor";
    }

    @PostMapping("/actores/confirmarCambios")
    public String doConfirmarCambiosActores(@ModelAttribute Actor dtoActor) {
        // 1. Cargar/crear Persona y actualizar campos básicos
        Integer id = dtoActor.getId() == null ? -1 : dtoActor.getId();
        PersonaEntity persona = personaRepository.findById(id).orElse(new PersonaEntity());
        persona.setUrlfoto(dtoActor.getUrlfoto());
        persona.setNombre(dtoActor.getNombre());
        persona.setGeneropersonaid(generoPersonaRepository.findById(dtoActor.getGenero()).orElse(null));
        persona.setNacionalidadid(nacionalidadRepository.findById(dtoActor.getNacionalidad()).orElse(null));
        persona = personaRepository.save(persona);

        List<ActuacionEntity> actuales = persona.getActuacionList() != null ? persona.getActuacionList() : new ArrayList<>();

        List<Integer> peliculasSeleccionadas = dtoActor.getPeliculas() != null ? dtoActor.getPeliculas() : new ArrayList<>();
        List<Integer> personajesSeleccionados = dtoActor.getActuaciones() != null ? dtoActor.getActuaciones() : new ArrayList<>();

        // GESTIÓN DE PELÍCULAS
        // Eliminar actuaciones de películas que ya no están seleccionadas
        for (ActuacionEntity act : actuales) {
            Integer peliId = act.getPeliculaid() != null
                    ? act.getPeliculaid().getId()
                    : null;
            if (peliId != null && !peliculasSeleccionadas.contains(peliId)) {
                actuacionRepository.delete(act);
            }
        }
        // Crear actuaciones nuevas para cada película seleccionada si no hay ya una actuación similar agregada al actor
        for (Integer peliculaId : peliculasSeleccionadas) {
            boolean exists = actuales.stream()
                    .anyMatch(a -> a.getPeliculaid() != null
                            && a.getPeliculaid().getId().equals(peliculaId));
            if (!exists) {
                PeliculaEntity peli = peliculaRepository.findById(peliculaId).orElse(null);
                if (peli != null) {
                    ActuacionEntity nueva = new ActuacionEntity();
                    nueva.setPersonaId(persona);
                    nueva.setPeliculaId(peli);
                    nueva.setGeneropersonaid(persona.getGeneropersonaid());
                    actuacionRepository.save(nueva);
                }
            }
        }

        // GESTIÓN DE PERSONAJES “SUELTOS”
        // Eliminar personajes sueltos que el usuario haya desmarcado
        for (ActuacionEntity act : actuales) {
            Integer peliId = act.getPeliculaid() != null
                    ? act.getPeliculaid().getId()
                    : null;
            Integer actId  = act.getId();
            if (peliId == null && !personajesSeleccionados.contains(actId)) {
                actuacionRepository.delete(act);
            }
        }
        // Crear actuaciones nuevas para cada personaje seleccionado si no hay ya una actuación similar agregada al actor
        for (Integer actSeleccionadaId : personajesSeleccionados) {
            boolean exists = actuales.stream()
                    .anyMatch(a -> a.getId().equals(actSeleccionadaId));
            if (!exists) {
                ActuacionEntity a = actuacionRepository.findById(actSeleccionadaId).orElse(null);
                if (a != null) {
                    ActuacionEntity nueva = new ActuacionEntity();
                    nueva.setPersonaId(persona);
                    nueva.setPeliculaId(null);
                    nueva.setGeneropersonaid(persona.getGeneropersonaid());
                    nueva.setPersonaje(a.getPersonaje());
                    actuacionRepository.save(nueva);
                }
            }
        }

        // CAMPO “PERSONAJE NUEVO”
        String personajeNuevo = dtoActor.getNombrePersonaje() != null ? dtoActor.getNombrePersonaje().trim() : "";
        if (!personajeNuevo.isEmpty()) {
            boolean yaExiste = actuales.stream()
                    .filter(a -> a.getPeliculaid() == null)
                    .anyMatch(a -> personajeNuevo.equalsIgnoreCase(a.getPersonaje()));
            if (!yaExiste) {
                ActuacionEntity suelto = new ActuacionEntity();
                suelto.setPersonaId(persona);
                suelto.setPeliculaId(null);
                suelto.setGeneropersonaid(persona.getGeneropersonaid());
                suelto.setPersonaje(personajeNuevo);
                actuacionRepository.save(suelto);
            }
        }

        return "redirect:/editor/actores";
    }

    @PostMapping("actores/borrar")
    public String doBorrarActor(@RequestParam("id") Integer id){
        for(ActuacionEntity a : this.actuacionRepository.findAll()){
            if(Objects.equals(a.getPersonaid().getId(), id)){
                this.actuacionRepository.delete(a);
            }
        }
        this.personaRepository.deleteById(id);
        return "redirect:/editor/actores";
    }

    @GetMapping("/perfil")
    public String doPerfil(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getUsuarioId();

        List<ListaPeliculaEntity> listasPeliculas = listaPeliculaRepository.findListasByUsuarioid(idUsuario);
        model.addAttribute("listasPeliculas", listasPeliculas);
        model.addAttribute("listaPelicula", new ListaPelicula());
        model.addAttribute("peliculas",peliculaRepository.findAll());

        return "UsuarioPremium/perfilUsuarioPremium";
    }
}
