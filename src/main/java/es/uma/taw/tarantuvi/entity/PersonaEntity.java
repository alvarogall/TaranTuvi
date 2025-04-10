package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PERSONA", schema = "TaranTuvi")
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSONAID", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENEROPERSONAID")
    private GeneroPersonaEntity generopersonaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NACIONALIDADID")
    private NacionalidadEntity nacionalidadid;

    @Lob
    @Column(name = "URLFOTO")
    private String urlfoto;

    @OneToMany(mappedBy = "personaid")
    private List<ActuacionEntity> actuacionList;

    @OneToMany(mappedBy = "personaid")
    private List<TrabajoEntity> trabajoList;

}