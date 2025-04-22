package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
    @Query("Select p from PersonaEntity p where p.nombre = :actorName")
    PersonaEntity findByNombre(String actorName);
}
