package umc.spring.validation.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.service.food_category_service.FoodCategoryValidationService;
import umc.spring.validation.annotation.ExistCategories;

@Component
@RequiredArgsConstructor
public class CategoryExistValidator implements ConstraintValidator<ExistCategories, List<Long>> {
    private final FoodCategoryValidationService foodCategoryValidationService;

    @Override
    public void initialize(ExistCategories constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        boolean isValid = foodCategoryValidationService.allFoodCategoriesExistsById(values);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.FOOD_CATEGORY_NOT_FOUND.toString())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
