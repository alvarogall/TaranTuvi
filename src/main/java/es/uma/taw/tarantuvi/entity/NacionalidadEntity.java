/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.GeneroPersona;
import es.uma.taw.tarantuvi.dto.Nacionalidad;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "NACIONALIDAD", schema = "TaranTuvi")
public class NacionalidadEntity implements Serializable, DTO<Nacionalidad> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NACIONALIDADID", nullable = false)
    private Integer id;

    @Column(name = "NACIONALIDADNOMBRE", length = 50)
    private String nacionalidadnombre;

    public Nacionalidad toDto(){
        Nacionalidad nacionalidad = new Nacionalidad();

        nacionalidad.setId(this.id);
        nacionalidad.setNacionalidadnombre(this.nacionalidadnombre);

        return nacionalidad;
    }

}