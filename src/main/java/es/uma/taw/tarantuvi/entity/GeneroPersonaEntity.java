package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.GeneroPersona;
import es.uma.taw.tarantuvi.dto.PaisRodaje;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "GENEROPERSONA", schema = "TaranTuvi")
public class GeneroPersonaEntity implements Serializable, DTO<GeneroPersona> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENEROPERSONAID", nullable = false)
    private Integer id;

    @Column(name = "GENEROPERSONANOMBRE")
    private String generopersonanombre;

    public GeneroPersona toDto(){
        GeneroPersona generoPersona = new GeneroPersona();

        generoPersona.setId(id);
        generoPersona.setGeneropersonanombre(generopersonanombre);

        return generoPersona;
    }
}