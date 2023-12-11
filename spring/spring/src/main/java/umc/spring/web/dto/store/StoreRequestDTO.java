package umc.spring.web.dto.store;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class StoreRequestDTO {
    @Getter
    public static class AddMissionRequestDTO {
        @NotNull
        @Range(max = 1_000) // default min = 0L
        private Integer reward;
        @NotNull
        private LocalDate deadline;
        @NotBlank
        @Length(min = 20, max = 100)
        private String missionSpec;
    }

    @Getter
    public static class AddReviewRequestDTO {
        @NotNull
        private Long memberId;

        @NotBlank
        @Length(min = 1, max = 50)
        private String title;

        @NotBlank
        @Length(min = 10, max = 200)
        private String body;

        @NotNull
        @Range(min = 0, max = 5)
        private Float score;

        // reviewImage 관련 데이터는 S3가 관련되어 있어 편의상 배제하였다.
//        @NotNull
//        @Size(max = 3)
//        List<MultipartFile> reviewImages;
    }
}
