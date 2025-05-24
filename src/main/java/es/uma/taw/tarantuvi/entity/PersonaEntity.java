/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PERSONA", schema = "TaranTuvi")
public class PersonaEntity implements Serializable, DTO<Actor> {
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
            actor.setNombreGenero(this.generopersonaid.getGeneropersonanombre());
            actor.setNombreNacionalidad(this.nacionalidadid.getNacionalidadnombre());
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
        }

        return actor;
    }

    public ActorResumen toDtoResumen() {
        ActorResumen actorResumen = new ActorResumen();
        actorResumen.setId(id);
        actorResumen.setUrlfoto(urlfoto);
        actorResumen.setNombre(nombre);
        actorResumen.setNombreGenero(generopersonaid.getGeneropersonanombre());
        actorResumen.setNombreNacionalidad(nacionalidadid.getNacionalidadnombre());

        List<ActuacionResumen> actuaciones = new ArrayList<>();

        if(actuacionList != null) {
            for(ActuacionEntity actuacion : actuacionList) {
                actuaciones.add(actuacion.toDtoResumen());
            }
        }

        actorResumen.setActuaciones(actuaciones);

        List<TrabajoResumen> trabajos = new ArrayList<>();

        if(trabajoList != null) {
            for(TrabajoEntity trabajo : trabajoList) {
                trabajos.add(trabajo.toDtoResumen());
            }
        }

        actorResumen.setTrabajos(trabajos);

        return actorResumen;
    }

    public Persona toDtoPersona(){
        Persona persona = new Persona();

        persona.setId(this.id);
        persona.setNombre(this.nombre);
        if(this.generopersonaid != null && this.nacionalidadid != null){
            persona.setGeneropersonaid(this.generopersonaid.toDto());
            persona.setNacionalidadid(this.nacionalidadid.toDto());
        }
        persona.setUrlfoto(this.urlfoto);

        return persona;
    }

}