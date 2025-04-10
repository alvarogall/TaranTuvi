package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ROL", schema = "TaranTuvi")
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLID", nullable = false)
    private Integer id;

    @Column(name = "ROLNOMBRE")
    private String rolnombre;

}