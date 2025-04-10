package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PALABRACLAVE", schema = "TaranTuvi")
public class PalabraClaveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PALABRACLAVEID", nullable = false)
    private Integer id;

    @Column(name = "PALABRACLAVENOMBRE")
    private String palabraclavenombre;

    @ManyToMany(mappedBy = "palabraClaveList")
    private List<PeliculaEntity> peliculaList;

}