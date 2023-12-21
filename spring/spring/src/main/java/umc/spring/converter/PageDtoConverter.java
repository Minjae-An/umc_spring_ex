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

    public static PageRequest toPageRequest(Integer page, Integer size){
        size = Optional.ofNullable(size).isPresent() ? size : DEFAULT_PAGE_SIZE;
        return PageRequest.of(page, size);
    }
}
