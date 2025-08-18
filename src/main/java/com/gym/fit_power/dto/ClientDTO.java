package com.gym.fit_power.dto;

import com.gym.fit_power.model.Client;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
public class ClientDTO {

    @JsonIgnore
    private Long id;
    private String cuit;
    private String assignedGym;
    private String assignedTrainer;
    private String assignedNutritionist;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String birthDate;
    private Boolean enabled;

    public ClientDTO toDto(Client entity) {
        ClientDTO dto = new ClientDTO();
        dto.setCuit(entity.getCuit());
        dto.setAssignedGym(entity.getAssignedGym() != null ? entity.getAssignedGym().getAddress() : null);
        dto.setAssignedTrainer(entity.getAssignedTrainer() != null ? entity.getAssignedTrainer().getCuit() : null);
        dto.setAssignedNutritionist(entity.getAssignedNutritionist() != null ? entity.getAssignedNutritionist().getCuit() : null);
        dto.setName(entity.getName());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setBirthDate(entity.getBirthDate() != null ? entity.getBirthDate().toString() : null);
        dto.setEnabled(entity.isEnabled());
        return dto;
    }

}
