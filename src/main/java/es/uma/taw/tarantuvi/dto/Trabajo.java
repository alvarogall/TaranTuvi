/*
User: jesus
 */

package es.uma.taw.tarantuvi.dto;

import es.uma.taw.tarantuvi.entity.DepartamentoEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
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
