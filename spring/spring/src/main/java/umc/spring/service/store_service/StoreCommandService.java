package umc.spring.service.store_service;

import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.store.StoreRequestDTO;

public interface StoreCommandService {
    Store save(Store store);
    Mission addMission(StoreRequestDTO.AddMissionRequestDTO request, Long storeId);
    Review addReview(StoreRequestDTO.AddReviewRequestDTO requestDTO, Long storeId);
}
