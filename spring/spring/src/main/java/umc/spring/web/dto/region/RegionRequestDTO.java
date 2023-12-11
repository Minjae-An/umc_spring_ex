package umc.spring.web.dto.region;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class RegionRequestDTO {
    @Getter
    public static class AddStoreToRegionRequestDTO{
        @NotBlank
        @Length(min = 1, max = 50)
        private String name;
        @NotBlank
        @Length(min = 10, max = 50)
        private String address;
    }
}
