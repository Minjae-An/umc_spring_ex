package umc.spring.service.store_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.store.StoreResponseDTO.ReviewDetailDTO;

public interface StoreQueryService {
    Store findById(Long storeId);

    boolean isStoreExist(Long storeId);

    Page<Review> getReviewList(Long storeId, Integer page);

    Page<Mission> getMissionList(Long storeId, PageRequest pageRequest);

    ReviewDetailDTO getReviewDetail(Long reviewId);
}
