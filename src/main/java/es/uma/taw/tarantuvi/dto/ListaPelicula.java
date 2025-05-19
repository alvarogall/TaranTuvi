package es.uma.taw.tarantuvi.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListaPelicula {
    protected Integer listaPeliculaId;
    protected String listaPeliculaNombre;
    protected Integer usuarioId;

}
