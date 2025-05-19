//Máximo Prados Meléndez
package es.uma.taw.tarantuvi.controller;


import es.uma.taw.tarantuvi.dao.ListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaListaPeliculaRepository;
import es.uma.taw.tarantuvi.dao.PeliculaRepository;
import es.uma.taw.tarantuvi.dao.UsuarioRepository;
import es.uma.taw.tarantuvi.dto.*;
import es.uma.taw.tarantuvi.entity.ListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaListaPeliculaId;
import es.uma.taw.tarantuvi.service.*;
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
public class UsuarioPremium extends BaseController {
    @Autowired
    protected GeneroPersonaService generoPersonaService;

    @Autowired
    protected NacionalidadService nacionalidadService;


    @Autowired
    protected PeliculaService peliculaService;

    @Autowired
    protected PersonaService personaService;

    @Autowired
    protected ProductoraService productoraService;

    @Autowired
    protected GeneroPeliculaService generoPeliculaService;


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
        List<PeliculaEntity> peliculasPendientes = new ArrayList<>();
        List<PeliculaEntity> peliculasQueMeGustan = new ArrayList<>();
        List<ListaPeliculaEntity> listasPeliculas = listaPeliculaRepository.findListasByUsuarioid(idUsuario);
        for(ListaPeliculaEntity playlist : listasPeliculas) {
            peliculasQueMeGustan.addAll(peliculaListaPeliculaRepository.findByListaId(playlist));

        }

        peliculasPendientes = peliculaRepository.findPelisNoVistas(peliculasQueMeGustan);



        List<PeliculaEntity> peliculas = peliculaRepository.findAll();
        List<PeliculaEntity> novedades = peliculaRepository.findPeliculasMasRecientes(PageRequest.of(0, 2));
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("novedades", novedades);
        model.addAttribute("peliculasQueMeGustan", peliculasPendientes);

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


    @GetMapping("/eliminarPeliculaLista")
    public String doEliminarPeliculaLista(Model model, HttpSession session,@RequestParam("idLista") Integer idLista,
                                          @RequestParam("idPelicula") Integer idPelicula) {

        PeliculaListaPeliculaId id = new PeliculaListaPeliculaId(idPelicula, idLista);

        peliculaListaPeliculaRepository.deleteById(id);

        return "redirect:/usuarioPremium/perfil";
    }




    @GetMapping("/pelicula/listar")
    public String doListarPelicula(Model model,
                                   HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            model.addAttribute("peliculas", peliculaService.listarPeliculas());
            model.addAttribute("actores", personaService.listarActores());
            model.addAttribute("productoras", productoraService.listarProductoras());
            model.addAttribute("generos", generoPeliculaService.listarGenerosPeliculas());
            model.addAttribute("filtroPelicula", new FiltroPelicula());

            return "UsuarioPremium/peliculas";
        }
    }


    @GetMapping("/pelicula")
    public String doDetallesPelicula(@RequestParam("id") Integer id,
                                     Model model,
                                     HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            Pelicula peliculaDto = this.peliculaService.buscarPelicula(id);
            Usuario usuarioDto = (Usuario) session.getAttribute("usuario");
            Valoracion valoracionDto = this.peliculaService.obtenerValoracionUsuario(id, usuarioDto.getUsuarioId());
            model.addAttribute("pelicula", peliculaDto);
            model.addAttribute("valoracion", valoracionDto);

            return "UsuarioPremium/pelicula";
        }
    }

    @PostMapping("/pelicula/filtrar")
    public String doFiltrarPeliculas(@ModelAttribute("filtroPelicula") FiltroPelicula filtro,
                                     Model model,
                                     HttpSession session) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            List<Pelicula> peliculasFiltradas = peliculaService.filtrarPeliculas(filtro);
            model.addAttribute("peliculas", peliculasFiltradas);

            List<Actor> actoresDto = this.personaService.listarActores();
            List<Productora> productorasDto = this.productoraService.listarProductoras();
            List<GeneroPelicula> generosDto = this.generoPeliculaService.listarGenerosPeliculas();

            model.addAttribute("actores", actoresDto);
            model.addAttribute("productoras", productorasDto);
            model.addAttribute("generos", generosDto);

            model.addAttribute("filtroPelicula", filtro);

            return "UsuarioPremium/peliculas";
        }
    }

    @PostMapping("/pelicula/valorar")
    public String doValorarPelicula(@RequestParam("id") Integer id,
                                    @RequestParam("nota") Integer nota,
                                    HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            Usuario usuarioDto = (Usuario) session.getAttribute("usuario");
            this.peliculaService.valorarPelicula(id, usuarioDto.getUsuarioId(), nota);

            return "redirect:/UsuarioPremium/pelicula?id=" + id;
        }
    }

    @GetMapping("/actor")
    public String doDetallesActor(@RequestParam("id") Integer id,
                                  Model model,
                                  HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            ActorResumen actorResumenDto = this.personaService.buscarActorConActuaciones(id);
            model.addAttribute("actor", actorResumenDto);

            return "UsuarioPremium/actor";
        }
    }

    @GetMapping("/actor/listar")
    public String doListarActor(Model model,
                                HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            List<Actor> actoresDto = this.personaService.listarActores();
            model.addAttribute("actores", actoresDto);

            model.addAttribute("generos", generoPersonaService.listarGenerosPersonas());
            model.addAttribute("nacionalidades", nacionalidadService.listarNacionalidades());
            model.addAttribute("peliculas", peliculaService.listarPeliculas());
            model.addAttribute("filtroActor", new FiltroActor());

            return "UsuarioPremium/actores";
        }
    }

    @PostMapping("/actor/filtrar")
    public String doFiltrarActor(@ModelAttribute("filtroActor") FiltroActor filtro,
                                 Model model,
                                 HttpSession session) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            List<Actor> actoresFiltrados = personaService.filtrarActores(filtro);
            model.addAttribute("actores", actoresFiltrados);

            model.addAttribute("generos", generoPersonaService.listarGenerosPersonas());
            model.addAttribute("nacionalidades", nacionalidadService.listarNacionalidades());
            model.addAttribute("peliculas", peliculaService.listarPeliculas());
            model.addAttribute("filtroActor", filtro);

            return "UsuarioPremium/actores";
        }
    }

}
