/*
User: jesus
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.ProductoraRepository;
import es.uma.taw.tarantuvi.dto.Productora;
import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoraService extends DTOService<Productora, ProductoraEntity>{

    @Autowired
    private ProductoraRepository productoraRepository;

    public List<Productora> listarProductoras () {
        List<ProductoraEntity> entities = this.productoraRepository.findAll();
        return this.entity2DTO(entities);
    }

    public void borrarProductora(Integer id) {
        this.productoraRepository.deleteById(id);
    }
}
