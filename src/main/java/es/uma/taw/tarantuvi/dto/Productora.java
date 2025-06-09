/**
 * @author Jesús Repiso
 */


package es.uma.taw.tarantuvi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Productora {
    protected Integer id;
    protected String productoranombre;
    protected Double notaMedia;
    protected Double presupuestoMedio;
    protected Double recaudacionMedia;
    protected Integer numeroPeliculas;
    protected Nacionalidad nacionalidad;
    protected String ceo;
    protected String sede;
}
