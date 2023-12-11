package umc.spring.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import umc.spring.domain.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String address;

    @ColumnDefault("0.0")
    private Float score;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Mission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    // 연관 관계 편의 메서드
    public void region(Region region) {
        Optional.ofNullable(this.region)
                .ifPresent(oldRegion -> oldRegion.removeStore(this));

        this.region = region;
        region.addStore(this);
    }

    public void addMission(Mission mission) {
        missions.add(mission);
    }

    public void removeMission(Mission mission) {
        missions.remove(mission);
    }
}
