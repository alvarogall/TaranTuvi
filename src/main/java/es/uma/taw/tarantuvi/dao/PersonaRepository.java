package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
}
