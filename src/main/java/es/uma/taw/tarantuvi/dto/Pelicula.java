package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Pelicula {
    protected Integer id;
    protected String urlCaratula;
    protected String nombre;
    protected String fecha;
    protected String duracion;
    protected String descripcion;

    protected List<Integer> cast = new ArrayList<>();
    protected List<Integer> crew = new ArrayList<>();
    protected List<Integer> productoras = new ArrayList<>();

    protected String paginaweb;
    protected String presupuesto;
    protected String recaudacion;

    protected List<Integer> paisesRodaje = new ArrayList<>();
    protected List<Integer> idiomas = new ArrayList<>();
    protected List<Integer> generos = new ArrayList<>();

    protected String eslogan;
    protected String estado;


}