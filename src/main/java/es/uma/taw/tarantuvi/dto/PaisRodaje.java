/**
 * @author Jesús Repiso
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PaisRodaje {
    protected Integer id;
    protected String paisrodajenombre;
    protected Integer numeroPeliculas;
}
