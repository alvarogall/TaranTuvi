/**
 * @author Máximo Prados
 */

package es.uma.taw.tarantuvi.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PELICULALISTAPELICULA")
@IdClass(PeliculaListaPeliculaId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaListaPeliculaEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "PELICULAID", referencedColumnName = "PELICULAID")
    private PeliculaEntity pelicula;

    @Id
    @ManyToOne
    @JoinColumn(name = "LISTAPELICULAID", referencedColumnName = "LISTAPELICULAID")
    private ListaPeliculaEntity listaPelicula;

}

