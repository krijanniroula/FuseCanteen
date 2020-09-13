package com.fusemachines.fusecanteen;

import com.fusemachines.fusecanteen.models.FoodItem;
import com.fusemachines.fusecanteen.services.FoodItemService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class FuseCanteenApplicationTests {

	@Mock
	FoodItemService foodItemService;

	@Test
	void contextLoads(){
		Mockito.when(foodItemService.getAllFoodItems()).thenReturn( Stream.of(new FoodItem("123","Samosa",121)).collect(Collectors.toList()));
		assertEquals(1,foodItemService.getAllFoodItems().size());

	}

}
