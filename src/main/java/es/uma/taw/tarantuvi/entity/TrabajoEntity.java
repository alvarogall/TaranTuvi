package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.Trabajo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "TRABAJO", schema = "TaranTuvi")
public class TrabajoEntity implements Serializable, DTO<Trabajo> {
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

    public Trabajo toDto(){
        Trabajo trabajo = new Trabajo();

        trabajo.setId(this.id);
        trabajo.setPersonaid(this.personaid);
        trabajo.setPeliculaid(this.peliculaid);
        trabajo.setTrabajonombre(this.trabajonombre);
        trabajo.setDepartamentoid(this.departamentoid);

        trabajo.setLabel(this.getLabel());

        return trabajo;
    }
}