/**
 * @author Jesús Repiso
 * @author Alejandro Cueto
 * @author Pablo Gámez
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

    public PaisRodaje buscarPaisRodaje(Integer id) {
        PaisRodajeEntity paisRodaje = this.paisRodajeRepository.findById(id).orElse(new PaisRodajeEntity());
        if (paisRodaje != null) {
            return paisRodaje.toDto();
        } else {
            return new PaisRodaje();
        }
    }

    public void guardarPaisRodaje(PaisRodaje dtoPaisRodaje) {
        PaisRodajeEntity paisRodaje;

        if (dtoPaisRodaje.getId() != null) {
            paisRodaje = this.paisRodajeRepository.findById(dtoPaisRodaje.getId()).orElse(new PaisRodajeEntity());
        } else {
            paisRodaje = new PaisRodajeEntity();
        }

        paisRodaje.setPaisrodajenombre(dtoPaisRodaje.getPaisrodajenombre());
        this.paisRodajeRepository.save(paisRodaje);
    }

    public void borrarPaisRodaje(Integer id) {
        this.paisRodajeRepository.deleteById(id);
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
