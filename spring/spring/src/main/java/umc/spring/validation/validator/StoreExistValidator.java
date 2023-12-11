package umc.spring.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.service.store_service.StoreQueryService;
import umc.spring.validation.annotation.ExistStores;

@Component
@RequiredArgsConstructor
public class StoreExistValidator implements ConstraintValidator<ExistStores, Long> {
    private final StoreQueryService service;
    @Override
    public void initialize(ExistStores constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = service.isStoreExist(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.STORE_NOT_FOUND.toString())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
