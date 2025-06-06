package es.uma.taw.tarantuvi.service;


import es.uma.taw.tarantuvi.dao.ListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.UsuarioRepository;
import es.uma.taw.tarantuvi.dto.ListaPelicula;
import es.uma.taw.tarantuvi.entity.ListaPeliculaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaPeliculaService extends DTOService<ListaPelicula, ListaPeliculaEntity> {

    @Autowired
    private ListaPeliculaRepository listaPeliculaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public ListaPelicula findById(Integer id){

        return listaPeliculaRepository.findById(id).get().toDto();

    }


    public List<ListaPelicula> findListasByUsuarioid(Integer idUsuario){
        List<ListaPeliculaEntity> entidades = listaPeliculaRepository.findListasByUsuarioid(idUsuario);

        return this.entity2DTO(entidades);
    }


    public void guardar(ListaPelicula listaPelicula){
        ListaPeliculaEntity listaPeliculaEntity = new ListaPeliculaEntity();
        listaPeliculaEntity.setUsuarioid(usuarioRepository.getReferenceById(listaPelicula.getUsuarioId()));
        listaPeliculaEntity.setListapeliculanombre(listaPelicula.getListaPeliculaNombre());
        listaPeliculaEntity.setPeliculaList(new ArrayList<>());
        listaPeliculaRepository.save(listaPeliculaEntity);
    }

    public void deleteById(Integer id){
        listaPeliculaRepository.deleteById(id);
    }

}
