package umc.spring.service.member_service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MemberHandler;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MemberPreferConverter;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.mapping.MemberPrefer;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.service.food_category_service.FoodCategoryQueryService;
import umc.spring.service.mission_service.MissionQueryService;
import umc.spring.web.dto.member.MemberRequestDTO.ChallengeMissionRequestDto;
import umc.spring.web.dto.member.MemberRequestDTO.JoinDto;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final FoodCategoryQueryService foodCategoryQueryService;
    private final MissionQueryService missionQueryService;

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public Member joinMember(JoinDto request) {
        Member member = MemberConverter.toMember(request);
        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(foodCategoryQueryService::findById)
                .collect(Collectors.toList());

        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);

        memberPreferList.forEach(memberPrefer -> memberPrefer.setMember(member));
        return memberRepository.save(member);
    }

    @Override
    public MemberMission challengeMission(ChallengeMissionRequestDto request) {
        Member member = findById(request.getMemberId());
        Mission mission = missionQueryService.findById(request.getMissionId());
        MemberMission memberMission = MemberConverter.toMemberMission(member, mission);

        member.challengeMission(memberMission);
        mission.challenge(memberMission);
        return memberMission;
    }

    @Override
    public void completeMission(Long memberId, Long memberMissionId) {
        Member member = findById(memberId);
        MemberMission memberMission = findMemberMissionById(memberMissionId);
        member.completeMission(memberMission);
    }

    private MemberMission findMemberMissionById(Long memberMissionId){
        return memberMissionRepository.findById(memberMissionId)
                .orElseThrow(()->new MemberHandler(ErrorStatus.MEMBER_MISSION_NOT_FOUND));
    }
}
