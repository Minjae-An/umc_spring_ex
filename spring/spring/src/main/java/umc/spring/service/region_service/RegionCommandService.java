package umc.spring.service.region_service;

import umc.spring.domain.Store;
import umc.spring.web.dto.region.RegionRequestDTO;

public interface RegionCommandService {
    Store addStoreToRegion(RegionRequestDTO.AddStoreToRegionRequestDTO request, Long regionId);
}
