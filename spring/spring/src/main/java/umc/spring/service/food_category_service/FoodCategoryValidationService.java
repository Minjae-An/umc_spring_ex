package umc.spring.service.food_category_service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.repository.FoodCategoryRepository;

@Service
@RequiredArgsConstructor
public class FoodCategoryValidationService {
    private final FoodCategoryRepository foodCategoryRepository;

    public boolean allFoodCategoriesExistsById(List<Long> values) {
        return values.stream().allMatch(foodCategoryRepository::existsById);
    }
}
