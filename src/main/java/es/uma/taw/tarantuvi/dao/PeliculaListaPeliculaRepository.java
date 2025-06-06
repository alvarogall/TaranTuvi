/**
 * @author Máximo Prados
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeliculaListaPeliculaRepository extends JpaRepository<PeliculaListaPeliculaEntity, PeliculaListaPeliculaId> {


    @Query("select c.pelicula from PeliculaListaPeliculaEntity c where c.listaPelicula.id=:playlist")
    List<PeliculaEntity> findByListaId(@Param("playlist") Integer playlist);
}
