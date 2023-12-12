package umc.spring.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MemberHandler;
import umc.spring.domain.common.BaseEntity;
import umc.spring.domain.enums.Gender;
import umc.spring.domain.enums.MemberStatus;
import umc.spring.domain.enums.SocialType;
import umc.spring.domain.mapping.MemberAgree;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.mapping.MemberPrefer;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 40)
    private String address;

    @Column(nullable = false, length = 40)
    private String specAddress;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberStatus status;

    private LocalDate inactiveDate;

    /*
        week 9.
        이메일을 소셜 로그인에서 처리한 후, 나머지 정보를 기입 받는 것이 맞는 순서이나
        소셜 로그인 없이 구현 중이라 이메일을 nullable로 바꾸고 진행
     */
//    @Column(nullable = false, length = 50)
    private String email;

    //    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    @ColumnDefault("0")
    private Integer point;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberAgree> memberAgreeList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPrefer> memberPreferList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberMission> memberMissionList = new ArrayList<>();

    /*
        Law of Demeter 와 Tell, Don't Ask를 준수하기 위한
        memberPreferList 관련 메서드들
     */
    public void addMemberPrefer(MemberPrefer memberPrefer) {
        memberPreferList.add(memberPrefer);
    }

    public void removeMemberPrefer(MemberPrefer memberPrefer) {
        memberPreferList.remove(memberPrefer);
    }

    public void writeReview(Review review) {
        reviewList.add(review);
    }

    public void deleteReview(Review review) {
        reviewList.remove(review);
    }

    public void challengeMission(MemberMission memberMission) {
        memberMissionList.add(memberMission);
    }

    public void completeMission(MemberMission mission) {
        MemberMission ongoingMission = memberMissionList.stream()
                .filter(memberMission -> memberMission.equals(mission))
                .findFirst()
                .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_ONGOING_MISSION));

        ongoingMission.complete();
    }
}
