/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 * @author Pablo Gámez
 */

package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.GeneroPelicula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GENEROPELICULA", schema = "TaranTuvi")
public class GeneroPeliculaEntity implements Serializable, DTO<GeneroPelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENEROPELICULAID", nullable = false)
    private Integer id;

    @Column(name = "GENERONOMBRE")
    private String generonombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToMany(mappedBy = "generoPeliculaList")
    private List<PeliculaEntity> peliculaList;

    public GeneroPelicula toDto(){
        GeneroPelicula generoPelicula = new GeneroPelicula();

        generoPelicula.setId(id);
        generoPelicula.setGeneronombre(generonombre);
        generoPelicula.setDescripcion(descripcion);

        return generoPelicula;
    }
}