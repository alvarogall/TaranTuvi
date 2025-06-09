/**
 * @author Jesús Repiso
 * @author Pablo Gámez
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneroPelicula {
    protected Integer id;
    protected String generonombre;
    protected String descripcion;
}
