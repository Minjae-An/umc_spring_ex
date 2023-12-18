package umc.spring.service.store_service;

import org.springframework.data.domain.Page;
import umc.spring.domain.Review;
import umc.spring.domain.Store;

public interface StoreQueryService {
    Store findById(Long storeId);
    boolean isStoreExist(Long storeId);
    Page<Review> getReviewList(Long storeId, Integer page);
}
