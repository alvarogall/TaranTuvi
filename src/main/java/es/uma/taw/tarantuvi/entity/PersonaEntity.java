package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.Actor;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PERSONA", schema = "TaranTuvi")
public class PersonaEntity {
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
        }

        if(this.actuacionList != null){
            List<Integer> idsPeliculas = new ArrayList<Integer>();
            for(ActuacionEntity a : this.actuacionList){
                idsPeliculas.add(a.getPeliculaid().getId());
            }
            actor.setPeliculas(idsPeliculas);

            List<Integer> idsActuaciones = new ArrayList<Integer>();
            for(ActuacionEntity a : this.actuacionList){
                idsActuaciones.add(a.getId());
            }
            actor.setActuaciones(idsActuaciones);

            List<Integer> idsGeneros = new ArrayList<Integer>();
            for(ActuacionEntity a : this.actuacionList){
                for(GeneroPeliculaEntity g : a.getPeliculaid().getGeneroPeliculaList()){
                    if(!idsGeneros.contains(g.getId())){
                        idsGeneros.add(g.getId());
                    }
                }
            }
            actor.setGenerosPeliculas(idsGeneros);
        }

        return actor;
    }

}