package umc.spring.converter;

import static umc.spring.web.dto.store.StoreRequestDTO.AddMissionRequestDTO;

import umc.spring.domain.Mission;
import umc.spring.web.dto.store.StoreResponseDTO.AddMissionResponseDTO;

public class MissionConverter {
    public static Mission toMission(AddMissionRequestDTO request){
        return Mission.builder()
                .reward(request.getReward())
                .deadline(request.getDeadline())
                .missionSpec(request.getMissionSpec())
                .build();
    }

    public static AddMissionResponseDTO toAddMissionResponseDTO(Mission mission){
        return AddMissionResponseDTO.builder()
                .missionId(mission.getId())
                .storeId(mission.getStore().getId())
                .createdAt(mission.getCreatedAt())
                .build();
    }
}
