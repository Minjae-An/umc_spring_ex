package umc.spring.service.member_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MemberHandler;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.repository.MemberRepository;
import umc.spring.service.mission_service.MissionQueryService;
import umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;
    private final MissionQueryService missionQueryService;

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private boolean hasMission(Member member, Mission mission) {
        return member.getMemberMissionList().stream()
                .anyMatch(memberMission -> memberMission.getMission().equals(mission));
    }

    @Override
    public boolean isOngoingMission(ChallengeMissionRequestDto request) {
        Member member = findById(request.getMemberId());
        Mission mission = missionQueryService.findById(request.getMissionId());
        return hasMission(member, mission);
    }
}
