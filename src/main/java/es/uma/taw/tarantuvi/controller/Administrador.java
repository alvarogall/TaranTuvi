package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.GeneroPeliculaRepository;
import es.uma.taw.tarantuvi.dao.ProductoraRepository;
import es.uma.taw.tarantuvi.dto.*;
import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import es.uma.taw.tarantuvi.dao.*;
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
    protected GeneroPeliculaRepository generoPeliculaRepository;

    @Autowired
    protected ProductoraRepository productoraRepository;

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
            List<GeneroPeliculaEntity> generos = generoPeliculaRepository.findAll();
            model.addAttribute("generos", generos);
            return "Administrador/generos";
        }
    }

    @PostMapping("/generos/editar")
    public String doEditarGenero(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        GeneroPeliculaEntity genero = this.generoPeliculaRepository.findById(id).orElse(new GeneroPeliculaEntity());
        model.addAttribute("genero", genero);
        return "Administrador/genero";
    }

    @PostMapping("/generos/confirmarCambios")
    public String doConfirmarCambiosGenero(@RequestParam(value = "id", defaultValue = "-1") Integer id,
                                     @RequestParam("nombre") String nombre) {
        GeneroPeliculaEntity genero = this.generoPeliculaRepository.findById(id).orElse(new GeneroPeliculaEntity());
        genero.setGeneronombre(nombre);
        this.generoPeliculaRepository.save(genero);
        return "redirect:/administrador/generos";
    }

    @PostMapping("/generos/borrar")
    public String doBorrarGenero(@RequestParam("id") Integer id) {
        this.generoPeliculaRepository.deleteById(id);
        return "redirect:/administrador/generos";
    }

    @GetMapping("/productoras")
    public String vistaProductoras(Model model, HttpSession session) {
        if (!estaAutenticado(session)){
            return "redirect:/";
        } else {
            List<ProductoraEntity> productoras = productoraRepository.findAll();
            model.addAttribute("productoras", productoras);
            return "Administrador/productoras";
        }
    }

    @PostMapping("/productoras/editar")
    public String doEditarProductora(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        ProductoraEntity productora = this.productoraRepository.findById(id).orElse(new ProductoraEntity());
        model.addAttribute("productora", productora);
        return "Administrador/productora";
    }

    @PostMapping("/productoras/confirmarCambios")
    public String doConfirmarCambiosProductora(@RequestParam(value = "id", defaultValue = "-1") Integer id,
                                     @RequestParam("nombre") String nombre) {
        ProductoraEntity productora = this.productoraRepository.findById(id).orElse(new ProductoraEntity());
        productora.setProductoranombre(nombre);
        this.productoraRepository.save(productora);
        return "redirect:/administrador/productoras";
    }

    @PostMapping("/productoras/borrar")
    public String vistaGeneros(@RequestParam("id") Integer id) {
        this.productoraRepository.deleteById(id);
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
