package com.legends.taskFlow.repositorty;

import com.legends.taskFlow.model.entity.Department;
import com.legends.taskFlow.model.entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    boolean existsByCityAndCountry(String city, String country);

    Optional<Location> findLocationByCityAndCountry(String city, String country);

}
