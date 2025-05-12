package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.ValoracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ValoracionRepository extends JpaRepository<ValoracionEntity, Integer> {

    @Query("SELECT V.peliculaid, AVG(V.nota) FROM ValoracionEntity V GROUP BY V.peliculaid ORDER BY AVG(V.nota) DESC")
    List<Object[]> getPeliculaConMayorNotaMedia();

}
