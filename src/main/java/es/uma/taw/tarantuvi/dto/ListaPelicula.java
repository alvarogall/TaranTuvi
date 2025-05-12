package es.uma.taw.tarantuvi.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListaPelicula {
    Integer listaPeliculaId;
    String listaPeliculaNombre;
    Integer usuarioId;

}
