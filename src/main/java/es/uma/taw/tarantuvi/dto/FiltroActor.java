/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import java.util.List;

@Data
public class FiltroActor {
    private String nombre;
    private Integer genero;
    private Integer nacionalidad;
    private List<Integer> peliculas;
}