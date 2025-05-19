package es.uma.taw.tarantuvi.dto;

import java.util.List;

import lombok.Data;

@Data
public class ActorResumen {
    private Integer id;
    private String urlfoto;
    private String nombre;
    private String nombreGenero;
    private String nombreNacionalidad;
    private List<ActuacionResumen> actuaciones;
    private List<TrabajoResumen> trabajos;
}
