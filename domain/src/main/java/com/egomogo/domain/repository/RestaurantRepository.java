package com.egomogo.domain.repository;

import com.egomogo.domain.dto.IRestaurantDistanceDto;
import com.egomogo.domain.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @Query(
            value = "SELECT *, " +
                    "ST_Distance_Sphere(POINT(:userX, :userY), POINT(r.x, r.y)) as distance " +
                    "FROM restaurant as r " +
                    "INNER JOIN " +
                        "(SELECT distinct restaurant_id FROM restaurant_categories WHERE categories IN (:categories)) as rc " +
                    "ON r.id = rc.restaurant_id " +
                    "HAVING distance <= :distanceLimit " +
                    "ORDER BY RAND(:seed)",
            nativeQuery = true
    )
    Slice<IRestaurantDistanceDto> findByRandomAndDistanceAndCategories(Long seed, Double userX, Double userY, Integer distanceLimit,
                                                                       Collection<String> categories, Pageable pageable);
  
    List<Restaurant> findByMenusIsNull();

    Optional<Restaurant> findByName(String name);
}