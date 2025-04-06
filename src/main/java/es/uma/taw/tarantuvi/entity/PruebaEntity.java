package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRUEBA", schema = "TaranTuvi")
public class PruebaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRUEBAID", nullable = false)
    private Integer id;

    @Column(name = "TEXTO")
    private String texto;

    @Column(name = "NUMERO")
    private Integer numero;

}