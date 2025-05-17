/*
User: jesus
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.NacionalidadRepository;
import es.uma.taw.tarantuvi.dto.Nacionalidad;
import es.uma.taw.tarantuvi.entity.NacionalidadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NacionalidadService extends DTOService<Nacionalidad, NacionalidadEntity>{

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    public List<Nacionalidad> listarNacionalidades() {
        List<NacionalidadEntity> entities = this.nacionalidadRepository.findAll();
        return this.entity2DTO(entities);
    }
}
