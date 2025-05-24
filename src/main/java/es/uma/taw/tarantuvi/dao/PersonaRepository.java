/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
    @Query("Select p from PersonaEntity p where p.nombre = :actorName")
    PersonaEntity findByNombre(String actorName);

    @Query("select p from PersonaEntity p where p.actuacionList is not empty")
    List<PersonaEntity> findAllActores();

    @Query("select distinct p from PersonaEntity p left join p.actuacionList a where (:nombre is null or :nombre = '' or lower(p.nombre) like lower(concat('%', :nombre, '%'))) and (:genero is null or p.generopersonaid.id = :genero) and (:nacionalidad is null or p.nacionalidadid.id = :nacionalidad) and (:peliculas is null or a.peliculaid.id in :peliculas)")
    List<PersonaEntity> filtrarActores(@Param("nombre") String nombre,
                                       @Param("genero") Integer genero,
                                       @Param("nacionalidad") Integer nacionalidad,
                                       @Param("peliculas") List<Integer> peliculas);
}
