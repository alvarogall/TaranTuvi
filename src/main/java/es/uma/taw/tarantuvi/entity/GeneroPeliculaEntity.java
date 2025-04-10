package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GENEROPELICULA", schema = "TaranTuvi")
public class GeneroPeliculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENEROPELICULAID", nullable = false)
    private Integer id;

    @Column(name = "GENERONOMBRE")
    private String generonombre;

    @ManyToMany(mappedBy = "generoPeliculaList")
    private List<PeliculaEntity> peliculaList;
}