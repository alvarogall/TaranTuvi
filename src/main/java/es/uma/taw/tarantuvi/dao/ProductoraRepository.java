package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ProductoraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoraRepository extends JpaRepository<ProductoraEntity, Integer> {
    @Query("Select p from ProductoraEntity p where p.productoranombre = :trim")
    ProductoraEntity findByNombre(String trim);
}
