package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "LISTAPELICULA", schema = "TaranTuvi")
public class ListaPeliculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISTAPELICULAID", nullable = false)
    private Integer id;

    @Column(name = "LISTAPELICULANOMBRE")
    private String listapeliculanombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIOID")
    private UsuarioEntity usuarioid;

    @ManyToMany
    @JoinTable(
            name = "PELICULALISTAPELICULA",
            joinColumns = @JoinColumn(name = "LISTAPELICULAID", referencedColumnName = "LISTAPELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID")
    )
    private List<PeliculaEntity> peliculaList;

}