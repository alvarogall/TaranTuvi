/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.NacionalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NacionalidadRepository extends JpaRepository<NacionalidadEntity, Integer> {
    @Query("Select n from NacionalidadEntity n where n.nacionalidadnombre = :trim")
    NacionalidadEntity findByNombre(String trim);
}
