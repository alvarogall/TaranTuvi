/**
 * @author Jesús Repiso
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Actor {
    protected Integer id;
    protected String urlfoto;
    protected String nombre;
    protected Integer genero;
    protected String nombrePersonaje;
    protected Integer nacionalidad;
    protected List<Integer> peliculas;
    protected List<Integer> actuaciones;

    protected String nombreGenero;
    protected String nombreNacionalidad;
}
