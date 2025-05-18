package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.Departamento;
import es.uma.taw.tarantuvi.dto.Trabajo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DEPARTAMENTO", schema = "TaranTuvi")
public class DepartamentoEntity implements Serializable, DTO<Departamento> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTAMENTOID", nullable = false)
    private Integer id;

    @Column(name = "DEPARTAMENTONOMBRE")
    private String departamentonombre;

    @OneToMany(mappedBy = "departamentoid")
    private List<TrabajoEntity> trabajoList;

    public Departamento toDto(){
        Departamento departamento = new Departamento();

        departamento.setId(this.id);
        departamento.setDepartamentonombre(this.departamentonombre);
        departamento.setTrabajoList(this.trabajoList);


        return departamento;
    }

}