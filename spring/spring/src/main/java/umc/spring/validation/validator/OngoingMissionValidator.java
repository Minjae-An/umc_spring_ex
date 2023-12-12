package umc.spring.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.service.member_service.MemberQueryService;
import umc.spring.validation.annotation.OngoingMission;
import umc.spring.web.dto.MemberRequestDTO.ChallengeMissionRequestDto;

@Component
@RequiredArgsConstructor
public class OngoingMissionValidator implements ConstraintValidator<OngoingMission, ChallengeMissionRequestDto> {

    private final MemberQueryService memberQueryService;

    @Override
    public void initialize(OngoingMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ChallengeMissionRequestDto value, ConstraintValidatorContext context) {
        boolean isValid = memberQueryService.isOngoingMission(value);

        if(isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ONGOING_MISSION.toString())
                    .addConstraintViolation();
        }

        return !isValid;
    }
}
