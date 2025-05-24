/**
 * @author Jesús Repiso
 * @author Pablo Gámez
 * @author Pablo Gámez
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

    public Nacionalidad buscarNacionalidad(Integer id) {
        NacionalidadEntity nacionalidad = this.nacionalidadRepository.findById(id).orElse(new NacionalidadEntity());
        if (nacionalidad != null) {
            return nacionalidad.toDto();
        } else {
            return new Nacionalidad();
        }
    }

    public void guardarNacionalidad(Nacionalidad dtoNacionalidad) {
        NacionalidadEntity nacionalidad;

        if (dtoNacionalidad.getId() != null) {
            nacionalidad = this.nacionalidadRepository.findById(dtoNacionalidad.getId()).orElse(new NacionalidadEntity());
        } else {
            nacionalidad = new NacionalidadEntity();
        }

        nacionalidad.setNacionalidadnombre(dtoNacionalidad.getNacionalidadnombre());
        this.nacionalidadRepository.save(nacionalidad);
    }

    public void borrarNacionalidad(Integer id) {
        this.nacionalidadRepository.deleteById(id);
    }
}
