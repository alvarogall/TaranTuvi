package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.ListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dao.UsuarioRepository;
import es.uma.taw.tarantuvi.dto.ListaPelicula;
import es.uma.taw.tarantuvi.dto.SeleccionPeliculasDto;
import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.entity.ListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaEntity;
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
    protected PeliculaListaPeliculaRepository peliculaListaPeliculaRepository;

    @Autowired
    protected PeliculaRepository peliculaRepository;

    @Autowired
    protected ListaPeliculaRepository listaPeliculaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String usuarioPlus(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getUsuarioId();
        List<PeliculaEntity> peliculasQueMeGustan = new ArrayList<>();
        List<ListaPeliculaEntity> listasPeliculas = listaPeliculaRepository.findListasByUsuarioid(idUsuario);
        for(ListaPeliculaEntity playlist : listasPeliculas) {
            peliculasQueMeGustan.addAll(peliculaListaPeliculaRepository.findByListaId(playlist));

        }

        int contador = 0;
        for(PeliculaEntity pelicula : peliculasQueMeGustan) {
            contador++;

        }



        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        List<PeliculaEntity> novedades = peliculaRepository.findPeliculasMasRecientes(PageRequest.of(0, 2));
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("novedades", novedades);
        model.addAttribute("peliculasQueMeGustan", peliculasQueMeGustan);

        return "UsuarioPremium/inicioUsuarioPremium";
    }

    @GetMapping("/perfil")
    public String doPerfil(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getUsuarioId();
        List<PeliculaEntity> peliculasQueMeGustan = new ArrayList<>();


        List<ListaPeliculaEntity> listasPeliculas = listaPeliculaRepository.findListasByUsuarioid(idUsuario);
        for(ListaPeliculaEntity playlist : listasPeliculas) {
            peliculasQueMeGustan.addAll(peliculaListaPeliculaRepository.findByListaId(playlist));

        }
        model.addAttribute("peliculasQueMeGustan", peliculasQueMeGustan);
        model.addAttribute("listasPeliculas", listasPeliculas);
        model.addAttribute("listaPelicula", new ListaPelicula());
        model.addAttribute("peliculas",peliculaRepository.findAll());

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

    @GetMapping("/eliminarLista")
    public String doEliminarLista(Model model, HttpSession session,@RequestParam("idLista") Integer idLista) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getUsuarioId();
        listaPeliculaRepository.deleteById(idLista);

        model.addAttribute("listasPeliculas",listaPeliculaRepository.findListasByUsuarioid(idUsuario));
        model.addAttribute("listaPelicula", new ListaPelicula());


        return "redirect:/usuarioPremium/perfil";
    }

    @GetMapping("/anyadirPelicula")
    public String doAnyadirPelicula(Model model, HttpSession session,@RequestParam("idLista") Integer listaId) {
        SeleccionPeliculasDto seleccionPeliculas = new SeleccionPeliculasDto();
        seleccionPeliculas.setListaId(listaId);
        model.addAttribute("seleccionPeliculas", seleccionPeliculas);
        model.addAttribute("peliculas", peliculaRepository.findAll());
        model.addAttribute("listaPelicula", listaPeliculaRepository.findById(listaId).get());
        return "UsuarioPremium/seleccionarPeliculas";
    }

    @PostMapping("/guardarPeliculasSeleccionadas")
    public String doGuardarPeliculasSeleccionadas(Model model,@ModelAttribute("seleccionPeliculas") SeleccionPeliculasDto seleccionPeliculas,HttpSession session) {
        Integer idLista = seleccionPeliculas.getListaId();
        List<Integer> peliculasIds = seleccionPeliculas.getPeliculaIds();
        if(peliculasIds!=null && !peliculasIds.isEmpty()){
            for(Integer peliculaId : peliculasIds){
                PeliculaListaPeliculaEntity nuevaPelicula = new PeliculaListaPeliculaEntity();
                nuevaPelicula.setPelicula(peliculaRepository.findById(peliculaId).get());
                nuevaPelicula.setListaPelicula(listaPeliculaRepository.findById(idLista).get());
                peliculaListaPeliculaRepository.save(nuevaPelicula);

            }
        }

        return "redirect:/usuarioPremium/perfil";
    }


}
