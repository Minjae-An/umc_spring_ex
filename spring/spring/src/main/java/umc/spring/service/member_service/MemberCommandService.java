package umc.spring.service.member_service;

import static umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;
import static umc.spring.web.dto.member.MemberRequestDTO.JoinDto;

import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberMission;

public interface MemberCommandService {
    Member findById(Long memberId);

    Member joinMember(JoinDto request);

    MemberMission challengeMission(ChallengeMissionRequestDto request);

    void completeMission(Long memberId, Long memberMissionId);
}
