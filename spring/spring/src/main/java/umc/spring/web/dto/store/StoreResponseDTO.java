package umc.spring.web.dto.store;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddMissionResponseDTO{
        private Long storeId;
        private Long missionId;
        private LocalDateTime createdAt;
    }
}
