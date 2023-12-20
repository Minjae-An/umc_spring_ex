package umc.spring.converter;

import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import umc.spring.web.dto.page.PageRequestDto;

public class PageDtoConverter {
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    public static PageRequest toPageRequest(PageRequestDto requestDto){
        Integer size = Optional.ofNullable(requestDto.getSize()).isEmpty()
                ? DEFAULT_PAGE_SIZE : requestDto.getSize();
        return PageRequest.of(requestDto.getPage()-1 , size);
    }
}
