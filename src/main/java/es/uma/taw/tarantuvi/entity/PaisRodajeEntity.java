package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.PaisRodaje;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PAISRODAJE", schema = "TaranTuvi")
public class PaisRodajeEntity implements Serializable, DTO<PaisRodaje> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAISRODAJEID", nullable = false)
    private Integer id;

    @Column(name = "PAISRODAJENOMBRE")
    private String paisrodajenombre;

    @ManyToMany(mappedBy = "paisRodajeList")
    private List<PeliculaEntity> peliculaList;

    public PaisRodaje toDto(){
        PaisRodaje paisRodaje = new PaisRodaje();

        paisRodaje.setId(id);
        paisRodaje.setPaisrodajenombre(this.paisrodajenombre);
        paisRodaje.setPeliculaList(this.peliculaList);

        return paisRodaje;
    }
}