/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 * @author Alejandro Cueto
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PaisRodajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaisRodajeRepository extends JpaRepository<PaisRodajeEntity, Integer> {
    @Query("Select p from PaisRodajeEntity p where p.paisrodajenombre = :trim")
    PaisRodajeEntity findByNombre(String trim);

    @Query("SELECT COUNT(pl) FROM PaisRodajeEntity P JOIN P.peliculaList pl")
    Integer countPeliculasAsociadasPaisRodaje();
}
