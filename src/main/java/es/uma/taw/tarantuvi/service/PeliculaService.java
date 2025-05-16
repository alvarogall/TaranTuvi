/*
User: jesus
*/

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.TrabajoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeliculaService extends DTOService<Pelicula, PeliculaEntity> {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private ActuacionRepository actuacionRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private IdiomaHabladoRepository idiomaHabladoRepository;

    @Autowired
    private ProductoraRepository productoraRepository;

    @Autowired
    private PaisRodajeRepository paisRodajeRepository;

    @Autowired
    private GeneroPeliculaRepository generoPeliculaRepository;

    public List<Pelicula> listarPeliculas () {
        List<PeliculaEntity> entities = this.peliculaRepository.findAll();
        return this.entity2DTO(entities);
    }
    
    public void borrarPelicula(Integer id) {
        this.peliculaRepository.deleteById(id);
    }
    
    public Pelicula buscarPelicula(Integer id) {
        PeliculaEntity pelicula = this.peliculaRepository.findById(id).orElse(null);
        if(pelicula != null){
            return pelicula.toDto();
        }else{
            return new Pelicula();
        }
    }
    
    public void guardarPelicula(Pelicula p) {
        PeliculaEntity pelicula;

        if (p.getId() != null) {
            pelicula = this.peliculaRepository.findById(p.getId()).orElse(new PeliculaEntity());
        } else {
            pelicula = new PeliculaEntity();
        }

        pelicula.setTitulooriginal(p.getTitulooriginal());
        pelicula.setFechaestreno(LocalDate.parse(p.getFecha()));
        pelicula.setDuracion(Integer.parseInt(p.getDuracion().trim()));
        pelicula.setDescripcion(p.getDescripcion());
        pelicula.setRecaudacion(new BigDecimal(p.getRecaudacion().trim()));
        pelicula.setPresupuesto(new BigDecimal(p.getPresupuesto().trim()));
        pelicula.setEstado(p.getEstado());
        pelicula.setUrlcaratula(p.getUrlcaratula());
        pelicula.setPaginaweb(p.getPaginaweb());
        pelicula.setEslogan(p.getEslogan());

        pelicula = peliculaRepository.save(pelicula);

        // CAST
        List<ActuacionEntity> castActual = pelicula.getActuacionList() != null ? new ArrayList<>(pelicula.getActuacionList()) : new ArrayList<>();
        List<Integer> castSeleccionado = p.getCast() != null ? p.getCast() : Collections.emptyList();

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
                    if(pelicula.getActuacionList() == null){
                        pelicula.setActuacionList(new ArrayList<>());
                    }
                    pelicula.getActuacionList().add(copia);
                }
            }
        }

        // CREW
        List<TrabajoEntity> crewActual = pelicula.getTrabajoList() != null ? new ArrayList<>(pelicula.getTrabajoList()) : new ArrayList<>();
        List<Integer> crewSeleccionado = p.getCrew() != null ? p.getCrew() : Collections.emptyList();

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
                    if(pelicula.getTrabajoList() == null){
                        pelicula.setTrabajoList(new ArrayList<>());
                    }
                    pelicula.getTrabajoList().add(copia);
                }
            }
        }

        // Idiomas
        if(pelicula.getIdiomaHabladoList() == null){
            pelicula.setIdiomaHabladoList(new ArrayList<>());
        }
        pelicula.getIdiomaHabladoList().clear();
        if (p.getIdiomas() != null) {
            for (Integer idiomaId : p.getIdiomas()) {
                idiomaHabladoRepository.findById(idiomaId).ifPresent(pelicula.getIdiomaHabladoList()::add);
            }
        }

        // Productoras
        if(pelicula.getProductoraList() == null){
            pelicula.setProductoraList(new ArrayList<>());
        }
        pelicula.getProductoraList().clear();
        if (p.getProductoras() != null) {
            for (Integer prodId : p.getProductoras()) {
                productoraRepository.findById(prodId).ifPresent(pelicula.getProductoraList()::add);
            }
        }

        // Países de rodaje
        if(pelicula.getPaisRodajeList() == null){
            pelicula.setPaisRodajeList(new ArrayList<>());
        }
        pelicula.getPaisRodajeList().clear();
        if (p.getPaisesRodaje() != null) {
            for (Integer paisId : p.getPaisesRodaje()) {
                paisRodajeRepository.findById(paisId).ifPresent(pelicula.getPaisRodajeList()::add);
            }
        }
        // Géneros
        if(pelicula.getGeneroPeliculaList() == null){
            pelicula.setGeneroPeliculaList(new ArrayList<>());
        }
        pelicula.getGeneroPeliculaList().clear();
        if (p.getGeneros() != null) {
            for (Integer genId : p.getGeneros()) {
                generoPeliculaRepository.findById(genId).ifPresent(pelicula.getGeneroPeliculaList()::add);
            }
        }

        peliculaRepository.save(pelicula);
    }
}
