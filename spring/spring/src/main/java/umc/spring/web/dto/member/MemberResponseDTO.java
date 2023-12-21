package umc.spring.web.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        Long memberId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeMissionResponseDto{
        Long memberId;
        Long memberMissionId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WrittenReviewResponseDto{
        private String storeName;
        private String memberName;
        private Float score;
        private String body;
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OngoingMissionResponseDto{
        private Long missionId;
        private String storeName;
        private Integer reward;
        private LocalDate deadline;
        private String missionSpec;
    }
}
