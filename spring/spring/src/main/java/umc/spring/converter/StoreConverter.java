package umc.spring.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.region.RegionRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.ReviewPreviewDTO;

public class StoreConverter {
    /*
        예제의 MemberConverter.toMember와 다르게 일대다 연관관계에 해당하는 필드들이
        엔티티 상에서 이미 빈 리스트로 초기화되어 있으므로, 굳이 DTO를 엔티티로 매핑하는
        코드에서 해당 필드들을 재초기화해주지 않았다.
     */
    public static Store toStore(RegionRequestDTO.AddStoreToRegionRequestDTO request) {
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .build();
    }

    public static StoreResponseDTO.ReviewPreviewDTO reviewPreviewDTO(Review review) {
        return StoreResponseDTO.ReviewPreviewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getBody())
                .build();
    }

    public static StoreResponseDTO.ReviewPreviewListDTO reviewPreviewListDTO(Page<Review> reviewPage) {
        List<ReviewPreviewDTO> reviewPreviewDTOList = reviewPage.stream()
                .map(StoreConverter::reviewPreviewDTO)
                .collect(Collectors.toList());

        return StoreResponseDTO.ReviewPreviewListDTO.builder()
                .isLast(reviewPage.isLast())
                .isFirst(reviewPage.isFirst())
                .totalPage(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .listSize(reviewPage.getSize())
                .reviews(reviewPreviewDTOList)
                .build();
    }
}
