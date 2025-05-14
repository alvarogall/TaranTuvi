package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.PersonaRepository;
import es.uma.taw.tarantuvi.dto.Actor;
import es.uma.taw.tarantuvi.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersonaService extends DTOService<Actor, PersonaEntity> {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Actor> listarActores () {
        List<PersonaEntity> entities = this.personaRepository.findAll();
        return this.entity2DTO(entities);
    }
}
