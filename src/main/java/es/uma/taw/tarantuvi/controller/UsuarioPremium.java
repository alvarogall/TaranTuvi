//Máximo Prados Meléndez
package es.uma.taw.tarantuvi.controller;



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
    protected ListaPeliculaService listaPeliculaService;
    @Autowired
    private PeliculaListaPeliculaService peliculaListaPeliculaService;

    @GetMapping("/")
    public String usuarioPlus(Model model, HttpSession session) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Integer idUsuario = usuario.getUsuarioId();
            List<Pelicula> peliculasPendientes = new ArrayList<>();
            List<Pelicula> peliculasQueMeGustan = new ArrayList<>();
            List<ListaPelicula> listasPeliculas = listaPeliculaService.findListasByUsuarioid(idUsuario);
            for (ListaPelicula playlist : listasPeliculas) {

                peliculasQueMeGustan.addAll(peliculaService.findByListaId(playlist.getListaPeliculaId()));

            }

            peliculasPendientes = peliculaService.findPelisNoVistas(peliculasQueMeGustan); //Fallo en esta query


            List<Pelicula> peliculas = peliculaService.listarPeliculas();
            List<Pelicula> novedades = peliculaService.findPeliculasMasRecientes();
            model.addAttribute("peliculas", peliculas);
            model.addAttribute("novedades", novedades);
            model.addAttribute("peliculasQueMeGustan", peliculasPendientes);

            return "UsuarioPremium/inicioUsuarioPremium";
        }
    }
    @GetMapping("/perfil")
    public String doPerfil(Model model, HttpSession session) {

        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Integer idUsuario = usuario.getUsuarioId();
            List<Pelicula> peliculasQueMeGustan = new ArrayList<>();


            List<ListaPelicula> listasPeliculas = listaPeliculaService.findListasByUsuarioid(idUsuario);
            for (ListaPelicula playlist : listasPeliculas) {
                peliculasQueMeGustan.addAll(peliculaService.findByListaId(playlist.getListaPeliculaId()));

            }
            model.addAttribute("peliculasQueMeGustan", peliculasQueMeGustan);
            model.addAttribute("listasPeliculas", listasPeliculas);
            model.addAttribute("listaPelicula", new ListaPelicula());
            model.addAttribute("peliculas", peliculaService.listarPeliculas());

            return "UsuarioPremium/perfilUsuarioPremium";
        }
    }

    @PostMapping("/crearLista")
    public String doCrearLista(@ModelAttribute()ListaPelicula listaPelicula, Model model, HttpSession session) {

        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            ListaPelicula listaPeliculaCrear = new ListaPelicula();
            listaPeliculaCrear.setListaPeliculaNombre(listaPelicula.getListaPeliculaNombre());
            listaPeliculaCrear.setUsuarioId(listaPelicula.getUsuarioId());
            listaPeliculaCrear.setPeliculaList(new ArrayList<>());

            listaPeliculaService.guardar(listaPeliculaCrear);

            return "redirect:/usuarioPremium/perfil";
        }
    }
    @GetMapping("/eliminarLista")
    public String doEliminarLista(Model model, HttpSession session,@RequestParam("idLista") Integer idLista) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Integer idUsuario = usuario.getUsuarioId();
            listaPeliculaService.deleteById(idLista);

            model.addAttribute("listasPeliculas", listaPeliculaService.findListasByUsuarioid(idUsuario));
            model.addAttribute("listaPelicula", new ListaPelicula());


            return "redirect:/usuarioPremium/perfil";
        }
    }
    @GetMapping("/anyadirPelicula")
    public String doAnyadirPelicula(Model model, HttpSession session,@RequestParam("idLista") Integer listaId) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            SeleccionPeliculasDto seleccionPeliculas = new SeleccionPeliculasDto();
            seleccionPeliculas.setListaId(listaId);
            ListaPelicula listaPelicula = listaPeliculaService.findById(listaId);
            List<Integer> peliculasYaSeleccionadas = new ArrayList<Integer>();
            for (Pelicula p : listaPelicula.getPeliculaList()) {

                peliculasYaSeleccionadas.add(p.getId());
            }
            seleccionPeliculas.setPeliculaIds(peliculasYaSeleccionadas);

            model.addAttribute("seleccionPeliculas", seleccionPeliculas);
            model.addAttribute("peliculas", peliculaService.listarPeliculas());
            model.addAttribute("listaPelicula", listaPelicula);
            return "UsuarioPremium/seleccionarPeliculas";
        }
    }
    @PostMapping("/guardarPeliculasSeleccionadas")
    public String doGuardarPeliculasSeleccionadas(Model model,@ModelAttribute("seleccionPeliculas") SeleccionPeliculasDto seleccionPeliculas,HttpSession session) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            Integer idLista = seleccionPeliculas.getListaId();
            List<Integer> peliculasIds = seleccionPeliculas.getPeliculaIds();
            if (peliculasIds != null && !peliculasIds.isEmpty()) {
                for (Integer peliculaId : peliculasIds) {
                    PeliculaListaPelicula nuevaPelicula = new PeliculaListaPelicula();
                    nuevaPelicula.setPeliculaId(peliculaId);
                    nuevaPelicula.setListaPeliculaId(idLista);
                    peliculaListaPeliculaService.guardar(nuevaPelicula);

                }
            }

            return "redirect:/usuarioPremium/perfil";
        }
    }

    @GetMapping("/eliminarPeliculaLista")
    public String doEliminarPeliculaLista(Model model, HttpSession session,@RequestParam("idLista") Integer idLista,
                                          @RequestParam("idPelicula") Integer idPelicula) {
        if (!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            PeliculaListaPeliculaId id = new PeliculaListaPeliculaId(idPelicula, idLista);

            peliculaListaPeliculaService.eliminar(id);

            return "redirect:/usuarioPremium/perfil";
        }
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
