/**
 * @author Jesús Repiso
 * @author Alejandro Cueto
 * @author Pablo Gámez
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.GeneroPeliculaRepository;
import es.uma.taw.tarantuvi.dto.GeneroPelicula;
import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroPeliculaService extends DTOService<GeneroPelicula, GeneroPeliculaEntity>{

    @Autowired
    private GeneroPeliculaRepository generoPeliculaRepository;

    public List<GeneroPelicula> listarGenerosPeliculas () {
        List<GeneroPeliculaEntity> entities = this.generoPeliculaRepository.findAll();
        return this.entity2DTO(entities);
    }

    public GeneroPelicula buscarGeneroPelicula(Integer id) {
        GeneroPeliculaEntity genero = this.generoPeliculaRepository.findById(id).orElse(new GeneroPeliculaEntity());
        if (genero != null) {
            return genero.toDto();
        } else {
            return new GeneroPelicula();
        }
    }

    public void guardarGeneroPelicula(GeneroPelicula dtoGeneroPelicula) {
        GeneroPeliculaEntity generoPelicula;

        if (dtoGeneroPelicula.getId() != null) {
            generoPelicula = this.generoPeliculaRepository.findById(dtoGeneroPelicula.getId()).orElse(new GeneroPeliculaEntity());
        } else {
            generoPelicula = new GeneroPeliculaEntity();
        }

        generoPelicula.setGeneronombre(dtoGeneroPelicula.getGeneronombre());
        this.generoPeliculaRepository.save(generoPelicula);
    }

    public void borrarGeneroPelicula(Integer id) {
        this.generoPeliculaRepository.deleteById(id);
    }

    public List<Object[]> obtenerNotaMediaPorGeneroPelicula() {
        return this.generoPeliculaRepository.findNotaMediaPorGenero();
    }

    public List<Object[]> obtenerNumeroValoracionesPorGeneroPelicula() {
        return this.generoPeliculaRepository.findNumeroValoracionesPorGenero();
    }
}
