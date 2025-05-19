package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import es.uma.taw.tarantuvi.dto.Valoracion;

@Getter
@Setter
@Entity
@Table(name = "VALORACION", schema = "TaranTuvi")
public class ValoracionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VALORACIONID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PELICULAID")
    private PeliculaEntity peliculaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIOID")
    private UsuarioEntity usuarioid;

    @Column(name = "NOTA")
    private Integer nota;

    public Valoracion toDto() {
        Valoracion valoracion = new Valoracion();
        
        valoracion.setNota(nota);

        return valoracion;
    }

}