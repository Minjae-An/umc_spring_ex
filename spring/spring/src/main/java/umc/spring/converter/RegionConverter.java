package umc.spring.converter;

import umc.spring.domain.Store;
import umc.spring.web.dto.region.RegionResponseDTO;

public class RegionConverter {
    public static RegionResponseDTO.AddStoreToRegionResponseDTO
    toAddStoreToRegionResponseDTO(Store store) {
        return RegionResponseDTO.AddStoreToRegionResponseDTO.builder()
                .regionId(store.getRegion().getId())
                .storeId(store.getId())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
