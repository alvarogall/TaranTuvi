/**
 * @author Pablo Gámez
 */

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

    public PalabraClave buscarPalabraClave(Integer id) {
        PalabraClaveEntity palabraClave = this.palabraClaveRepository.findById(id).orElse(new PalabraClaveEntity());

        if (palabraClave != null) {
            return palabraClave.toDto();
        } else {
            return new PalabraClave();
        }
    }

    public void guardarPalabraClave(PalabraClave dtoPalabraClave) {
        PalabraClaveEntity palabraClave;

        if (dtoPalabraClave.getId() != null) {
            palabraClave = this.palabraClaveRepository.findById(dtoPalabraClave.getId()).orElse(new PalabraClaveEntity());
        } else {
            palabraClave = new PalabraClaveEntity();
        }

        palabraClave.setPalabraclavenombre(dtoPalabraClave.getPalabraclavenombre());
        this.palabraClaveRepository.save(palabraClave);
    }

    public void borrarPalabraClave(Integer id) {
        this.palabraClaveRepository.deleteById(id);
    }
}
