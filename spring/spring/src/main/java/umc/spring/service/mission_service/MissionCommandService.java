package umc.spring.service.mission_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Mission;
import umc.spring.repository.MissionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandService {
    private final MissionRepository missionRepository;

    public Mission save(Mission mission){
        return missionRepository.save(mission);
    }
}
