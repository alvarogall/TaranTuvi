package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NACIONALIDAD", schema = "TaranTuvi")
public class NacionalidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NACIONALIDADID", nullable = false)
    private Integer id;

    @Column(name = "NACIONALIDADNOMBRE", length = 50)
    private String nacionalidadnombre;

}