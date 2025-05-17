/*
User: jesus
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.PaisRodajeRepository;
import es.uma.taw.tarantuvi.dto.PaisRodaje;
import es.uma.taw.tarantuvi.entity.PaisRodajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisRodajeService extends DTOService<PaisRodaje, PaisRodajeEntity>{

    @Autowired
    private PaisRodajeRepository paisRodajeRepository;

    public List<PaisRodaje> listarPaisesRodaje () {
        List<PaisRodajeEntity> entities = this.paisRodajeRepository.findAll();
        return this.entity2DTO(entities);
    }
}
