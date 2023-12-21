package umc.spring.service.member_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;

public interface MemberQueryService {
    Member findById(Long memberId);

    boolean isOngoingMission(ChallengeMissionRequestDto request);

    Page<Review> getWrittenReviews(Long memberId, PageRequest request);

    Page<MemberMission> getOngoingMissions(Long memberId, PageRequest request);
}
