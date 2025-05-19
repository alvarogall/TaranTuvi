package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.IdiomaHablado;
import es.uma.taw.tarantuvi.dto.Pelicula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "IDIOMAHABLADO", schema = "TaranTuvi")
public class IdiomaHabladoEntity implements Serializable, DTO<IdiomaHablado> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDIOMAHABLADOID", nullable = false)
    private Integer id;

    @Column(name = "IDIOMAHABLADONOMBRE")
    private String idiomahabladonombre;

    @ManyToMany(mappedBy = "idiomaHabladoList")
    private List<PeliculaEntity> peliculaList;

    public IdiomaHablado toDto(){
        IdiomaHablado idiomaHablado = new IdiomaHablado();

        idiomaHablado.setId(this.id);
        idiomaHablado.setIdiomahabladonombre(this.idiomahabladonombre);

        return idiomaHablado;
    }
}