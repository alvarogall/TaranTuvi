package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TRABAJO", schema = "TaranTuvi")
public class TrabajoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRABAJOID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAID")
    private PersonaEntity personaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PELICULAID")
    private PeliculaEntity peliculaid;

    @Column(name = "TRABAJONOMBRE")
    private String trabajonombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENTOID")
    private DepartamentoEntity departamentoid;

    public void setPersonaId(PersonaEntity crewMember) {
        this.personaid = crewMember;
    }

    public void setPeliculaId(PeliculaEntity pelicula) {
        this.peliculaid = pelicula;
    }

    @Transient
    public String getLabel() {
        return this.personaid.getNombre() + " – " + this.getTrabajonombre();
    }
}