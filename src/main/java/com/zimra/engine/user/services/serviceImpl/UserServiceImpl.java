package com.zimra.engine.user.services.serviceImpl;

import com.zimra.engine.common.exceptions.NotFoundException;
import com.zimra.engine.user.dtos.UserDTO;
import com.zimra.engine.user.dtos.UserDtoPost;
import com.zimra.engine.user.dtos.mappers.UserMapper;
import com.zimra.engine.user.models.Role;
import com.zimra.engine.user.models.User;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.repositories.RoleRepository;
import com.zimra.engine.user.repositories.SystemRepository;
import com.zimra.engine.user.repositories.UserRepository;
import com.zimra.engine.user.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final SystemRepository systemRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO create(UserDtoPost userDtoPost) {
        User user = userMapper.dtoToEntity(userDtoPost);
        user.setPassword(passwordEncoder.encode("Password123"));
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long id, UserDtoPost userDtoPost) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (userDtoPost.getFName() != null) {
            user.setFirstName(userDtoPost.getFName());
        }
        if (userDtoPost.getLName() != null) {
            user.setLastName(userDtoPost.getLName());
        }
        if (userDtoPost.getPhone() != null) {
            user.setPhone(userDtoPost.getPhone());
        }
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("USER") 
                .build();
    }

    @Override
    public List<UserDTO> findAllBySystemId(Long systemId) {
        // This would need to be re-implemented to query users based on roles.
        // For now, returning an empty list.
        return List.of();
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.parallelStream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO assignRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role with id " + roleId + " not found"));
        Set<Role> roleSet = user.getRoles();
        roleSet.add(role);
        user.setRoles(roleSet);
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDTO assignManyRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        Set<Role> roleSet = user.getRoles();
        for (Long roleId : roleIds) {
            roleSet.add(roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role with id " + roleId + " not found")));
        }
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDTO unassignRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role with id " + roleId + " not found"));
        Set<Role> roleSet = user.getRoles();
        roleSet.remove(role);
        user.setRoles(roleSet);
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDTO changeStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        user.setActive(!user.isActive());
        return userMapper.entityToDto(userRepository.save(user));
    }

}
