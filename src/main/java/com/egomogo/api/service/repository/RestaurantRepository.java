package com.egomogo.api.service.repository;

import com.egomogo.api.service.dto.restaurant.IRestaurantDto;
import com.egomogo.api.service.entity.Restaurant;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    @Query(
            value = "SELECT *, " +
                    "ST_Distance_Sphere(POINT(:userX, :userY), POINT(r.x, r.y)) as distance " +
                    "FROM restaurant r INNER JOIN menu m ON m.restaurant_id = r.id " +
                    "HAVING distance <= :distance " +
                    "ORDER BY RAND(:seed) ",
            nativeQuery = true
    )
    Slice<IRestaurantDto> findRestaurantDistanceByRandomAndDistanceLimit(Double userX, Double userY, Integer distance, Long seed);

}