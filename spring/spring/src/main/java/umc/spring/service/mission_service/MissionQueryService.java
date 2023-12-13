package umc.spring.service.mission_service;

import umc.spring.domain.Mission;

public interface MissionQueryService {
    Mission findById(Long missionId);
}
