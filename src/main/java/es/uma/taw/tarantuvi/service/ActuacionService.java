/*
User: jesus
*/

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.ActuacionRepository;
import es.uma.taw.tarantuvi.dto.Actuacion;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActuacionService extends DTOService<Actuacion, ActuacionEntity>{
    @Autowired
    private ActuacionRepository actuacionRepository;

    public List<Actuacion> listarActuaciones () {
        List<ActuacionEntity> entities = this.actuacionRepository.findAll();
        return this.entity2DTO(entities);
    }

    public void borrarActuacion(Integer id) {
        this.actuacionRepository.deleteById(id);
    }
}
