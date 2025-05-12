package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Integer> {

    @Query("SELECT p FROM PeliculaEntity p ORDER BY p.fechaestreno DESC")
    List<PeliculaEntity> findPeliculasMasRecientes(Pageable pageable);

    @Query("select p from PeliculaEntity p order by p.fechaestreno desc limit 2")
    List<PeliculaEntity> obtenerNovedades();

}
