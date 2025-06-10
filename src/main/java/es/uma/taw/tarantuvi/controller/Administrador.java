/**
 * @author Pablo Gámez
 */

package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dto.*;
import es.uma.taw.tarantuvi.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            List<GeneroPelicula> generos = this.generoPeliculaService.listarGenerosPeliculas();
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
            List<Productora> productoras = this.productoraService.listarProductoras();
            model.addAttribute("productoras", productoras);
            return "Administrador/productoras";
        }
    }

    @PostMapping("/productoras/editar")
    public String doEditarProductora(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        Productora productora = this.productoraService.buscarProductora(id);
        List<Nacionalidad> nacionalidades = nacionalidadService.listarNacionalidades();
        model.addAttribute("productora", productora);
        model.addAttribute("nacionalidades", nacionalidades);
        return "Administrador/productora";
    }

    @PostMapping("/productoras/confirmarCambios")
    public String doConfirmarCambiosProductora(@ModelAttribute Productora dtoProductora) {
        this.productoraService.guardarProductora(dtoProductora);
        return "redirect:/administrador/productoras";
    }

    @PostMapping("/productoras/borrar")
    public String doBorrarProductora(@RequestParam("id") Integer id) {
        this.productoraService.borrarProductora(id);
        return "redirect:/administrador/productoras";
    }

    @GetMapping("/idiomasHablados")
    public String vistaIdiomasHablados(Model model, HttpSession session){
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<IdiomaHablado> idiomasHablados = this.idiomaHabladoService.listarIdiomasHablados();
            model.addAttribute("idiomasHablados", idiomasHablados);
            return "Administrador/idiomasHablados";
        }
    }

    @PostMapping("/idiomasHablados/editar")
    public String doEditarIdiomaHablado(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
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

    @GetMapping("/nacionalidades")
    public String vistaNacionalidades(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<Nacionalidad> nacionalidades = nacionalidadService.listarNacionalidades();
            model.addAttribute("nacionalidades", nacionalidades);
            return "Administrador/nacionalidades";
        }
    }

    @PostMapping("/nacionalidades/editar")
    public String doEditarNacionalidad(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else {
            Nacionalidad nacionalidad = this.nacionalidadService.buscarNacionalidad(id);
            model.addAttribute("nacionalidad", nacionalidad);
            return "Administrador/nacionalidad";
        }
    }

    @PostMapping("/nacionalidades/confirmarCambios")
    public String doConfirmarCambiosNacionalidad(@ModelAttribute Nacionalidad dtoNacionalidad) {
        this.nacionalidadService.guardarNacionalidad(dtoNacionalidad);
        return "redirect:/administrador/nacionalidades";
    }

    @PostMapping("/nacionalidades/borrar")
    public String doBorrarNacionalidad(@RequestParam("id") Integer id){
        this.nacionalidadService.borrarNacionalidad(id);
        return "redirect:/administrador/nacionalidades";
    }

    @GetMapping("/paisesRodaje")
    public String vistaPaisesRodaje(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<PaisRodaje> paisesRodaje = this.paisRodajeService.listarPaisesRodaje();
            model.addAttribute("paisesRodaje", paisesRodaje);
            return "Administrador/paisesRodaje";
        }
    }

    @PostMapping("/paisesRodaje/editar")
    public String doEditarPaisRodaje(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else {
            PaisRodaje paisRodaje = this.paisRodajeService.buscarPaisRodaje(id);
            model.addAttribute("paisRodaje", paisRodaje);
            return "Administrador/paisRodaje";
        }
    }

    @PostMapping("/paisesRodaje/confirmarCambios")
    public String doConfirmarCambiosPaisRodaje(@ModelAttribute PaisRodaje dtoPaisRodaje) {
        this.paisRodajeService.guardarPaisRodaje(dtoPaisRodaje);
        return "redirect:/administrador/paisesRodaje";
    }

    @PostMapping("/paisesRodaje/borrar")
    public String doBorrarPaisRodaje(@RequestParam("id") Integer id){
        this.paisRodajeService.borrarPaisRodaje(id);
        return "redirect:/administrador/paisesRodaje";
    }

    @GetMapping("/palabrasClave")
    public String vistaPalabrasClave(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<PalabraClave> palabrasClave = this.palabraClaveService.listarPalabrasClave();
            model.addAttribute("palabrasClave", palabrasClave);
            return "Administrador/palabrasClave";
        }
    }

    @PostMapping("/palabrasClave/editar")
    public String doEditarPalabraClave(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else {
            PalabraClave palabraClave = this.palabraClaveService.buscarPalabraClave(id);
            model.addAttribute("palabraClave", palabraClave);
            return "Administrador/palabraClave";
        }
    }

    @PostMapping("/palabrasClave/confirmarCambios")
    public String doConfirmarCambiosPalabraClave(@ModelAttribute PalabraClave dtoPalabraClave) {
        this.palabraClaveService.guardarPalabraClave(dtoPalabraClave);
        return "redirect:/administrador/palabrasClave";
    }

    @PostMapping("/palabrasClave/borrar")
    public String doBorrarPalabraClave(@RequestParam("id") Integer id){
        this.palabraClaveService.borrarPalabraClave(id);
        return "redirect:/administrador/palabrasClave";
    }

    @GetMapping("/departamentos")
    public String vistaDepartamentos(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<Departamento> departamentos = this.departamentoService.listarDepartamentos();
            model.addAttribute("departamentos", departamentos);
            return "Administrador/departamentos";
        }
    }

    @PostMapping("/departamentos/editar")
    public String doEditarDepartamento(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            return "redirect:/";
        }else {
            Departamento departamento = this.departamentoService.buscarDepartamento(id);
            model.addAttribute("departamento", departamento);
            return "Administrador/departamento";
        }
    }

    @PostMapping("/departamentos/confirmarCambios")
    public String doConfirmarDepartamento(@ModelAttribute Departamento dtoDepartamento) {
        this.departamentoService.guardarDepartamento(dtoDepartamento);
        return "redirect:/administrador/departamentos";
    }

    @PostMapping("/departamentos/borrar")
    public String doBorrarDepartamento(@RequestParam("id") Integer id){
        this.departamentoService.borrarDepartamento(id);
        return "redirect:/administrador/departamentos";
    }
}
