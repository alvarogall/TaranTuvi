/*
User: jesus
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.IdiomaHabladoRepository;
import es.uma.taw.tarantuvi.dto.IdiomaHablado;
import es.uma.taw.tarantuvi.entity.IdiomaHabladoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdiomaHabladoService extends DTOService<IdiomaHablado, IdiomaHabladoEntity>{

    @Autowired
    private IdiomaHabladoRepository idiomaHabladoRepository;

    public List<IdiomaHablado> listarIdiomasHablados () {
        List<IdiomaHabladoEntity> entities = this.idiomaHabladoRepository.findAll();
        return this.entity2DTO(entities);
    }

    public IdiomaHablado buscarIdiomaHablado(Integer id) {
        IdiomaHabladoEntity idiomaHablado = this.idiomaHabladoRepository.findById(id).orElse(null);
        if(idiomaHablado != null){
            return idiomaHablado.toDto();
        }else{
            return new IdiomaHablado();
        }
    }

    public void guardarIdiomaHablado(IdiomaHablado dtoIdiomaHablado) {
        IdiomaHabladoEntity idiomaHablado;

        if (dtoIdiomaHablado.getId() != null) {
            idiomaHablado = this.idiomaHabladoRepository.findById(dtoIdiomaHablado.getId()).orElse(new IdiomaHabladoEntity());
        } else {
            idiomaHablado = new IdiomaHabladoEntity();
        }

        idiomaHablado.setIdiomahabladonombre(dtoIdiomaHablado.getIdiomahabladonombre());
        this.idiomaHabladoRepository.save(idiomaHablado);
    }

    public void borrarIdiomaHablado(Integer id) {
        this.idiomaHabladoRepository.deleteById(id);
    }
}
