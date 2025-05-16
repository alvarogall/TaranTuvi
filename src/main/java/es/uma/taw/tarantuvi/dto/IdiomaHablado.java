/*
User: jesus
 */

package es.uma.taw.tarantuvi.dto;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IdiomaHablado {
    protected Integer id;
    protected String idiomahabladonombre;
    protected List<PeliculaEntity> peliculaList;
}
