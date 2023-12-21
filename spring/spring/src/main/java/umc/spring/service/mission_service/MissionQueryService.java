package umc.spring.service.mission_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;

public interface MissionQueryService {
    Mission findById(Long missionId);

    Page<Mission> findAllByStore(Store store, PageRequest pageRequest);
}
