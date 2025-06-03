/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 * @author Alejandro Cueto
 */

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

    @Query("SELECT P, " +
            "       (SELECT AVG(V.nota) FROM ValoracionEntity V JOIN V.peliculaid pl2 JOIN pl2.productoraList p2 WHERE p2 = P), " +
            "       (SELECT AVG(pl3.presupuesto) FROM PeliculaEntity pl3 JOIN pl3.productoraList p3 WHERE p3 = P), " +
            "       (SELECT AVG(pl4.recaudacion) FROM PeliculaEntity pl4 JOIN pl4.productoraList p4 WHERE p4 = P) " +
            "FROM ProductoraEntity P " +
            "WHERE (:presupuestoMin IS NULL OR (SELECT AVG(pl3.presupuesto) FROM PeliculaEntity pl3 JOIN pl3.productoraList p3 WHERE p3 = P) >= :presupuestoMin) " +
            "AND (:presupuestoMax IS NULL OR (SELECT AVG(pl3.presupuesto) FROM PeliculaEntity pl3 JOIN pl3.productoraList p3 WHERE p3 = P) <= :presupuestoMax) " +
            "AND (:recaudacionMin IS NULL OR (SELECT AVG(pl4.recaudacion) FROM PeliculaEntity pl4 JOIN pl4.productoraList p4 WHERE p4 = P) >= :recaudacionMin) " +
            "AND (:recaudacionMax IS NULL OR (SELECT AVG(pl4.recaudacion) FROM PeliculaEntity pl4 JOIN pl4.productoraList p4 WHERE p4 = P) <= :recaudacionMax)")
    List<Object[]> getProductorasConNotasMediasYFiltros(Double presupuestoMin,Double presupuestoMax,Double recaudacionMin,Double recaudacionMax);
}
