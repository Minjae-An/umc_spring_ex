package umc.spring.web.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.converter.RegionConverter;
import umc.spring.domain.Store;
import umc.spring.service.region_service.RegionCommandService;
import umc.spring.web.dto.region.RegionRequestDTO;
import umc.spring.web.dto.region.RegionResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/region")
public class RegionRestController {
    private final RegionCommandService regionCommandService;

    @PostMapping("/{regionId}/store")
    public ResponseEntity<RegionResponseDTO.AddStoreToRegionResponseDTO>
        addStoreToRegion(@RequestBody @Valid RegionRequestDTO.AddStoreToRegionRequestDTO request,
                @PathVariable Long regionId){
        Store store = regionCommandService.addStoreToRegion(request, regionId);
        return ResponseEntity.ok(RegionConverter.toAddStoreToRegionResponseDTO(store));
    }
}
