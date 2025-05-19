/*
User: jesus
*/

package es.uma.taw.tarantuvi.dto;

import es.uma.taw.tarantuvi.entity.*;
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

    protected List<GeneroPeliculaEntity> generoPeliculaList;
    protected List<PalabraClaveEntity> palabraClaveList;
    protected List<IdiomaHabladoEntity> idiomaHabladoList;
    protected List<PaisRodajeEntity> paisRodajeList;
    protected List<ProductoraEntity> productoraList;
    protected List<ListaPeliculaEntity> listaPeliculaList;
    protected List<ActuacionEntity> actuacionList;
    protected List<TrabajoEntity> trabajoList;
    protected List<ValoracionEntity> valoracionList;
}