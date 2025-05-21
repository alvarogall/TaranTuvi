/*
User: jesus
*/

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Pelicula {
    protected Integer id;
    protected String urlcaratula;
    protected String titulooriginal;
    protected String fecha;
    protected String duracion;
    protected String descripcion;
    private BigDecimal nota;
    private BigDecimal popularidad;
    private LocalDate fechaestreno;
    private IdiomaHablado idiomaoriginalhablado;
    private Integer votos;

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

    protected List<GeneroPelicula> generoPeliculaList;
    protected List<IdiomaHablado> idiomaHabladoList;
    protected List<PaisRodaje> paisRodajeList;
    protected List<Productora> productoraList;
    protected List<Actuacion> actuacionList;
    protected List<Trabajo> trabajoList;
}