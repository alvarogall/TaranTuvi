package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GENEROPERSONA", schema = "TaranTuvi")
public class GeneroPersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENEROPERSONAID", nullable = false)
    private Integer id;

    @Column(name = "GENEROPERSONANOMBRE")
    private String generopersonanombre;

}