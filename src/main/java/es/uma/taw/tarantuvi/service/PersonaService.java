/**
 * @author Jesús Repiso
 * @author Pablo Gámez
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.dto.ActorResumen;
import es.uma.taw.tarantuvi.dto.FiltroActor;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersonaService extends DTOService<Actor, PersonaEntity> {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private GeneroPersonaRepository generoPersonaRepository;

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private ActuacionRepository actuacionRepository;

    public List<Actor> listarPersonas () {
        List<PersonaEntity> entities = this.personaRepository.findAll();
        return this.entity2DTO(entities);
    }

    public void borrarPersona(Integer id) {
        this.personaRepository.deleteById(id);
    }

    public Actor buscarPersona(Integer id) {
        PersonaEntity persona = this.personaRepository.findById(id).orElse(null);
        if(persona != null){
            return persona.toDto();
        }else{
            return new Actor();
        }
    }

    public void guardarActor(Actor dtoActor) {
        PersonaEntity persona;

        if (dtoActor.getId() != null) {
            persona = this.personaRepository.findById(dtoActor.getId()).orElse(new PersonaEntity());
        } else {
            persona = new PersonaEntity();
        }

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
    }

    public List<Actor> listarActores() {
        List<PersonaEntity> entities = this.personaRepository.findAllActores();
        return this.entity2DTO(entities);
    }

    public ActorResumen buscarActorConActuaciones(Integer id) {
        PersonaEntity persona = this.personaRepository.findById(id).orElse(null);

        if(persona != null) {
            ActorResumen actorResumenDto = persona.toDtoResumen();
            return actorResumenDto;
        } else {
            return new ActorResumen();
        }
    }

    public List<Actor> filtrarActores(FiltroActor filtro) {
        List<Integer> peliculas = (filtro.getPeliculas() == null || filtro.getPeliculas().isEmpty()) ? null : filtro.getPeliculas();
        List<PersonaEntity> entities = personaRepository.filtrarActores(
            filtro.getNombre(),
            filtro.getGenero(),
            filtro.getNacionalidad(),
            peliculas
        );
        return this.entity2DTO(entities);
    }
}
