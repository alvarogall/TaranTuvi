/*
User: jesus
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
}
