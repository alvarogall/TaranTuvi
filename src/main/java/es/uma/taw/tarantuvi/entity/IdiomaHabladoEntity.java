package es.uma.taw.tarantuvi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "IDIOMAHABLADO", schema = "TaranTuvi")
public class IdiomaHabladoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDIOMAHABLADOID", nullable = false)
    private Integer id;

    @Column(name = "IDIOMAHABLADONOMBRE")
    private String idiomahabladonombre;

    @ManyToMany(mappedBy = "idiomaHabladoList")
    private List<PeliculaEntity> peliculaList;

    public void addPeliculaId(PeliculaEntity pelicula) {
        this.peliculaList.add(pelicula);
    }

    public void setIdiomaHabladoId(IdiomaHabladoEntity idioma) {
        this.id = idioma.getId();
    }
}