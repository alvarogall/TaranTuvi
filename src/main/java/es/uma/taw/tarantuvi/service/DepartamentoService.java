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

    public Departamento buscarDepartamento(Integer id) {
        DepartamentoEntity departamento = this.DepartamentoRepository.findById(id).orElse(new DepartamentoEntity());

        if (departamento != null) {
            return departamento.toDto();
        } else {
            return new Departamento();
        }
    }

    public void guardarDepartamento(Departamento dtoDepartamento) {
        DepartamentoEntity departamento;

        if (dtoDepartamento.getId() != null) {
            departamento = this.DepartamentoRepository.findById(dtoDepartamento.getId()).orElse(new DepartamentoEntity());
        } else {
            departamento = new DepartamentoEntity();
        }

        departamento.setDepartamentonombre(dtoDepartamento.getDepartamentonombre());
        this.DepartamentoRepository.save(departamento);
    }

    public void borrarDepartamento(Integer id) {
        this.DepartamentoRepository.deleteById(id);
    }
}
