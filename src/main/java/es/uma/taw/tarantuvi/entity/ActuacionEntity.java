package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACTUACION", schema = "TaranTuvi")
public class ActuacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTUACIONID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAID")
    private PersonaEntity personaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PELICULAID")
    private PeliculaEntity peliculaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENEROPERSONAID")
    private GeneroPersonaEntity generopersonaid;

    @Column(name = "PERSONAJE")
    private String personaje;

    @Column(name = "ORDEN")
    private Integer orden;

    public void setPersonaId(PersonaEntity actor) {
        this.personaid = actor;
    }

    public void setPeliculaId(PeliculaEntity pelicula) {
        this.peliculaid = pelicula;
    }

}