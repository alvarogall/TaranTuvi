package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
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
}