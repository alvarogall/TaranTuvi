package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.DepartamentoRepository;
import es.uma.taw.tarantuvi.dao.TrabajoRepository;
import es.uma.taw.tarantuvi.dto.Departamento;
import es.uma.taw.tarantuvi.dto.Trabajo;
import es.uma.taw.tarantuvi.entity.DepartamentoEntity;
import es.uma.taw.tarantuvi.entity.TrabajoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService extends DTOService<Departamento, DepartamentoEntity> {

    @Autowired
    private DepartamentoRepository DepartamentoRepository;

    public List<Departamento> listarDepartamentos () {
        List<DepartamentoEntity> entities = this.DepartamentoRepository.findAll();
        return this.entity2DTO(entities);
    }
}
