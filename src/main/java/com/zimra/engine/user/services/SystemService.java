package com.zimra.engine.user.services;

import com.zimra.engine.user.dtos.SystemDTO;
import com.zimra.engine.user.dtos.SystemDtoPost;

import java.util.List;

public interface SystemService {

    SystemDTO createSystem(SystemDtoPost systemDTO);

    SystemDTO updateSystem(SystemDTO systemDTO);

    SystemDTO getById(Long id);

    List<SystemDTO> getAllSystems();

    List<SystemDTO> getByUserId(Long userId);

}
