/**
 * @author Alejandro Cueto
 */

package es.uma.taw.tarantuvi.service;


import es.uma.taw.tarantuvi.dao.ValoracionRepository;
import es.uma.taw.tarantuvi.dto.Valoracion;
import es.uma.taw.tarantuvi.entity.ValoracionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ValoracionService extends DTOService<Valoracion, ValoracionEntity> {

    @Autowired
    private ValoracionRepository valoracionRepository;


}
