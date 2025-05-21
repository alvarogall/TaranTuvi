/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ValoracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * @author Álvaro Gallardo
 */

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<ValoracionEntity, Integer> {

    @Query("SELECT V.peliculaid, AVG(V.nota) FROM ValoracionEntity V GROUP BY V.peliculaid ORDER BY AVG(V.nota) DESC")
    List<Object[]> getPeliculaConMayorNotaMedia();

    @Query("select v from ValoracionEntity v where v.peliculaid.id = :peliculaId and v.usuarioid.id = :usuarioId")
    ValoracionEntity obtenerValoracionPeliculaUsuario(@Param("peliculaId") Integer peliculaId,
                                                      @Param("usuarioId") Integer usuarioId);

    @Query("select avg(v.nota) from ValoracionEntity v where v.peliculaid.id = :peliculaId")
    Double calcularNotaMediaPorPelicula(@Param("peliculaId") Integer peliculaId);
}
