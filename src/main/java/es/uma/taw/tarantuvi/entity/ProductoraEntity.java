package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTORA", schema = "TaranTuvi")
public class ProductoraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTORAID", nullable = false)
    private Integer id;

    @Column(name = "PRODUCTORANOMBRE")
    private String productoranombre;

    @ManyToMany(mappedBy = "productoraList")
    private List<PeliculaEntity> peliculaList;

    public void addPeliculaId(PeliculaEntity pelicula) {
        this.peliculaList.add(pelicula);
    }

    public void setProductoraId(ProductoraEntity productora) {
        this.id = productora.getId();
    }
}