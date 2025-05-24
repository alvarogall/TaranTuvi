/**
 * @author Álvaro Gallardo
 * @author Alejandro Cueto
 * @author Máximo Prados
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Integer> {

    @Query("SELECT p FROM PeliculaEntity p ORDER BY p.fechaestreno DESC")
    List<PeliculaEntity> findPeliculasMasRecientes(Pageable pageable);

    @Query("SELECT p, AVG(V.nota) FROM PeliculaEntity p " +
            "LEFT JOIN p.valoracionList V " +
            "JOIN p.generoPeliculaList g " +
            "WHERE (:generoId IS NULL OR g.id = :generoId) " +
            "AND (:idiomaId IS NULL OR p.idiomaoriginalhabladoid.id = :idiomaId) " +
            "GROUP BY p")
    List<Object[]> findPeliculasByFiltros(Integer generoId, Integer idiomaId);

    @Query("SELECT p, AVG(v.nota), COUNT(v) " +
            "FROM PeliculaEntity p JOIN p.valoracionList v " +
            "GROUP BY p " +
            "ORDER BY AVG(v.nota) DESC, COUNT(v) DESC")
    List<Object[]> findPeliculasOrdenadasPorNotaYValoraciones();

    @Query("select distinct p from PeliculaEntity p left join p.generoPeliculaList g left join p.productoraList prod left join p.actuacionList act where (:nombre is null or :nombre = '' or lower(p.titulooriginal) like lower(concat('%', :nombre, '%'))) and (:valoracion is null or p.nota >= :valoracion) and (:anio is null or function('year', p.fechaestreno) >= :anio) and (:generos is null or g.id in :generos) and (:productoras is null or prod.id in :productoras) and (:actores is null or act.personaid.id in :actores)")
    List<PeliculaEntity> filtrarPeliculas(@Param("nombre") String nombre,
                                          @Param("valoracion") Integer valoracion,
                                          @Param("anio") Integer anio,
                                          @Param("generos") List<Integer> generos,
                                          @Param("productoras") List<Integer> productoras,
                                          @Param("actores") List<Integer> actores);



    @Query("select c from PeliculaEntity c where c not in :peliculasQueMeGustan")
    List<PeliculaEntity> findPelisNoVistas(@Param("peliculasQueMeGustan") List<PeliculaEntity> peliculasQueMeGustan);

    @Query("select p from PeliculaEntity p where p.nota is not null order by p.nota desc, p.votos desc")
    List<PeliculaEntity> findPeliculasMejorValoradas();

}
