package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.GeneroPeliculaRepository;
import es.uma.taw.tarantuvi.dao.ProductoraRepository;
import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Administrador {
    @Autowired
    protected GeneroPeliculaRepository generoPeliculaRepository;

    @Autowired
    protected ProductoraRepository productoraRepository;

    @GetMapping("/administrador")
    public String vistaAdministrador() {
        return "Administrador/inicioAdministrador";
    }

    @GetMapping("/administrador/generos")
    public String vistaGeneros(Model model) {
        List<GeneroPeliculaEntity> generos = generoPeliculaRepository.findAll();
        model.addAttribute("generos", generos);
        return "Administrador/generos";
    }

    @PostMapping("/administrador/generos/editar")
    public String doEditarGenero(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        GeneroPeliculaEntity genero = this.generoPeliculaRepository.findById(id).orElse(new GeneroPeliculaEntity());
        model.addAttribute("genero", genero);
        return "Administrador/genero";
    }

    @PostMapping("/administrador/generos/confirmarCambios")
    public String doConfirmarCambiosGenero(@RequestParam(value = "id", defaultValue = "-1") Integer id,
                                     @RequestParam("nombre") String nombre,
                                     Model model) {
        GeneroPeliculaEntity genero = this.generoPeliculaRepository.findById(id).orElse(new GeneroPeliculaEntity());
        genero.setGeneronombre(nombre);
        this.generoPeliculaRepository.save(genero);
        return "redirect:/administrador/generos";
    }

    @PostMapping("/administrador/generos/borrar")
    public String doBorrarGenero(@RequestParam("id") Integer id) {
        this.generoPeliculaRepository.deleteById(id);
        return "redirect:/administrador/generos";
    }

    @GetMapping("/administrador/productoras")
    public String vistaProductoras(Model model) {
        List<ProductoraEntity> productoras = productoraRepository.findAll();
        model.addAttribute("productoras", productoras);
        return "Administrador/productoras";
    }

    @PostMapping("/administrador/productoras/editar")
    public String doEditarProductora(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
        ProductoraEntity productora = this.productoraRepository.findById(id).orElse(new ProductoraEntity());
        model.addAttribute("productora", productora);
        return "Administrador/productora";
    }

    @PostMapping("/administrador/productoras/confirmarCambios")
    public String doConfirmarCambiosProductora(@RequestParam(value = "id", defaultValue = "-1") Integer id,
                                     @RequestParam("nombre") String nombre,
                                     Model model) {
        ProductoraEntity productora = this.productoraRepository.findById(id).orElse(new ProductoraEntity());
        productora.setProductoranombre(nombre);
        this.productoraRepository.save(productora);
        return "redirect:/administrador/productoras";
    }

    @PostMapping("/administrador/productoras/borrar")
    public  String vistaGeneros(@RequestParam("id") Integer id) {
        this.productoraRepository.deleteById(id);
        return "redirect:/administrador/productoras";
    }
}
