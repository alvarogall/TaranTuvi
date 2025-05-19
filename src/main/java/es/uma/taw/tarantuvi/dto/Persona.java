package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Persona {
    protected Integer id;
    protected String nombre;
    protected GeneroPersona generopersonaid;
    protected Nacionalidad nacionalidadid;
    protected String urlfoto;
}
