/**
 * @author Alejandro Cueto
 */

package es.uma.taw.tarantuvi.dao;

import es.uma.taw.tarantuvi.entity.ActuacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActuacionRepository extends JpaRepository<ActuacionEntity, Integer> {

    @Query("SELECT a.peliculaid.titulooriginal, ROUND(SUM(CASE WHEN a.generopersonaid.generopersonanombre = 'Femenino' THEN 1 ELSE 0 END) * 100.0 / COUNT(a),1), ROUND(100 - SUM(CASE WHEN a.generopersonaid.generopersonanombre = 'Femenino' THEN 1 ELSE 0 END) * 100.0 / COUNT(a),1) " +
            "FROM ActuacionEntity a " +
            "GROUP BY a.peliculaid.titulooriginal")
    List<Object[]> getFemalePercentagePerMovie();

    @Query("SELECT ROUND(SUM(CASE WHEN a.generopersonaid.generopersonanombre = 'Femenino' THEN 1 ELSE 0 END) * 100.0 / COUNT(a),1), ROUND(100 - SUM(CASE WHEN a.generopersonaid.generopersonanombre = 'Femenino' THEN 1 ELSE 0 END) * 100.0 / COUNT(a),1) " +
            "FROM ActuacionEntity a ")
    List<Object[]> getFemalePercentageGlobal();

    @Query("SELECT " +
            "SUM(CASE WHEN a.generopersonaid.generopersonanombre = 'Femenino' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN a.generopersonaid.generopersonanombre = 'Masculino' THEN 1 ELSE 0 END) " +
            "FROM ActuacionEntity a")
    List<Object[]> getFemaleMaleCounts();

    @Query("SELECT a.personaid.nacionalidadid.nacionalidadnombre, COUNT(a) " +
            "FROM ActuacionEntity a " +
            "GROUP BY  a.personaid.nacionalidadid.nacionalidadnombre ")
    List<Object[]> getCountryCount();

    @Query("SELECT COUNT(a.personaid) "+
            "FROM ActuacionEntity a ")
    Long getActorCount();

    @Query("SELECT A FROM ActuacionEntity A WHERE A.peliculaid.id = :peliculaid")
    public List<ActuacionEntity> getActoresPelicula(Integer peliculaid);

}