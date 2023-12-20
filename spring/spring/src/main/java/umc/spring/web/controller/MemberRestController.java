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
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.PageDtoConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.member_service.MemberCommandService;
import umc.spring.service.member_service.MemberQueryService;
import umc.spring.validation.annotation.OngoingMission;
import umc.spring.web.dto.member.MemberResponseDTO.WrittenReviewResponseDto;
import umc.spring.web.dto.page.PageRequestDto;
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
    getWrittenReviews(@PathVariable @PositiveOrZero Long memberId,
                      @Valid PageRequestDto request) {
        PageRequest pageRequest = PageDtoConverter.toPageRequest(request);
        Page<Review> page = memberQueryService.getWrittenReviews(memberId, pageRequest);
        PageResponseDto<WrittenReviewResponseDto> response =
                MemberConverter.writtenReviewPageResponseDto(page);
        return ApiResponse.onSuccess(response);
    }
}
