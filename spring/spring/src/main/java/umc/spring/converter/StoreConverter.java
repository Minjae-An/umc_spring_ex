package umc.spring.converter;

import umc.spring.domain.Store;
import umc.spring.web.dto.region.RegionRequestDTO;

public class StoreConverter {
    /*
        예제의 MemberConverter.toMember와 다르게 일대다 연관관계에 해당하는 필드들이
        엔티티 상에서 이미 빈 리스트로 초기화되어 있으므로, 굳이 DTO를 엔티티로 매핑하는
        코드에서 해당 필드들을 재초기화해주지 않았다.
     */
    public static Store toStore(RegionRequestDTO.AddStoreToRegionRequestDTO request){
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .build();
    }
}
