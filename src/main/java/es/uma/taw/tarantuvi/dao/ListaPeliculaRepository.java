package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ListaPeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaPeliculaRepository extends JpaRepository<ListaPeliculaEntity, Integer> {
}
