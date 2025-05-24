/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.TrabajoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajoRepository extends JpaRepository<TrabajoEntity, Integer> {
}
