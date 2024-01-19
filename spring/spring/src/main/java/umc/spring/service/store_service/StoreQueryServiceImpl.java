package umc.spring.service.store_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ReviewHandler;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.ReviewRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.service.mission_service.MissionQueryService;
import umc.spring.web.dto.store.StoreResponseDTO.ReviewDetailDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService {
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final MissionQueryService missionQueryService;

    @Override
    public Store findById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));
    }

    @Override
    public boolean isStoreExist(Long storeId) {
        return storeRepository.findById(storeId)
                .isPresent();
    }

    @Override
    public Page<Review> getReviewList(Long storeId, Integer page) {
        Store store = findById(storeId);
        return reviewRepository.findAllByStore(store, PageRequest.of(page, 10));
    }

    @Override
    public Page<Mission> getMissionList(Long storeId, PageRequest pageRequest) {
        Store store = findById(storeId);
        return missionQueryService.findAllByStore(store, pageRequest);
    }

    @Override
    public ReviewDetailDTO getReviewDetail(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewHandler(ErrorStatus.REVIEW_NOT_FOUND));
        return ReviewConverter.toReviewDetailDTO(review);
    }
}
