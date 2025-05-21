/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    @Query("select u from UsuarioEntity u where u.usuario = :usuario and u.password = :password")
    public UsuarioEntity autenticar(@Param("usuario") String usuario,
                                    @Param("password") String password);
}
