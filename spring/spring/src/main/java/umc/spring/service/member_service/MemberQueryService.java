package umc.spring.service.member_service;

import umc.spring.domain.Member;
import umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;

public interface MemberQueryService {
    Member findById(Long memberId);

    boolean isOngoingMission(ChallengeMissionRequestDto request);
}
