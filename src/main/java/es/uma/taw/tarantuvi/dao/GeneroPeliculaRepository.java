/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 * @author Alejandro Cueto
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneroPeliculaRepository extends JpaRepository<GeneroPeliculaEntity, Integer> {
    @Query("Select g from GeneroPeliculaEntity g where g.generonombre = :trim")
    GeneroPeliculaEntity findByNombre(String trim);

    @Query("SELECT G.id, G.generonombre, AVG(V.nota) " +
            "FROM GeneroPeliculaEntity G " +
            "LEFT JOIN G.peliculaList P " +
            "LEFT JOIN P.valoracionList V " +
            "GROUP BY G.id, G.generonombre")
    List<Object[]> findNotaMediaPorGenero();

    @Query("SELECT G.id, G.generonombre, COUNT(V) " +
            "FROM GeneroPeliculaEntity G " +
            "JOIN G.peliculaList P " +
            "JOIN P.valoracionList V " +
            "GROUP BY G.id, G.generonombre")
    List<Object[]> findNumeroValoracionesPorGenero();




}
