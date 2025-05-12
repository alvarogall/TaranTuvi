package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.UsuarioRepository;
import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.entity.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController extends BaseController {
    @Autowired
    protected UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String doInit(HttpSession session,
                         Model model) {
        if(estaAutenticado(session)) {
            Usuario usuarioDto = (Usuario) session.getAttribute("usuario");

            StringBuilder sb = new StringBuilder();

            sb.append("redirect:/");
            sb.append(usuarioDto.getRol());
            sb.append("/");

            return sb.toString();
        } else {
            model.addAttribute("usuario", new Usuario());
            return "login/login";
        }
    }

    @PostMapping("/autenticar")
    public String doLogin (@ModelAttribute() Usuario usuario,
                           Model model,
                           HttpSession session) {
        UsuarioEntity usuarioEntity = this.usuarioRepository.autenticar(usuario.getUsuario(), usuario.getPassword());

        if(usuarioEntity != null) {
            Usuario usuarioDto = usuarioEntity.toDto();
            session.setAttribute("usuario", usuarioDto);

            StringBuilder sb = new StringBuilder();

            sb.append("redirect:/");
            sb.append(usuarioDto.getRol());
            sb.append("/");

            return sb.toString();
        } else {
            model.addAttribute("error", "Te has equivocado");
            return "login/login";
        }
    }

    @GetMapping("/logout")
    public String doLogout (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
