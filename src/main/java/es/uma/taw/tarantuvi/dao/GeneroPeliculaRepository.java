package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GeneroPeliculaRepository extends JpaRepository<GeneroPeliculaEntity, Integer> {
    @Query("Select g from GeneroPeliculaEntity g where g.generonombre = :trim")
    GeneroPeliculaEntity findByNombre(String trim);
}
