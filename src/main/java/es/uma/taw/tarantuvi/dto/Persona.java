/*
User: jesus
*/

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Persona {
    protected Integer id;
    protected String nombre;
    protected GeneroPersona generopersonaid;
    protected Nacionalidad nacionalidadid;
    protected String urlfoto;
}
