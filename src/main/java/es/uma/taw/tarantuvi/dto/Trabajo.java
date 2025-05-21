/*
User: jesus
 */

package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Trabajo {
    protected Integer id;
    protected Persona personaid;
    protected Integer peliculaid;
    protected String trabajonombre;
    protected Integer departamentoid;

    protected String label; //Campo para sacar por pantalla nombre + trabajo
}
