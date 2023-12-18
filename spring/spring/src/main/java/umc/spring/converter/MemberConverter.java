package umc.spring.converter;

import static umc.spring.web.dto.member.MemberResponseDTO.ChallengeMissionResponseDto;
import static umc.spring.web.dto.member.MemberResponseDTO.JoinResultDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.enums.Gender;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.member.MemberRequestDTO;
import umc.spring.web.dto.member.MemberResponseDTO.WrittenReviewResponseDto;
import umc.spring.web.dto.page.PageResponseDto;

public class MemberConverter {
    public static JoinResultDTO toJoinResultDTO(Member member) {
        return JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request) {
        Gender gender = null;

        switch (request.getGender()) {
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            case 3:
                gender = Gender.NONE;
                break;
        }

        return Member.builder()
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .gender(gender)
                .name(request.getName())
                .memberPreferList(new ArrayList<>())
                .build();
    }

    public static ChallengeMissionResponseDto
    toChallengeMissionResponseDto(MemberMission memberMission) {
        return ChallengeMissionResponseDto.builder()
                .memberId(memberMission.getMember().getId())
                .memberMissionId(memberMission.getId())
                .build();
    }

    public static MemberMission toMemberMission(Member member, Mission mission) {
        return MemberMission.builder()
                .status(MissionStatus.CHALLENGING)
                .member(member)
                .mission(mission)
                .build();
    }

    public static WrittenReviewResponseDto writtenReviewResponseDto(Review review) {
        return WrittenReviewResponseDto.builder()
                .storeName(review.getStore().getName())
                .memberName(review.getMember().getName())
                .score(review.getScore())
                .body(review.getBody())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }

    public static PageResponseDto<WrittenReviewResponseDto> writtenReviewPageResponseDto(Page<Review> page) {
        List<WrittenReviewResponseDto> contents = page.getContent().stream()
                .map(MemberConverter::writtenReviewResponseDto)
                .collect(Collectors.toList());

        return PageResponseDto.<WrittenReviewResponseDto>builder()
                .contents(contents)
                .pageSize(page.getSize())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
