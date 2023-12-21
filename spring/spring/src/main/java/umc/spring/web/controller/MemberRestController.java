package umc.spring.web.controller;

import static umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;
import static umc.spring.web.dto.member.MemberRequestDTO.JoinDto;
import static umc.spring.web.dto.member.MemberResponseDTO.ChallengeMissionResponseDto;
import static umc.spring.web.dto.member.MemberResponseDTO.JoinResultDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.apiPayload.code.status.SuccessStatus;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.PageDtoConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.member_service.MemberCommandService;
import umc.spring.service.member_service.MemberQueryService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.OngoingMission;
import umc.spring.web.dto.member.MemberResponseDTO.OngoingMissionResponseDto;
import umc.spring.web.dto.member.MemberResponseDTO.WrittenReviewResponseDto;
import umc.spring.web.dto.page.PageResponseDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/member")
public class MemberRestController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;


    @PostMapping("/")
    public ApiResponse<JoinResultDTO> join(@RequestBody @Valid JoinDto request) {
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/mission")
    public ApiResponse<ChallengeMissionResponseDto>
    challengeMission(@RequestBody @Valid @OngoingMission ChallengeMissionRequestDto request) {
        MemberMission memberMission = memberCommandService.challengeMission(request);
        return ApiResponse.onSuccess(MemberConverter.toChallengeMissionResponseDto(memberMission));
    }

    // TODO : 쿼리스트링을 요청 DTO의 필드로 매핑하고, 필드에 @Constraint 활용 커스텀 어노테이션 사용시 발생하는 빈 예외 응답 상황 해결
    @GetMapping("/{memberId}/written-reviews")
    @Operation(
            summary = "해당 멤버가 작성한 리뷰 목록 조회 API",
            description = "특정 멤버가 작성한 리뷰 목록을 조회하는 API이며, 페이징을 포함합니다."
                    + "query string으로 page 번호를 주세요(size는 선택, 기본 10)")
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
            @Parameter(name = "memberId", description = "멤버의 아이디, path variable"),
            @Parameter(name = "page", description = "페이지 번호, query string"),
            @Parameter(name = "size", description = "(선택적) 페이지당 컨텐츠 수, query string")
    })
    public ApiResponse<PageResponseDto<WrittenReviewResponseDto>>
    getWrittenReviews(@PathVariable @Positive Long memberId,
                      @RequestParam(name = "page") @Positive Integer page,
                      @RequestParam(name = "size", required = false) Integer size) {
        PageRequest pageRequest = PageDtoConverter.toPageRequest(page, size);
        Page<Review> writtenReviews = memberQueryService.getWrittenReviews(memberId, pageRequest);
        PageResponseDto<WrittenReviewResponseDto> response =
                MemberConverter.writtenReviewPageResponseDto(writtenReviews);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/{memberId}/ongoing-mission")
    @Operation(
            summary = "해당 멤버가 진행 중인 미션 목록 조회 API",
            description = "특정 멤버가 진행 중인 미션 목록을 조회하는 API이며, 페이징을 포함합니다."
                    + "query string으로 page 번호를 주세요(size는 선택, 기본 10)"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "해당 멤버가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4001", description = "유효하지 않은 페이지 번호(1 미만)",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable"),
            @Parameter(name = "page", description = "페이지 번호, 1이상이어야 함, query string"),
            @Parameter(name = "size", description = "(선택적) 페이지당 컨텐츠 개수, 기본 10, query string")
    })
    public ApiResponse<PageResponseDto<OngoingMissionResponseDto>>
    getOngoingMissions(@PathVariable @Positive Long memberId,
                       @RequestParam(name = "page") @CheckPage Integer page,
                       @RequestParam(name = "size", required = false) Integer size) {
        PageRequest pageRequest = PageDtoConverter.toPageRequest(page, size);
        Page<MemberMission> ongoingMissions = memberQueryService.getOngoingMissions(memberId, pageRequest);
        PageResponseDto<OngoingMissionResponseDto> response =
                MemberConverter.ongoingMissionPageResponseDto(ongoingMissions);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{memberId}/ongoing-mission/{memberMissionId}")
    @Operation(
            summary = "멤버의 진행 중인 미션을 완료 상태로 변경하는 API",
            description = "특정 멤버가 진행 중인 미션 목록을 조회하는 API, 요청/응답 모두 바디에 데이터를 담지 않는다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON204", description = "별도의 응답 데이터 존재 x, 정상 처리",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "해당 멤버가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "해당 멤버가 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER_MISSION4003", description = "해당 진행 중 미션이 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))

    })
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable"),
            @Parameter(name = "memberMissionId", description = "멤버의 진행 중 미션 ID, path variable")
    })
    public ApiResponse<Object> completeMission(@PathVariable @Positive Long memberId,
                                               @PathVariable @Positive Long memberMissionId) {
        memberCommandService.completeMission(memberId, memberMissionId);
        return ApiResponse.of(SuccessStatus._ACCEPTED, Optional.empty());
    }
}
