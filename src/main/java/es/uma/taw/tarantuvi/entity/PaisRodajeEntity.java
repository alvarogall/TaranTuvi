package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PAISRODAJE", schema = "TaranTuvi")
public class PaisRodajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAISRODAJEID", nullable = false)
    private Integer id;

    @Column(name = "PAISRODAJENOMBRE")
    private String paisrodajenombre;

    @ManyToMany(mappedBy = "paisRodajeList")
    private List<PeliculaEntity> peliculaList;

}