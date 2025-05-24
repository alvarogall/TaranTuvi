/*
 * Users: jesus, Alvaro
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.*;
import es.uma.taw.tarantuvi.dto.FiltroPelicula;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.dto.Valoracion;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.TrabajoEntity;
import es.uma.taw.tarantuvi.entity.UsuarioEntity;
import es.uma.taw.tarantuvi.entity.ValoracionEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @Autowired
    protected ValoracionRepository valoracionRepository;

    @Autowired
    protected UsuarioRepository usuarioRepository;

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

    public List<Object[]> buscarPeliculasPorGeneroIdioma(Integer generoID, Integer idiomaID, String ordenCampo, String ordenTipo){
        List<Object[]> peliculas = this.peliculaRepository.findPeliculasByFiltros(generoID, idiomaID);

        if (ordenCampo != null && ordenTipo != null) {
            Comparator<Object[]> comparator = switch (ordenCampo) {
                case "fecha" -> Comparator.comparing(a -> ((PeliculaEntity) a[0]).getFechaestreno());
                case "duracion" -> Comparator.comparing(a -> ((PeliculaEntity) a[0]).getDuracion());
                case "nota" -> Comparator.comparing(a -> (a[1] != null ? (Double) a[1] : 0.0));
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(ordenTipo)) {
                    comparator = comparator.reversed();
                }
                peliculas.sort(comparator);
            }
        }

        return peliculas;
    }

    public List<Object[]> obtenerMejorPelicula() {
        return peliculaRepository.findPeliculasOrdenadasPorNotaYValoraciones()
                .stream()
                .limit(1)
                .toList();
    }

    public List<Object[]> obtener10MejoresPelicula() {
        return peliculaRepository.findPeliculasOrdenadasPorNotaYValoraciones()
                .stream()
                .limit(10)
                .toList();
    }

    public void valorarPelicula(Integer peliculaId, 
                                Integer usuarioId, 
                                Integer nota) {
        ValoracionEntity valoracion = valoracionRepository.obtenerValoracionPeliculaUsuario(peliculaId, usuarioId);
        PeliculaEntity pelicula = peliculaRepository.findById(peliculaId).orElse(null);

        boolean esNuevaValoracion = false;

        if (valoracion != null) {
            valoracion.setNota(nota);
            valoracionRepository.save(valoracion);
        } else {
            valoracion = new ValoracionEntity();
            UsuarioEntity usuario = usuarioRepository.findById(usuarioId).orElse(null);

            valoracion.setUsuarioid(usuario);
            valoracion.setPeliculaid(pelicula);
            valoracion.setNota(nota);

            valoracionRepository.save(valoracion);
            esNuevaValoracion = true;
        }

        if (esNuevaValoracion && pelicula != null) {
            pelicula.setVotos(pelicula.getVotos() != null ? pelicula.getVotos() + 1 : 1);
        }

        Double media = valoracionRepository.calcularNotaMediaPorPelicula(peliculaId);
        
        if (pelicula != null && media != null) {
            pelicula.setNota(BigDecimal.valueOf(media));
            peliculaRepository.save(pelicula);
        }
    }

    public Valoracion obtenerValoracionUsuario(Integer peliculaId,
                                               Integer usuarioId) {
        ValoracionEntity valoracion = valoracionRepository.obtenerValoracionPeliculaUsuario(peliculaId, usuarioId);

        return valoracion != null ? valoracion.toDto() : new Valoracion();
    }

    public List<Pelicula> filtrarPeliculas(FiltroPelicula filtro) {
        List<Integer> generos = (filtro.getGeneros() == null || filtro.getGeneros().isEmpty()) ? null : filtro.getGeneros();
        List<Integer> productoras = (filtro.getProductoras() == null || filtro.getProductoras().isEmpty()) ? null : filtro.getProductoras();
        List<Integer> actores = (filtro.getActores() == null || filtro.getActores().isEmpty()) ? null : filtro.getActores();
        List<PeliculaEntity> entities = peliculaRepository.filtrarPeliculas(
            filtro.getNombre(),
            filtro.getValoracion(),
            filtro.getAnio(),
            generos,
            productoras,
            actores
        );
        return this.entity2DTO(entities);
    }

    public Pelicula obtenerPeliculaMejorValorada() {
    List<PeliculaEntity> entities = peliculaRepository.findPeliculasMejorValoradas();
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0).toDto();
        }
        return null;
    }
}
