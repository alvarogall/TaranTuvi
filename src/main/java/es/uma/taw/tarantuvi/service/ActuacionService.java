/**
 * @author Jesús Repiso
 * @author Alejandro Cueto
 */
package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.ActuacionRepository;
import es.uma.taw.tarantuvi.dto.Actuacion;
import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ActuacionService extends DTOService<Actuacion, ActuacionEntity>{
    @Autowired
    private ActuacionRepository actuacionRepository;

    public List<Actuacion> listarActuaciones () {
        List<ActuacionEntity> entities = this.actuacionRepository.findAll();
        return this.entity2DTO(entities);
    }

    public void borrarActuacion(Integer id) {
        this.actuacionRepository.deleteById(id);
    }

    public List<Object[]> obtenerTasaFemeninaGlobal() {
        return actuacionRepository.getFemalePercentageGlobal();
    }

    public List<Object[]> obtenerTasaFemeninaPorPelicula(String ordenCampo, String ordenTipo) {
        List<Object[]> datos = actuacionRepository.getFemalePercentagePerMovie();

        if (ordenCampo != null && ordenTipo != null) {
            Comparator<Object[]> comparator = switch (ordenCampo) {
                case "titulo" -> Comparator.comparing(a -> (String) a[0]);
                case "actrices" -> Comparator.comparing(a -> (Double) a[1]);
                case "actores" -> Comparator.comparing(a -> (Double) a[2]);
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(ordenTipo)) {
                    comparator = comparator.reversed();
                }
                datos.sort(comparator);
            }
        }

        return datos;
    }

    public List<Object[]> obtenerTasaPorNacionalidad(String ordenCampoAuxiliar, String ordenTipoAuxiliar) {
        List<Object[]> datos = actuacionRepository.getCountryCount();

        if (ordenCampoAuxiliar != null && ordenTipoAuxiliar != null) {
            Comparator<Object[]> comparator = switch (ordenCampoAuxiliar) {
                case "nacionalidad" -> Comparator.comparing(a -> (String) a[0]);
                case "cantidad" -> Comparator.comparing(a -> (Long) a[1]);
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(ordenTipoAuxiliar)) {
                    comparator = comparator.reversed();
                }
                datos.sort(comparator);
            }
        }

        return datos;
    }

    public List<Object[]> obtenerNumeroGeneros() {
        return actuacionRepository.getFemaleMaleCounts();
    }

    public Long obtenerTotalActores() {
        return actuacionRepository.getActorCount();
    }

    public List<Actuacion> obtenerActoresDePelicula(Integer peliculaId) {
        List<ActuacionEntity> entities = actuacionRepository.getActoresPelicula(peliculaId);
        return entity2DTO(entities);
    }

}
