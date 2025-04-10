package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.DepartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, Integer> {
}
