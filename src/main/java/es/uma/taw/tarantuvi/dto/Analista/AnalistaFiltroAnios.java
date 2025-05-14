package es.uma.taw.tarantuvi.dto.Analista;

import lombok.Data;

@Data
public class AnalistaFiltroAnios {
    private Integer anios;
    private Double cantidadMinima;
    private Double cantidadMaxima;
    private Integer idioma;
    private Integer genero;
    private String ordenCampo;
    private String ordenTipo;
    private String ordenCampoAuxiliar;
    private String ordenTipoAuxiliar;

}

