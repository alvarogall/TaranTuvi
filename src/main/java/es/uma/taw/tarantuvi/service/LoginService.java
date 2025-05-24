/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.UsuarioRepository;
import es.uma.taw.tarantuvi.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uma.taw.tarantuvi.dto.Usuario;


@Service
public class LoginService {
    @Autowired
    protected UsuarioRepository usuarioRepository;

    public Usuario autenticar (String usuario, String clave) {
        UsuarioEntity user = this.usuarioRepository.autenticar(usuario, clave);

        if (user != null) {
            return user.toDto();
        } else {
            return null;
        }
    }
}
