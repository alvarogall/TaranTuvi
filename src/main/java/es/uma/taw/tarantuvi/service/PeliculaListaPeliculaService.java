package es.uma.taw.tarantuvi.service;


import es.uma.taw.tarantuvi.dao.ListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dto.ListaPelicula;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.dto.PeliculaListaPelicula;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaListaPeliculaService extends DTOService<PeliculaListaPelicula, PeliculaListaPeliculaEntity>{


@Autowired
PeliculaListaPeliculaRepository peliculaListaPeliculaRepository;

@Autowired
PeliculaRepository peliculaRepository;

@Autowired
ListaPeliculaRepository listaPeliculaRepository;


public void guardar(PeliculaListaPelicula pelicula) {

    PeliculaListaPeliculaEntity peliculaListaPeliculaEntity = new PeliculaListaPeliculaEntity();
    peliculaListaPeliculaEntity.setPelicula(peliculaRepository.findById(pelicula.getPeliculaId()).get());
    peliculaListaPeliculaEntity.setListaPelicula(listaPeliculaRepository.findById(pelicula.getListaPeliculaId()).get());

    peliculaListaPeliculaRepository.save(peliculaListaPeliculaEntity);

}


public void eliminar(PeliculaListaPeliculaId id) {
    peliculaListaPeliculaRepository.deleteById(id);
}


}
