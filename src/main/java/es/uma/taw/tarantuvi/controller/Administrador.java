package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dto.*;
import es.uma.taw.tarantuvi.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/administrador")
public class Administrador extends BaseController {
    @Autowired
    protected GeneroPeliculaService generoPeliculaService;

    @Autowired
    protected ProductoraService productoraService;

    @Autowired
    protected IdiomaHabladoService idiomaHabladoService;

    @Autowired
    protected NacionalidadService nacionalidadService;

    @Autowired
    protected PaisRodajeService paisRodajeService;

    @Autowired
    protected PalabraClaveService palabraClaveService;

    @Autowired
    protected DepartamentoService departamentoService;

    @GetMapping("/")
    public String vistaAdministrador(HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            return "Administrador/inicioAdministrador";
        }
    }

    @GetMapping("/generos")
    public String vistaGeneros(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<GeneroPelicula> generos = generoPeliculaService.listarGenerosPeliculas();
            model.addAttribute("generos", generos);
            return "Administrador/generos";
        }
    }

    @PostMapping("/generos/editar")
    public String doEditarGenero(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else {
            GeneroPelicula genero = this.generoPeliculaService.buscarGeneroPelicula(id);
            model.addAttribute("genero", genero);
            return "Administrador/genero";
        }
    }

    @PostMapping("/generos/confirmarCambios")
    public String doConfirmarCambiosGenero(@ModelAttribute GeneroPelicula dtoGeneroPelicula) {
        this.generoPeliculaService.guardarGeneroPelicula(dtoGeneroPelicula);
        return "redirect:/administrador/generos";
    }

    @PostMapping("/generos/borrar")
    public String doBorrarGenero(@RequestParam("id") Integer id) {
        this.generoPeliculaService.borrarGeneroPelicula(id);
        return "redirect:/administrador/generos";
    }

    @GetMapping("/productoras")
    public String vistaProductoras(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<Productora> productoras = productoraService.listarProductoras();
            model.addAttribute("productoras", productoras);
            return "Administrador/productoras";
        }
    }

    @PostMapping("/productoras/editar")
    public String doEditarProductora(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        Productora productora = this.productoraService.buscarProductora(id);
        model.addAttribute("productora", productora);
        return "Administrador/productora";
    }

    @PostMapping("/productoras/confirmarCambios")
    public String doConfirmarCambiosProductora(@ModelAttribute Productora dtoProductora) {
        this.productoraService.guardarProductora(dtoProductora);
        return "redirect:/administrador/productoras";
    }

    @PostMapping("/productoras/borrar")
    public String vistaGeneros(@RequestParam("id") Integer id) {
        this.productoraService.borrarProductora(id);
        return "redirect:/administrador/productoras";
    }

    @GetMapping("/idiomasHablados")
    public String vistaIdiomasHablados(Model model, HttpSession session){
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<IdiomaHablado> idiomasHablados = idiomaHabladoService.listarIdiomasHablados();
            model.addAttribute("idiomasHablados", idiomasHablados);
            return "Administrador/idiomasHablados";
        }
    }

    @PostMapping("/idiomasHablados/editar")
    public String doEditarIdiomasHablados(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else {
            IdiomaHablado idiomaHablado = this.idiomaHabladoService.buscarIdiomaHablado(id);
            model.addAttribute("idiomaHablado", idiomaHablado);
            return "Administrador/idiomaHablado";
        }
    }

    @PostMapping("/idiomasHablados/confirmarCambios")
    public String doConfirmarCambiosIdiomaHablado(@ModelAttribute IdiomaHablado dtoIdiomaHablado) {
        this.idiomaHabladoService.guardarIdiomaHablado(dtoIdiomaHablado);
        return "redirect:/administrador/idiomasHablados";
    }

    @PostMapping("/idiomasHablados/borrar")
    public String doBorrarIdiomaHablado(@RequestParam("id") Integer id){
        this.idiomaHabladoService.borrarIdiomaHablado(id);
        return "redirect:/administrador/idiomasHablados";
    }
}
