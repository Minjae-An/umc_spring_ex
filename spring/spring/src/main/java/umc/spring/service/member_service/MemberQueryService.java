package umc.spring.service.member_service;

import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;
import umc.spring.web.dto.page.PageRequestDto;

public interface MemberQueryService {
    Member findById(Long memberId);

    boolean isOngoingMission(ChallengeMissionRequestDto request);
    Page<Review> getWrittenReviews(Long memberId, PageRequestDto request);
}
