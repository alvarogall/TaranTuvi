package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import java.util.List;

@Data
public class FiltroPelicula {
    private String nombre;
    private Integer valoracion;
    private Integer anio;
    private List<Integer> generos;
    private List<Integer> productoras;
    private List<Integer> actores;
}