package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
    @Query("Select p from PersonaEntity p where p.nombre = :actorName")
    PersonaEntity findByNombre(String actorName);

    @Query("select p from PersonaEntity p where p.actuacionList is not empty")
    List<PersonaEntity> findAllActores();
}
