/**
 * @author Jesús Repiso
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.Productora;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTORA", schema = "TaranTuvi")
public class ProductoraEntity implements Serializable, DTO<Productora> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTORAID", nullable = false)
    private Integer id;

    @Column(name = "PRODUCTORANOMBRE")
    private String productoranombre;

    @ManyToOne
    @JoinColumn(name = "NACIONALIDADID")
    private NacionalidadEntity nacionalidadid;

    @Column(name = "CEO")
    private String ceo;

    @Column(name = "SEDE")
    private String sede;

    @ManyToMany(mappedBy = "productoraList")
    private List<PeliculaEntity> peliculaList;

    public Productora toDto(){
        Productora productora = new Productora();

        productora.setId(this.id);
        productora.setProductoranombre(this.productoranombre);
        if (this.getPeliculaList() != null) {
            productora.setNumeroPeliculas(this.getPeliculaList().size());
        }
        if (this.nacionalidadid != null) {
            productora.setNacionalidad(this.nacionalidadid.toDto());
        }
        productora.setCeo(this.ceo);
        productora.setSede(this.sede);

        return productora;
    }
}