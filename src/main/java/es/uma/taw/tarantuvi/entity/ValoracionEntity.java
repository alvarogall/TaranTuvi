package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.Trabajo;
import es.uma.taw.tarantuvi.dto.Valoracion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

import es.uma.taw.tarantuvi.dto.Valoracion;

@Getter
@Setter
@Entity
@Table(name = "VALORACION", schema = "TaranTuvi")
public class ValoracionEntity implements Serializable, DTO<Valoracion> {
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

    public Valoracion toDto(){
        Valoracion valoracion = new Valoracion();

        valoracion.setId(this.id);
        valoracion.setPeliculaid(this.peliculaid);
        valoracion.setUsuarioid(this.usuarioid);
        valoracion.setNota(this.nota);

        return valoracion;
    }

}