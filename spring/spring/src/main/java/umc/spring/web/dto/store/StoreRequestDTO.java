package umc.spring.web.dto.store;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class StoreRequestDTO {
    @Getter
    public static class AddMissionRequestDTO{
        @NotNull
        @Range(max = 1_000) // default min = 0L
        private Integer reward;
        @NotNull
        private LocalDate deadline;
        @NotBlank
        @Length(min = 20, max = 100)
        private String missionSpec;
    }
}
