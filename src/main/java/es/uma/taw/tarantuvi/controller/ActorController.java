package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dao.PersonaRepository;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController extends BaseController {
    @Autowired
    protected PersonaRepository personaRepository;

    @GetMapping("/")
    public String doDetallesActor(@RequestParam("id") Integer id,
                                  Model model,
                                  HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            PersonaEntity actor = this.personaRepository.findById(id).orElse(null);
            model.addAttribute("actor", actor);

            return "actor/actor";
        }
    }

    @GetMapping("/listar")
    public String doListar(Model model,
                           HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            List<PersonaEntity> actores = this.personaRepository.findAllActores();
            model.addAttribute("actores", actores);

            return "actor/actores";
        }
    }
}
