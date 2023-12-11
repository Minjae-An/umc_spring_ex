package umc.spring.service.region_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.RegionHandler;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.repository.RegionRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.region.RegionRequestDTO.AddStoreToRegionRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionCommandServiceImpl implements RegionCommandService {
    private final RegionRepository regionRepository;
    private final StoreRepository storeRepository;

    @Override
    public Store addStoreToRegion(AddStoreToRegionRequestDTO request, Long regionId) {
        Region region = findById(regionId);
        Store store = StoreConverter.toStore(request);
        store.region(region);

        return storeRepository.save(store);
    }

    private Region findById(Long regionId) {
        return regionRepository.findById(regionId)
                .orElseThrow(() -> new RegionHandler(ErrorStatus.REGION_NOT_FOUND));
    }
}
