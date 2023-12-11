package umc.spring.web.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.service.store_service.StoreCommandService;
import umc.spring.validation.annotation.ExistStores;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Validated
public class StoreRestController {
    private final StoreCommandService storeCommandService;

    @PostMapping("/{storeId}/mission")
    public ResponseEntity<StoreResponseDTO.AddMissionResponseDTO>
    addMission(@RequestBody @Valid StoreRequestDTO.AddMissionRequestDTO request,
               @PathVariable Long storeId) {
        Mission mission = storeCommandService.addMission(request, storeId);
        return ResponseEntity.ok(MissionConverter.toAddMissionResponseDTO(mission));
    }

    @PostMapping("/{storeId}/review")
    public ResponseEntity<StoreResponseDTO.AddReviewResponseDTO>
    addReview(@RequestBody @Valid StoreRequestDTO.AddReviewRequestDTO request,
              @PathVariable @ExistStores Long storeId) {
        Review review = storeCommandService.addReview(request, storeId);
        return ResponseEntity.ok(ReviewConverter.toAddReviewResponseDTO(review));
    }
}
