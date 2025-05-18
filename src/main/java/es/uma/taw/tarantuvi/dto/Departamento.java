package es.uma.taw.tarantuvi.dto;

import es.uma.taw.tarantuvi.entity.TrabajoEntity;
import lombok.Data;

import java.util.List;

@Data
public class Departamento {
    protected Integer id;
    protected String departamentonombre;
    protected List<TrabajoEntity> trabajoList;
}
