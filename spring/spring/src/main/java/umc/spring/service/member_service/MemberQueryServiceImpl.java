package umc.spring.service.member_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MemberHandler;
import umc.spring.apiPayload.exception.handler.MissionHandler;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.web.dto.MemberRequestDTO.ChallengeMissionRequestDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private boolean hasMission(Member member, Mission mission) {
        return member.getMemberMissionList().stream()
                .anyMatch(memberMission -> memberMission.getMission().equals(mission));
    }

    private Mission findMissionById(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionHandler(ErrorStatus.MISSION_NOT_FOUND));
    }

    @Override
    public boolean isOngoingMission(ChallengeMissionRequestDto request) {
        Member member = findById(request.getMemberId());
        Mission mission = findMissionById(request.getMissionId());
        return hasMission(member, mission);
    }
}
