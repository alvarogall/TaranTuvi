package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoraRepository extends JpaRepository<ProductoraEntity, Integer> {
    @Query("Select p from ProductoraEntity p where p.productoranombre = :trim")
    ProductoraEntity findByNombre(String trim);

    @Query("SELECT COUNT(pl) FROM ProductoraEntity P JOIN P.peliculaList pl")
    Integer countPeliculasAsociadasProductora();

    @Query("SELECT P, AVG(V.nota), AVG(pl.presupuesto), AVG(pl.recaudacion) " +
            "FROM ProductoraEntity P LEFT JOIN P.peliculaList pl LEFT JOIN pl.valoracionList V " +
            "GROUP BY P " +
            "HAVING (:presupuestoMin IS NULL OR AVG(pl.presupuesto) >= :presupuestoMin) " +
            "AND (:presupuestoMax IS NULL OR AVG(pl.presupuesto) <= :presupuestoMax) " +
            "AND (:recaudacionMin IS NULL OR AVG(pl.recaudacion) >= :recaudacionMin) " +
            "AND (:recaudacionMax IS NULL OR AVG(pl.recaudacion) <= :recaudacionMax)")
    List<Object[]> getProductorasConNotasMediasYFiltros(Double presupuestoMin,Double presupuestoMax,Double recaudacionMin,Double recaudacionMax);
}
