package umc.spring.web.controller;

import static umc.spring.web.dto.MemberRequestDTO.ChallengeMissionRequestDto;
import static umc.spring.web.dto.MemberRequestDTO.JoinDto;
import static umc.spring.web.dto.MemberResponseDTO.ChallengeMissionResponseDto;
import static umc.spring.web.dto.MemberResponseDTO.JoinResultDTO;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.member_service.MemberCommandService;
import umc.spring.validation.annotation.OngoingMission;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/member")
public class MemberRestController {
    private final MemberCommandService memberCommandService;

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
}
