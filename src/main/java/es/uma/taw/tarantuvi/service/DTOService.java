package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public abstract class DTOService<DTOClass, EntityClass> {

    protected List<DTOClass> entity2DTO (List<EntityClass> entidades) {
        List<DTOClass> lista = new ArrayList<>();
        for (EntityClass entidad : entidades) {
            DTO<DTOClass> clase = (DTO<DTOClass>) entidad;
            lista.add(clase.toDto());
        }
        return lista;
    }

}
