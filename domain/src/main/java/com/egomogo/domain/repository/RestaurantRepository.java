package com.egomogo.domain.repository;

import com.egomogo.domain.dto.IRestaurantDistanceDto;
import com.egomogo.domain.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    @Query(
            value = "SELECT *, " +
                    "ST_Distance_Sphere(POINT(:userX, :userY), POINT(r.x, r.y)) as distance " +
                    "FROM restaurant r " +
                    "HAVING distance <= :distanceLimit " +
                    "ORDER BY RAND(:seed) ",
            nativeQuery = true
    )
    Slice<IRestaurantDistanceDto> findByRandomAndDistance(Long seed, Double userX, Double userY, Integer distanceLimit, Pageable pageable);

    List<Restaurant> findByMenusIsNull();

}