package es.uma.taw.tarantuvi.controller;

import es.uma.taw.tarantuvi.dao.GeneroPeliculaRepository;
import es.uma.taw.tarantuvi.dao.ProductoraRepository;
import es.uma.taw.tarantuvi.entity.GeneroPeliculaEntity;
import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/administrador/productoras")
    public String vistaProductoras(Model model) {
        List<ProductoraEntity> productoras = productoraRepository.findAll();
        model.addAttribute("productoras", productoras);
        return "Administrador/productoras";
    }


}
