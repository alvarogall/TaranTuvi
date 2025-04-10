package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DEPARTAMENTO", schema = "TaranTuvi")
public class DepartamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTAMENTOID", nullable = false)
    private Integer id;

    @Column(name = "DEPARTAMENTONOMBRE")
    private String departamentonombre;

    @OneToMany(mappedBy = "departamentoid")
    private List<TrabajoEntity> trabajoList;

}