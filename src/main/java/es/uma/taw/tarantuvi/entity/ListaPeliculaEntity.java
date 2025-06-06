/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.ListaPelicula;
import es.uma.taw.tarantuvi.dto.Pelicula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "LISTAPELICULA", schema = "TaranTuvi")
public class ListaPeliculaEntity implements Serializable, DTO<ListaPelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISTAPELICULAID", nullable = false)
    private Integer id;

    @Column(name = "LISTAPELICULANOMBRE")
    private String listapeliculanombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIOID")
    private UsuarioEntity usuarioid;

    @ManyToMany
    @JoinTable(
            name = "PELICULALISTAPELICULA",
            joinColumns = @JoinColumn(name = "LISTAPELICULAID", referencedColumnName = "LISTAPELICULAID"),
            inverseJoinColumns = @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID")
    )
    private List<PeliculaEntity> peliculaList;


    public ListaPelicula toDto(){
        ListaPelicula dto = new ListaPelicula();
        dto.setListaPeliculaId(this.id);
        dto.setListaPeliculaNombre(this.listapeliculanombre);
        dto.setUsuarioId(this.usuarioid.getId());
        List<Pelicula> peliculasDto = this.peliculaList.stream()
                .map(PeliculaEntity::toDto)
                .collect(Collectors.toList());

        dto.setPeliculaList(peliculasDto);

        return dto;
    }

}