package com.egomogo.api.service.repository;

import com.egomogo.api.service.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    List<Menu> findTop3ByRestaurantId(String restaurantId);

}
