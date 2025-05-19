package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.Actuacion;
import es.uma.taw.tarantuvi.dto.ActuacionResumen;
import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.Pelicula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ACTUACION", schema = "TaranTuvi")
public class ActuacionEntity implements Serializable, DTO<Actuacion> {
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

    @Transient
    public String getLabel() {
        return this.personaid.getNombre() + " – " + this.personaje;
    }

    public Actuacion toDto() {
        Actuacion actuacion = new Actuacion();

        actuacion.setId(this.id);
        actuacion.setPersonaid(this.personaid);
        actuacion.setPeliculaid(this.peliculaid);
        actuacion.setGeneropersonaid(this.generopersonaid);
        actuacion.setOrden(this.orden);
        actuacion.setPersonaje(this.personaje);

        actuacion.setLabel(this.personaid.getNombre() + " – " + this.personaje);

        return actuacion;
    }

    public ActuacionResumen toDtoResumen() {
        ActuacionResumen actuacion = new ActuacionResumen();

        actuacion.setId(id);
        actuacion.setPersonaje(personaje);
        actuacion.setOrden(orden);
        actuacion.setPelicula(peliculaid.toDtoResumen());
        actuacion.setPersona(personaid.getNombre());

        return actuacion;
    }
}