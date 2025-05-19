package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.service.LoginService;
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
    protected LoginService loginService;

    private String redirigirRol(HttpSession session) {
        Usuario usuarioDto = (Usuario) session.getAttribute("usuario");
        return "redirect:/" + usuarioDto.getRol() + "/";
    }

    @GetMapping("/")
    public String doInit(HttpSession session,
                         Model model) {
        if(estaAutenticado(session)) {
            return redirigirRol(session);
        } else {
            model.addAttribute("usuario", new Usuario());
            return "login";
        }
    }

    @PostMapping("/autenticar")
    public String doLogin (@ModelAttribute() Usuario usuario,
                           Model model,
                           HttpSession session) {
        Usuario usuarioDto = this.loginService.autenticar(usuario.getUsuario(), usuario.getPassword());

        if(usuarioDto != null) {
            session.setAttribute("usuario", usuarioDto);
            return redirigirRol(session);
        } else {
            model.addAttribute("error", "Te has equivocado");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String doLogout (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
