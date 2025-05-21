/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;

@Data
public class TrabajoResumen {
    protected Integer id;
    protected String persona;
    protected String pelicula;
    protected String trabajo;
    protected String departamento;
}