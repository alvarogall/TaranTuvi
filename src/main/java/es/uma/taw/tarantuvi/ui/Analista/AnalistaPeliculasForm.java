package es.uma.taw.tarantuvi.ui.Analista;

import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AnalistaPeliculasForm {
    protected PeliculaEntity pelicula;
    protected BigDecimal nota;
}
