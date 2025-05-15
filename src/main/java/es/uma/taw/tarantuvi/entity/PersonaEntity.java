package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.dto.DTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PERSONA", schema = "TaranTuvi")
public class PersonaEntity implements DTO<Actor> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSONAID", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENEROPERSONAID")
    private GeneroPersonaEntity generopersonaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NACIONALIDADID")
    private NacionalidadEntity nacionalidadid;

    @Lob
    @Column(name = "URLFOTO")
    private String urlfoto;

    @OneToMany(mappedBy = "personaid")
    private List<ActuacionEntity> actuacionList;

    @OneToMany(mappedBy = "personaid")
    private List<TrabajoEntity> trabajoList;

    public Actor toDto(){
        Actor actor = new Actor();

        actor.setId(this.id);
        actor.setUrlfoto(this.urlfoto);
        actor.setNombre(this.nombre);
        if(this.generopersonaid != null && this.nacionalidadid != null){
            actor.setGenero(this.generopersonaid.getId());
            actor.setNacionalidad(this.nacionalidadid.getId());
            actor.setGeneropersonaid(this.generopersonaid);
            actor.setNacionalidadid(this.nacionalidadid);
        }

        if(this.actuacionList != null && this.trabajoList != null){
            List<Integer> idsPeliculas = new ArrayList<Integer>();
            for(ActuacionEntity a : this.actuacionList){
                if(a.getPeliculaid() != null){
                    idsPeliculas.add(a.getPeliculaid().getId());
                }
            }
            actor.setPeliculas(idsPeliculas);

            List<Integer> idsActuaciones = new ArrayList<Integer>();
            for(ActuacionEntity a : this.actuacionList){
                if(a.getPersonaje() != null){
                    idsActuaciones.add(a.getId());
                }
            }
            actor.setActuaciones(idsActuaciones);

            actor.setActuacionList(this.actuacionList);
            actor.setTrabajoList(this.trabajoList);
        }

        return actor;
    }

}