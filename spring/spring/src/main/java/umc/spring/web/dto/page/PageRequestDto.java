package umc.spring.web.dto.page;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import umc.spring.validation.annotation.CheckPage;

@Getter
@AllArgsConstructor
public class PageRequestDto {
    @NotNull
    @CheckPage
    private Integer page;

    private Integer size;
}
