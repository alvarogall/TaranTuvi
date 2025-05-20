package es.uma.taw.tarantuvi.entity;

import es.uma.taw.tarantuvi.dto.DTO;
import es.uma.taw.tarantuvi.dto.PalabraClave;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PALABRACLAVE", schema = "TaranTuvi")
public class PalabraClaveEntity implements Serializable, DTO<PalabraClave> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PALABRACLAVEID", nullable = false)
    private Integer id;

    @Column(name = "PALABRACLAVENOMBRE")
    private String palabraclavenombre;

    @ManyToMany(mappedBy = "palabraClaveList")
    private List<PeliculaEntity> peliculaList;

    public PalabraClave toDto(){
        PalabraClave palabraClave = new PalabraClave();

        palabraClave.setId(this.id);
        palabraClave.setPalabraclavenombre(this.palabraclavenombre);

        return palabraClave;
    }
}