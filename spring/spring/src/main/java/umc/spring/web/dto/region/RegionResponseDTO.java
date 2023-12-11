package umc.spring.web.dto.region;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RegionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddStoreToRegionResponseDTO{
        private Long regionId;
        private Long storeId;
        private LocalDateTime createdAt;
    }
}
