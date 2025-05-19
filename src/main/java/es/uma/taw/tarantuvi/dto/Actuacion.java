/*
User: jesus
*/

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Actuacion {
    protected Integer id;
    protected Persona personaid;
    protected Integer peliculaid;
    protected GeneroPersona generopersonaid;
    protected String personaje;
    protected Integer orden;

    protected String label; //Campo para sacar por pantalla nombre + personaje
}
