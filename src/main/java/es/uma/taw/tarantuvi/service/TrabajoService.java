/**
 * @author Jesús Repiso
 * @author Alejandro Cueto
 */
package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.TrabajoRepository;
import es.uma.taw.tarantuvi.dto.Trabajo;
import es.uma.taw.tarantuvi.entity.TrabajoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrabajoService extends DTOService<Trabajo, TrabajoEntity> {

    @Autowired
    private TrabajoRepository trabajoRepository;

    public List<Trabajo> listarTrabajos () {
        List<TrabajoEntity> entities = this.trabajoRepository.findAll();
        return this.entity2DTO(entities);
    }

    public void borrarTrabajo(Integer id) {
        this.trabajoRepository.deleteById(id);
    }
}
