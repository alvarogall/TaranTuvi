package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.dto.ActorResumen;
import es.uma.taw.tarantuvi.dto.FiltroActor;
import es.uma.taw.tarantuvi.dto.FiltroPelicula;
import es.uma.taw.tarantuvi.dto.GeneroPelicula;
import es.uma.taw.tarantuvi.dto.Pelicula;
import es.uma.taw.tarantuvi.dto.Productora;
import es.uma.taw.tarantuvi.dto.Usuario;
import es.uma.taw.tarantuvi.dto.Valoracion;
import es.uma.taw.tarantuvi.service.GeneroPeliculaService;
import es.uma.taw.tarantuvi.service.GeneroPersonaService;
import es.uma.taw.tarantuvi.service.NacionalidadService;
import es.uma.taw.tarantuvi.service.PeliculaService;
import es.uma.taw.tarantuvi.service.PersonaService;
import es.uma.taw.tarantuvi.service.ProductoraService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends BaseController {
    private static final String USUARIO_VIEW_ENDPOINT = "usuario/";

    @Autowired
    protected PeliculaService peliculaService;

    @Autowired
    protected PersonaService personaService;

    @Autowired
    protected ProductoraService productoraService;

    @Autowired
    protected GeneroPeliculaService generoPeliculaService;

    @Autowired
    protected GeneroPersonaService generoPersonaService;

    @Autowired
    protected NacionalidadService nacionalidadService;

    @GetMapping("/")
    public String doUsuario(Model model,
                            HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            return USUARIO_VIEW_ENDPOINT + "dashboard";
        }
    }

    @GetMapping("/perfil")
    public String doPerfil(HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        } else {
            return USUARIO_VIEW_ENDPOINT + "perfil";
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

            return USUARIO_VIEW_ENDPOINT + "pelicula";
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

            return USUARIO_VIEW_ENDPOINT + "peliculas";
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

            return USUARIO_VIEW_ENDPOINT + "peliculas";
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

            return "redirect:/usuario/pelicula?id=" + id;
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

            return USUARIO_VIEW_ENDPOINT + "actor";
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

            return USUARIO_VIEW_ENDPOINT + "actores";
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

            return USUARIO_VIEW_ENDPOINT + "actores";
        }
    }
}
