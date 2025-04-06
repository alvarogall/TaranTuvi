package es.uma.taw.tarantuvi.controller;

import org.springframework.ui.Model;
import es.uma.taw.tarantuvi.dao.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.taw.tarantuvi.entity.PruebaEntity;

import java.util.List;

@Controller
public class Controlador {
    @Autowired
    protected PruebaRepository pruebaRepository;

    @GetMapping("/")
    public String prueba(Model model) {
        List<PruebaEntity> listaPrueba = this.pruebaRepository.findAll();
        model.addAttribute("prueba", listaPrueba);
        return "prueba";
    }
}