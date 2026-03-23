package com.zimra.engine.user.dtos.mappers;

import com.zimra.engine.user.dtos.SystemDTO;
import com.zimra.engine.user.dtos.SystemDtoPost;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.models.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SystemMapper {

    public UserSystem toEntity(SystemDtoPost systemDTO) {
        UserSystem userSystem = new UserSystem();
        userSystem.setName(systemDTO.getName());
        userSystem.setDescription(systemDTO.getDescription());
        userSystem.setStatus(Status.ACTIVE);
        return userSystem;
    }

    public SystemDTO entityToDto(UserSystem userSystem) {
        SystemDTO dtoPost = new SystemDTO();
        dtoPost.setId(userSystem.getId());
        dtoPost.setName(userSystem.getName());
        dtoPost.setDescription(userSystem.getDescription());
        dtoPost.setStatus(userSystem.getStatus());
        return dtoPost;
    }
}
