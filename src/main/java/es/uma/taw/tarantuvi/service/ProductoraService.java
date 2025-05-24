/**
 * @author Jesús Repiso
 * @author Alejandro Cueto
 * @author Pablo Gámez
 */
package es.uma.taw.tarantuvi.service;

import es.uma.taw.tarantuvi.dao.ProductoraRepository;
import es.uma.taw.tarantuvi.dto.Productora;
import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductoraService extends DTOService<Productora, ProductoraEntity>{

    @Autowired
    private ProductoraRepository productoraRepository;

    public List<Productora> listarProductoras () {
        List<ProductoraEntity> entities = this.productoraRepository.findAll();
        return this.entity2DTO(entities);
    }

    public Productora buscarProductora(Integer id) {
        ProductoraEntity productora = this.productoraRepository.findById(id).orElse(new ProductoraEntity());
        if (productora != null) {
            return productora.toDto();
        } else {
            return new Productora();
        }
    }

    public void guardarProductora(Productora dtoProductora) {
        ProductoraEntity productora;

        if (dtoProductora.getId() != null) {
            productora = this.productoraRepository.findById(dtoProductora.getId()).orElse(new ProductoraEntity());
        } else {
            productora = new ProductoraEntity();
        }

        productora.setProductoranombre(dtoProductora.getProductoranombre());
        this.productoraRepository.save(productora);
    }

    public void borrarProductora(Integer id) {
        this.productoraRepository.deleteById(id);
    }

    public List<Object[]> obtenerProductorasConNotasMediasYFiltrosOrdenadas(Double cantidadMinima, Double cantidadMaxima, String ordenCampo, String ordenTipo) {

        Double presupuestoMin = null, presupuestoMax = null, recaudacionMin = null, recaudacionMax = null;
        if(ordenCampo != null) {
            switch (ordenCampo){
                case "presupuesto" -> {
                    presupuestoMin = cantidadMinima; presupuestoMax = cantidadMaxima;
                }
                case "recaudacion" -> {
                    recaudacionMin = cantidadMinima; recaudacionMax = cantidadMaxima;
                }
            }
        }

        List<Object[]> productoras = productoraRepository.getProductorasConNotasMediasYFiltros(presupuestoMin, presupuestoMax, recaudacionMin, recaudacionMax);

        if (ordenCampo != null && ordenTipo != null) {
            Comparator<Object[]> comparator = switch (ordenCampo) {
                case "productora" -> Comparator.comparing(a -> ((ProductoraEntity) a[0]).getProductoranombre());
                case "peliculas" -> Comparator.comparing(a -> ((ProductoraEntity) a[0]).getPeliculaList().size());
                case "nota" -> Comparator.comparing(a -> (a[1] != null ? (Double) a[1] : 0.0));
                case "presupuesto" -> Comparator.comparing(a -> (a[2] != null ? (Double) a[2] : 0.0));
                case "recaudacion" -> Comparator.comparing(a -> (a[3] != null ? (Double) a[3] : 0.0));
                default -> null;
            };

            if (comparator != null) {
                if ("DESC".equalsIgnoreCase(ordenTipo)) {
                    comparator = comparator.reversed();
                }
                productoras.sort(comparator);
            }


        }
        return productoras;
    }

    public int contarPeliculasAsociadasProductora() {
        return productoraRepository.countPeliculasAsociadasProductora();
    }
}
