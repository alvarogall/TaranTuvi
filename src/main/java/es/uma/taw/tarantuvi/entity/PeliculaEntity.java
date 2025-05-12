package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.Pelicula;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PELICULA", schema = "TaranTuvi")
public class PeliculaEntity {
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

    public Pelicula toDto(){
        Pelicula pelicula = new Pelicula();

        pelicula.setId(this.id);
        pelicula.setTitulooriginal(this.titulooriginal);
        pelicula.setUrlcaratula(this.urlcaratula);
        pelicula.setFecha(new String(String.valueOf(this.fechaestreno)));
        pelicula.setDuracion(new String(String.valueOf(this.duracion)));
        pelicula.setDescripcion(this.descripcion);
        pelicula.setPaginaweb(this.paginaweb);
        pelicula.setPresupuesto(new String(String.valueOf(this.presupuesto)));
        pelicula.setRecaudacion(new String(String.valueOf(this.recaudacion)));
        pelicula.setEslogan(this.eslogan);
        pelicula.setEstado(this.estado);

        if(this.actuacionList != null
                && this.trabajoList != null
                && this.productoraList != null
                && this.paisRodajeList != null
                && this.idiomaHabladoList != null
                && this.generoPeliculaList != null){
            List<Integer> idsCast = new ArrayList<Integer>();
            for(ActuacionEntity a : this.actuacionList){
                idsCast.add(a.getId());
            }
            pelicula.setCast(idsCast);

            List<Integer> idsCrew = new ArrayList<Integer>();
            for(TrabajoEntity t : this.trabajoList){
                idsCrew.add(t.getId());
            }
            pelicula.setCrew(idsCrew);

            List<Integer> idsProductoras = new ArrayList<Integer>();
            for(ProductoraEntity p : this.productoraList){
                idsProductoras.add(p.getId());
            }
            pelicula.setProductoras(idsProductoras);

            List<Integer> idsPaisesRodaje = new ArrayList<Integer>();
            for(PaisRodajeEntity p : this.paisRodajeList){
                idsPaisesRodaje.add(p.getId());
            }
            pelicula.setPaisesRodaje(idsPaisesRodaje);

            List<Integer> idsIdiomas = new ArrayList<Integer>();
            for(IdiomaHabladoEntity h : this.idiomaHabladoList){
                idsIdiomas.add(h.getId());
            }
            pelicula.setIdiomas(idsIdiomas);

            List<Integer> idsGeneros = new ArrayList<Integer>();
            for(GeneroPeliculaEntity g : this.generoPeliculaList){
                idsGeneros.add(g.getId());
            }
            pelicula.setGeneros(idsGeneros);
        }else{
            pelicula.setCast(new ArrayList<Integer>());
            pelicula.setCrew(new ArrayList<Integer>());
            pelicula.setProductoras(new ArrayList<Integer>());
            pelicula.setPaisesRodaje(new ArrayList<Integer>());
            pelicula.setIdiomas(new ArrayList<Integer>());
            pelicula.setGeneros(new ArrayList<Integer>());
        }

        return pelicula;
    }
}

























































































