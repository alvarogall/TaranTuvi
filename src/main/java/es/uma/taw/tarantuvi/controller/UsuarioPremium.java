package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuarioPremium")
public class UsuarioPremium {


    @Autowired
    protected PeliculaRepository peliculaRepository;

    @GetMapping("/")
    public String usuarioPlus(Model model) {
        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        List<PeliculaEntity> novedades = peliculaRepository.findPeliculasMasRecientes(PageRequest.of(0, 2));
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("novedades", novedades);

        return "UsuarioPremium/inicioUsuarioPremium";
    }


}
