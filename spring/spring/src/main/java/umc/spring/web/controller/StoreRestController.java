package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.service.store_service.StoreCommandService;
import umc.spring.service.store_service.StoreQueryService;
import umc.spring.validation.annotation.ExistStores;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.AddMissionResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Validated
public class StoreRestController {
    private final StoreQueryService storeQueryService;
    private final StoreCommandService storeCommandService;

    @PostMapping("/{storeId}/mission")
    public ApiResponse<AddMissionResponseDTO>
    addMission(@RequestBody @Valid StoreRequestDTO.AddMissionRequestDTO request,
               @PathVariable Long storeId) {
        Mission mission = storeCommandService.addMission(request, storeId);
        return ApiResponse.onSuccess(MissionConverter.toAddMissionResponseDTO(mission));
    }

    @PostMapping("/{storeId}/review")
    public ApiResponse<StoreResponseDTO.AddReviewResponseDTO>
    addReview(@RequestBody @Valid StoreRequestDTO.AddReviewRequestDTO request,
              @PathVariable @ExistStores Long storeId) {
        Review review = storeCommandService.addReview(request, storeId);
        return ApiResponse.onSuccess(ReviewConverter.toAddReviewResponseDTO(review));
    }

    @GetMapping("/{storeId}/reviews")
    @Operation(
            summary = "해당 가게의 리뷰 목록 조회 API",
            description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다."
                    + "query String으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreviewListDTO>
    getReviewList(@ExistStores @PathVariable(name = "storeId") Long storeId,
                  @RequestParam(name = "page") Integer page) {
        Page<Review> reviewPage = storeQueryService.getReviewList(storeId, page);
        return ApiResponse.onSuccess(StoreConverter.reviewPreviewListDTO(reviewPage));
    }
}
