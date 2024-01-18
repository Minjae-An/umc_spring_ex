package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.PageDtoConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.service.store_service.StoreCommandService;
import umc.spring.service.store_service.StoreQueryService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.ExistStores;
import umc.spring.web.dto.page.PageResponseDto;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreRequestDTO.ReviewDTO;
import umc.spring.web.dto.store.StoreResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.AddMissionResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.CreateReviewResultDTO;
import umc.spring.web.dto.store.StoreResponseDTO.MissionPreviewDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Validated
@Slf4j
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


    @GetMapping("/{storeId}/missions")
    @Operation(
            summary = "해당 가게의 미션 목록 조회 API",
            description = "특정 가게의 미션 목록을 조회하는 API, 페이징을 포함한다."
                    + "query String으로 page 번호, 페이지 사이즈(size, 선택적 기본 10) 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4001", description = "해당 id를 가진 Store가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable"),
            @Parameter(name = "page", description = "페이지 번호, query string"),
            @Parameter(name = "size", description = "(선택적) 페이지당 컨텐츠 수, query string")
    })
    public ApiResponse<PageResponseDto<StoreResponseDTO.MissionPreviewDTO>>
    getMissions(@PathVariable @Positive Long storeId,
                @RequestParam(name = "page") @CheckPage Integer page,
                @RequestParam(name = "size", required = false) Integer size) {
        PageRequest pageRequest = PageDtoConverter.toPageRequest(page, size);
        Page<Mission> missions = storeQueryService.getMissionList(storeId, pageRequest);
        PageResponseDto<MissionPreviewDTO> response =
                StoreConverter.missionPreviewDTOPageResponseDto(missions);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping(value = "/{storeId}/review", consumes = "multipart/form-data")
    @Operation(
            summary = "하나의 리뷰 이미지를 가진 리뷰 추가 API",
            description = "특정 가게에 리뷰를 추가하는 API, 하나의 리뷰 이미지를 가지는 리뷰만 추가 가능."
                    + "query string으로 멤버의 ID(memberId)를 주세요"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4001",
                    description = "해당 id를 가진 Store가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001",
                    description = "해당 id를 가진 Member가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable")
    })
    public ApiResponse<CreateReviewResultDTO> createReview(@ModelAttribute @Valid ReviewDTO request,
                                                           @ExistStores @PathVariable(name = "storeId") Long storeId) {
        log.info("request = {}", request);
        Review review = storeCommandService.createReview(request.getMemberId(), storeId, request);
        return ApiResponse.onSuccess(StoreConverter.toCreateReviewResultDTO(review));
    }
}
