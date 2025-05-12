package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.ListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dao.UsuarioRepository;
import es.uma.taw.tarantuvi.dto.ListaPelicula;
import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.entity.ListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/usuarioPremium")
public class UsuarioPremium {


    @Autowired
    protected PeliculaRepository peliculaRepository;

    @Autowired
    protected ListaPeliculaRepository listaPeliculaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String usuarioPlus(Model model) {
        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        List<PeliculaEntity> novedades = peliculaRepository.findPeliculasMasRecientes(PageRequest.of(0, 2));
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("novedades", novedades);

        return "UsuarioPremium/inicioUsuarioPremium";
    }

    @GetMapping("/perfil")
    public String doPerfil(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getUsuarioId();

        List<ListaPeliculaEntity> listasPeliculas = listaPeliculaRepository.findListasByUsuarioid(idUsuario);
        model.addAttribute("listasPeliculas", listasPeliculas);
        model.addAttribute("listaPelicula", new ListaPelicula());

        return "UsuarioPremium/perfilUsuarioPremium";
    }

    @PostMapping("/crearLista")
    public String doCrearLista(@ModelAttribute()ListaPelicula listaPelicula, Model model, HttpSession session) {

        ListaPeliculaEntity listaPeliculaEntity = new ListaPeliculaEntity();
        listaPeliculaEntity.setListapeliculanombre(listaPelicula.getListaPeliculaNombre());
        listaPeliculaEntity.setUsuarioid(usuarioRepository.getReferenceById(listaPelicula.getUsuarioId()));
        listaPeliculaEntity.setPeliculaList(new ArrayList<>());
        listaPeliculaRepository.save(listaPeliculaEntity);

        return "redirect:/usuarioPremium/perfil";
    }




}
