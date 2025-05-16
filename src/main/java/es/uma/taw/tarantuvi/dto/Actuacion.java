/*
User: jesus
*/

package es.uma.taw.tarantuvi.dto;

import es.uma.taw.tarantuvi.entity.GeneroPersonaEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Actuacion {
    protected Integer id;
    protected PersonaEntity personaid;
    protected PeliculaEntity peliculaid;
    protected GeneroPersonaEntity generopersonaid;
    protected String personaje;
    protected Integer orden;

    protected String label; //Campo para sacar por pantalla nombre + personaje
}
