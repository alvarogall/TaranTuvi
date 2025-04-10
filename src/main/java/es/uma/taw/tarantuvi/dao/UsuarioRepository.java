package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
}
