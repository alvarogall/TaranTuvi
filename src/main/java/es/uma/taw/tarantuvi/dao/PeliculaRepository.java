package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Integer> {

    @Query("SELECT p FROM PeliculaEntity p ORDER BY p.fechaestreno DESC")
    List<PeliculaEntity> findPeliculasMasRecientes(Pageable pageable);

    @Query("SELECT p FROM PeliculaEntity p " +
            "JOIN p.generoPeliculaList g " +
            "WHERE (:generoId IS NULL OR g.id = :generoId) " +
            "AND (:idiomaId IS NULL OR p.idiomaoriginalhabladoid.id = :idiomaId)")
    List<PeliculaEntity> findPeliculasByFiltros(Integer generoId, Integer idiomaId);


}
