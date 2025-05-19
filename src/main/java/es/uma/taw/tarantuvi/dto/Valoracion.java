package es.uma.taw.tarantuvi.dto;

<<<<<<< Updated upstream
import es.uma.taw.tarantuvi.entity.PeliculaEntity;
import es.uma.taw.tarantuvi.entity.UsuarioEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Valoracion {
    protected Integer id;
    protected PeliculaEntity peliculaid;
    protected UsuarioEntity usuarioid;
    protected BigDecimal nota;
}
=======
import lombok.Data;

@Data
public class Valoracion {
    protected Integer nota;
}
>>>>>>> Stashed changes
