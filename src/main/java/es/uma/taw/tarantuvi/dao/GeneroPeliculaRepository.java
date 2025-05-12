package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneroPeliculaRepository extends JpaRepository<GeneroPeliculaEntity, Integer> {
    @Query("Select g from GeneroPeliculaEntity g where g.generonombre = :trim")
    GeneroPeliculaEntity findByNombre(String trim);

    @Query("SELECT G.generonombre, AVG(V.nota) FROM GeneroPeliculaEntity G LEFT JOIN G.peliculaList P LEFT JOIN P.valoracionList V GROUP BY G.generonombre")
    List<Object[]> findNotaMediaPorGenero();
}
