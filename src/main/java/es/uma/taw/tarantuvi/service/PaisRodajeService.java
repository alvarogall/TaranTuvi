/*
User: jesus
 */

package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.PaisRodajeRepository;
import es.uma.taw.tarantuvi.dto.PaisRodaje;
import es.uma.taw.tarantuvi.entity.PaisRodajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PaisRodajeService extends DTOService<PaisRodaje, PaisRodajeEntity> {

    @Autowired
    private PaisRodajeRepository paisRodajeRepository;

    public List<PaisRodaje> listarPaisesRodaje() {
        List<PaisRodajeEntity> entities = this.paisRodajeRepository.findAll();
        return this.entity2DTO(entities);
    }

    public int contarPeliculasAsociadasPaisRodaje() {
        // Aquí debería ir la consulta que devuelve el total de películas asociadas
        return paisRodajeRepository.countPeliculasAsociadasPaisRodaje();
    }

    public List<PaisRodajeEntity> listarPaisesRodajeOrdenados(String ordenCampoAuxiliar, String ordenTipoAuxiliar) {
        List<PaisRodajeEntity> paisesRodaje = paisRodajeRepository.findAll();

        if (ordenCampoAuxiliar != null && ordenTipoAuxiliar != null) {
            Comparator<PaisRodajeEntity> comparator = switch (ordenCampoAuxiliar) {
                case "pais" -> Comparator.comparing(PaisRodajeEntity::getPaisrodajenombre);
                case "peliculas" -> Comparator.comparing(p -> p.getPeliculaList().size());
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(ordenTipoAuxiliar)) {
                    comparator = comparator.reversed();
                }
                paisesRodaje.sort(comparator);
            }
        }

        return paisesRodaje;

    }
}
