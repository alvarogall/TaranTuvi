package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.PalabraClaveRepository;
import es.uma.taw.tarantuvi.dto.PalabraClave;
import es.uma.taw.tarantuvi.entity.PalabraClaveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalabraClaveService extends DTOService<PalabraClave, PalabraClaveEntity> {

    @Autowired
    private PalabraClaveRepository palabraClaveRepository;

    public List<PalabraClave> listarPalabrasClave() {
        List<PalabraClaveEntity> entities = this.palabraClaveRepository.findAll();
        return this.entity2DTO(entities);
    }
}
