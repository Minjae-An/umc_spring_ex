package umc.spring.web.dto.page;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import umc.spring.validation.annotation.CheckPage;

@Getter
public class PageRequestDto {
    @NotNull
    @CheckPage
    private Integer page;

    @Positive
    private Integer size = 10;
}
