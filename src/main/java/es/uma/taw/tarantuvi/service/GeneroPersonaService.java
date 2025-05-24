/**
 * @author Jesús Repiso
 */
package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.GeneroPersonaRepository;
import es.uma.taw.tarantuvi.dto.GeneroPersona;
import es.uma.taw.tarantuvi.entity.GeneroPersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroPersonaService extends DTOService<GeneroPersona, GeneroPersonaEntity>{

    @Autowired
    private GeneroPersonaRepository generoPersonaRepository;

    public List<GeneroPersona> listarGenerosPersonas () {
        List<GeneroPersonaEntity> entities = this.generoPersonaRepository.findAll();
        return this.entity2DTO(entities);
    }
}
