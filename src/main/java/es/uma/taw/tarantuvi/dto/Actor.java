package es.uma.taw.tarantuvi.dto;

import es.uma.taw.tarantuvi.entity.GeneroPersonaEntity;
import es.uma.taw.tarantuvi.entity.NacionalidadEntity;
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Actor {
    protected Integer id;
    protected String urlfoto;
    protected String nombre;
    protected Integer genero;
    protected String nombrePersonaje;
    protected Integer nacionalidad;
    protected List<Integer> peliculas;
    protected List<Integer> actuaciones;
}
