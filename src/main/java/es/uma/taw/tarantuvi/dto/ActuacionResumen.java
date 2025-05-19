package es.uma.taw.tarantuvi.dto;

import lombok.Data;

@Data
public class ActuacionResumen {
    private Integer id;
    private String personaje;
    private Integer orden;
    private PeliculaResumen pelicula;
    private String persona;
}
