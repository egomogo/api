package com.egomogo.api.service.appservice;

import com.egomogo.api.service.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RestaurantServiceImplUnitTest {

//    @InjectMocks
    @Autowired
    private RestaurantServiceImpl restaurantService;
//    @Mock
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("매장 랜덤 조회")
    void test_getRandomRestaurants() {
        // given
        restaurantService.getRandomRestaurants(1234L, null, "137.123412", "35.123312", "1000",
                PageRequest.of(0, 10));
        // when
        // then
    }
}