package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.GeneroPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GeneroPersonaRepository extends JpaRepository<GeneroPersonaEntity, Integer> {
    @Query("Select g from GeneroPersonaEntity g where g.generopersonanombre = :trim")
    GeneroPersonaEntity findByNombre(String trim);
}
