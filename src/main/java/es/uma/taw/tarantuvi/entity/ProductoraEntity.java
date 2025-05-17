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

    @ManyToMany(mappedBy = "productoraList")
    private List<PeliculaEntity> peliculaList;

    public Productora toDto(){
        Productora productora = new Productora();

        productora.setId(this.id);
        productora.setProductoranombre(this.productoranombre);
        productora.setPeliculaList(this.peliculaList);

        return productora;
    }
}