package com.egomogo.api.service.repository;

import com.egomogo.api.service.dto.restaurant.IRestaurantDistanceDto;
import com.egomogo.api.service.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    @Query(
            value = "SELECT *, " +
                    "ST_Distance_Sphere(POINT(:userX, :userY), POINT(r.x, r.y)) as distance " +
                    "FROM restaurant r " +
                    "HAVING distance <= :distance " +
                    "ORDER BY RAND(:seed) ",
            nativeQuery = true
    )
    Slice<IRestaurantDistanceDto> findByRandomAndDistance(Long seed, Double userX, Double userY, Integer distance, Pageable pageable);

}