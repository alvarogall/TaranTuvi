package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.IdiomaHabladoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IdiomaHabladoRepository extends JpaRepository<IdiomaHabladoEntity, Integer> {
    @Query("Select i from IdiomaHabladoEntity i where i.idiomahabladonombre = :trim")
    IdiomaHabladoEntity findByNombre(String trim);
}
