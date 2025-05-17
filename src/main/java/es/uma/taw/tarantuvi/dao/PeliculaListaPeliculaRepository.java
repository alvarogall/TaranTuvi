package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaListaPeliculaRepository extends JpaRepository<PeliculaListaPeliculaEntity, PeliculaListaPeliculaId> {
}
