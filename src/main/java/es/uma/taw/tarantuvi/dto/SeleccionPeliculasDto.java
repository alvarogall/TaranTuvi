/**
 * @author Máximo Prados
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class SeleccionPeliculasDto {
    private Integer listaId;
    private List<Integer> peliculaIds;
}
