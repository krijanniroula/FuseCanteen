package com.fusemachines.fusecanteen;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.services.FoodItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FuseCanteenApplicationTests {

	@Autowired
	FoodItemService foodItemService;

	@Test
	void contextLoads() {
		FoodItem foodItem = foodItemService.getFoodItemByid("5f5db40d9cfde45dc6939352");
		System.out.println(foodItem);

	}

}
