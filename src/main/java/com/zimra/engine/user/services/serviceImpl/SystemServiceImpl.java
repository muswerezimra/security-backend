package com.zimra.engine.user.services.serviceImpl;

import com.zimra.engine.user.dtos.SystemDTO;
import com.zimra.engine.user.dtos.SystemDtoPost;
import com.zimra.engine.user.dtos.mappers.SystemMapper;
import com.zimra.engine.user.models.User;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.repositories.SystemRepository;
import com.zimra.engine.user.repositories.UserRepository;
import com.zimra.engine.user.services.SystemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SystemServiceImpl implements SystemService {

    private final SystemRepository systemRepository;
    private final SystemMapper systemMapper;
    private final UserRepository userRepository;

    @Override
    public SystemDTO createSystem(SystemDtoPost systemDTO) {
        // Check if system with the same name exists
        if (systemRepository.existsByName(systemDTO.getName())) {
            throw new RuntimeException("System with this name already exists.");
        }

        UserSystem userSystem = systemMapper.toEntity(systemDTO);
        return systemMapper.entityToDto(systemRepository.save(userSystem));
    }

    @Override
    public SystemDTO updateSystem(SystemDTO systemDTO) {
        return null;
    }

    @Override
    public SystemDTO getById(Long id) {
        return null;
    }

    @Override
    public List<SystemDTO> getAllSystems() {
        List<UserSystem> systemList = systemRepository.findAll();
        return systemList.stream().map(systemMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<SystemDTO> getByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<UserSystem> userSystemList = systemRepository.findAllByUserId(userId);
        return userSystemList.stream().map(systemMapper::entityToDto).collect(Collectors.toList());
    }
}
