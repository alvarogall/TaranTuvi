/**
 * @author Álvaro Gallardo
 * @author Máximo Prados
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ListaPeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListaPeliculaRepository extends JpaRepository<ListaPeliculaEntity, Integer> {

    @Query("select v from ListaPeliculaEntity v where v.usuarioid.id = :idUsuario")
    public List<ListaPeliculaEntity> findListasByUsuarioid(@Param("idUsuario") Integer idUsuario);
}
