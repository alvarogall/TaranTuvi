package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ValoracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<ValoracionEntity, Integer> {

    @Query( "SELECT p.titulooriginal AS pelicula, AVG(v.nota) AS notaMedia " +
            "FROM ValoracionEntity v " +
            "JOIN v.peliculaid p " +
            "GROUP BY p.titulooriginal " +
            "ORDER BY notaMedia DESC")
    List<Object[]> getRankingPeliculas();
}
