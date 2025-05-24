/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PELICULA", schema = "TaranTuvi")
public class PeliculaEntity implements Serializable, DTO<Pelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PELICULAID", nullable = false)
    private Integer id;

    @Column(name = "TITULOORIGINAL")
    private String titulooriginal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDIOMAORIGINALHABLADOID")
    private IdiomaHabladoEntity idiomaoriginalhabladoid;

    @Column(name = "PRESUPUESTO", precision = 10)
    private BigDecimal presupuesto;

    @Column(name = "FECHAESTRENO")
    private LocalDate fechaestreno;

    @Column(name = "DURACION")
    private Integer duracion;

    @Column(name = "RECAUDACION", precision = 10)
    private BigDecimal recaudacion;

    @Column(name = "ESTADO")
    private String estado;

    @Lob
    @Column(name = "PAGINAWEB")
    private String paginaweb;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ESLOGAN")
    private String eslogan;

    @Lob
    @Column(name = "URLCARATULA")
    private String urlcaratula;

    @Column(name = "VOTOS")
    private Integer votos;

    @Column(name = "NOTA", precision = 10)
    private BigDecimal nota;

    @Column(name = "POPULARIDAD", precision = 10)
    private BigDecimal popularidad;

    @ManyToMany
    @JoinTable(
            name = "PELICULAGENEROPELICULA",
            joinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "GENEROPELICULAID", referencedColumnName = "GENEROPELICULAID")
    )
    private List<GeneroPeliculaEntity> generoPeliculaList;

    @ManyToMany
    @JoinTable(
            name = "PELICULAPALABRACLAVE",
            joinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "PALABRACLAVEID", referencedColumnName = "PALABRACLAVEID")
    )
    private List<PalabraClaveEntity> palabraClaveList;

    @ManyToMany
    @JoinTable(
            name = "PELICULAIDIOMAHABLADO",
            joinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "IDIOMAHABLADOID", referencedColumnName = "IDIOMAHABLADOID")
    )
    private List<IdiomaHabladoEntity> idiomaHabladoList;

    @ManyToMany
    @JoinTable(
            name = "PELICULAPAISRODAJE",
            joinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "PAISRODAJEID", referencedColumnName = "PAISRODAJEID")
    )
    private List<PaisRodajeEntity> paisRodajeList;

    @ManyToMany
    @JoinTable(
            name = "PELICULAPRODUCTORA",
            joinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCTORAID", referencedColumnName = "PRODUCTORAID")
    )
    private List<ProductoraEntity> productoraList;

    @ManyToMany(mappedBy = "peliculaList")
    private List<ListaPeliculaEntity> listaPeliculaList;

    @OneToMany(mappedBy = "peliculaid")
    private List<ActuacionEntity> actuacionList;

    @OneToMany(mappedBy = "peliculaid")
    private List<TrabajoEntity> trabajoList;

    @OneToMany(mappedBy = "peliculaid")
    private List<ValoracionEntity> valoracionList;

    public void add(ActuacionEntity actuacion) {
        this.actuacionList.add(actuacion);
    }

    public Pelicula toDto() {
        Pelicula pelicula = new Pelicula();

        pelicula.setId(this.id);
        pelicula.setTitulooriginal(this.titulooriginal);
        pelicula.setUrlcaratula(this.urlcaratula);
        pelicula.setFecha(this.fechaestreno != null ? this.fechaestreno.toString() : "");
        pelicula.setDuracion(this.duracion != null ? String.valueOf(this.duracion) : "");
        pelicula.setDescripcion(this.descripcion);
        pelicula.setPaginaweb(this.paginaweb);
        pelicula.setPresupuesto(this.presupuesto != null ? this.presupuesto.toString() : "");
        pelicula.setRecaudacion(this.recaudacion != null ? this.recaudacion.toString() : "");
        pelicula.setEslogan(this.eslogan);
        pelicula.setEstado(this.estado);
        pelicula.setNota(this.nota);
        pelicula.setFechaestreno(this.fechaestreno);
        pelicula.setPopularidad(this.popularidad);
        pelicula.setVotos(this.votos);

        // Idioma original hablado
        if (this.idiomaoriginalhabladoid != null) {
            pelicula.setIdiomaoriginalhablado(this.idiomaoriginalhabladoid.toDto());
        }

        // Cast (Actuaciones)
        if (this.actuacionList != null) {
            List<Integer> idsCast = new ArrayList<>();
            List<Actuacion> actuaciones = new ArrayList<>();
            for (ActuacionEntity a : this.actuacionList) {
                idsCast.add(a.getId());
                actuaciones.add(a.toDto());
            }
            pelicula.setCast(idsCast);
            pelicula.setActuacionList(actuaciones);
        } else {
            pelicula.setCast(new ArrayList<>());
            pelicula.setActuacionList(new ArrayList<>());
        }

        // Crew (Trabajos)
        if (this.trabajoList != null) {
            List<Integer> idsCrew = new ArrayList<>();
            List<Trabajo> trabajos = new ArrayList<>();
            for (TrabajoEntity t : this.trabajoList) {
                idsCrew.add(t.getId());
                trabajos.add(t.toDto());
            }
            pelicula.setCrew(idsCrew);
            pelicula.setTrabajoList(trabajos);
        } else {
            pelicula.setCrew(new ArrayList<>());
            pelicula.setTrabajoList(new ArrayList<>());
        }

        // Productoras
        if (this.productoraList != null) {
            List<Integer> idsProductoras = new ArrayList<>();
            List<Productora> productoras = new ArrayList<>();
            for (ProductoraEntity p : this.productoraList) {
                idsProductoras.add(p.getId());
                productoras.add(p.toDto());
            }
            pelicula.setProductoras(idsProductoras);
            pelicula.setProductoraList(productoras);
        } else {
            pelicula.setProductoras(new ArrayList<>());
            pelicula.setProductoraList(new ArrayList<>());
        }

        // Países de rodaje
        if (this.paisRodajeList != null) {
            List<Integer> idsPaisesRodaje = new ArrayList<>();
            List<PaisRodaje> paisesRodaje = new ArrayList<>();
            for (PaisRodajeEntity p : this.paisRodajeList) {
                idsPaisesRodaje.add(p.getId());
                paisesRodaje.add(p.toDto());
            }
            pelicula.setPaisesRodaje(idsPaisesRodaje);
            pelicula.setPaisRodajeList(paisesRodaje);
        } else {
            pelicula.setPaisesRodaje(new ArrayList<>());
            pelicula.setPaisRodajeList(new ArrayList<>());
        }

        // Idiomas hablados
        if (this.idiomaHabladoList != null) {
            List<Integer> idsIdiomas = new ArrayList<>();
            List<IdiomaHablado> idiomas = new ArrayList<>();
            for (IdiomaHabladoEntity h : this.idiomaHabladoList) {
                idsIdiomas.add(h.getId());
                idiomas.add(h.toDto());
            }
            pelicula.setIdiomas(idsIdiomas);
            pelicula.setIdiomaHabladoList(idiomas);
        } else {
            pelicula.setIdiomas(new ArrayList<>());
            pelicula.setIdiomaHabladoList(new ArrayList<>());
        }

        // Géneros
        if (this.generoPeliculaList != null) {
            List<Integer> idsGeneros = new ArrayList<>();
            List<GeneroPelicula> generos = new ArrayList<>();
            for (GeneroPeliculaEntity g : this.generoPeliculaList) {
                idsGeneros.add(g.getId());
                generos.add(g.toDto());
            }
            pelicula.setGeneros(idsGeneros);
            pelicula.setGeneroPeliculaList(generos);
        } else {
            pelicula.setGeneros(new ArrayList<>());
            pelicula.setGeneroPeliculaList(new ArrayList<>());
        }

        return pelicula;
    }

    public PeliculaResumen toDtoResumen() {
        PeliculaResumen pelicula = new PeliculaResumen();

        pelicula.setId(id);
        pelicula.setTitulooriginal(titulooriginal);
        pelicula.setUrlcaratula(urlcaratula);
        pelicula.setEstado(estado);
        pelicula.setDescripcion(descripcion);
        pelicula.setDuracion(duracion);
        pelicula.setFechaestreno(fechaestreno);

        return pelicula;
    }
}

























































































