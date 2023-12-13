package umc.spring.service.food_category_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.FoodCategoryHandler;
import umc.spring.domain.FoodCategory;
import umc.spring.repository.FoodCategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryQueryService {
    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategory findById(Long foodCategoryId) {
        return foodCategoryRepository.findById(foodCategoryId)
                .orElseThrow(() -> new FoodCategoryHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND));
    }
}
