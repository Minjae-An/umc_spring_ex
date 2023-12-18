package umc.spring.web.dto.page;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PageResponseDto<T> {
    private List<T> contents;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalElements;
    private Boolean isFirst;
    private Boolean isLast;
}
