/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PeliculaResumen {
    private Integer id;
    private String titulooriginal;
    private String urlcaratula;
    private String estado;
    private String descripcion;
    private Integer duracion;
    private LocalDate fechaestreno;
    private List<ActuacionResumen> actuaciones;
}
