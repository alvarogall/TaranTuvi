package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dao.PersonaRepository;
import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService extends DTOService<Pelicula, PeliculaEntity> {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> listarPeliculas () {
        List<PeliculaEntity> entities = this.peliculaRepository.findAll();
        return this.entity2DTO(entities);
    }
}
