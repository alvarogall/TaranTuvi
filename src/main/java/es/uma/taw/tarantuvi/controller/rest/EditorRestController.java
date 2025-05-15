/*
User: jesus
*/

package es.uma.taw.tarantuvi.controller.rest;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/editor")
public class EditorRestController {

    @Autowired
    protected PeliculaRepository peliculaRepository;

    @Autowired
    protected PersonaRepository personaRepository;

    // GET todas las películas
    @GetMapping("/peliculas")
    public List<Pelicula> getPeliculas() {
        return peliculaRepository.findAll().stream().map(PeliculaEntity::toDto).collect(Collectors.toList());
    }

    // GET una película por ID
    @GetMapping("/peliculas/{id}")
    public Pelicula getPeliculaById(@PathVariable Integer id) {
        PeliculaEntity peli = peliculaRepository.findById(id).orElseThrow();
        return peli.toDto();
    }

    // GET todos los actores
    @GetMapping("/actores")
    public List<Actor> getActores() {
        return personaRepository.findAll().stream().map(PersonaEntity::toDto).collect(Collectors.toList());
    }

    // GET un actor por ID
    @GetMapping("/actores/{id}")
    public Actor getActorById(@PathVariable Integer id) {
        PersonaEntity persona = personaRepository.findById(id).orElseThrow();
        return persona.toDto();
    }

    // DELETE una película
    @DeleteMapping("/peliculas/{id}")
    public void deletePelicula(@PathVariable Integer id) {
        peliculaRepository.deleteById(id);
    }

    // DELETE un actor
    @DeleteMapping("/actores/{id}")
    public void deleteActor(@PathVariable Integer id) {
        personaRepository.deleteById(id);
    }
}
