package umc.spring.domain.mapping;

import java.util.Optional;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Member;
import umc.spring.domain.common.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberPrefer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private FoodCategory foodCategory;

    public void setMember(Member member) {
        Optional.ofNullable(this.member)
                .ifPresent(oldMember ->
                        oldMember.removeMemberPrefer(this));

//        if(this.member != null){
//            member.getMemberPreferList().remove(this);
//        }
        this.member = member;
        member.addMemberPrefer(this);
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }
}
