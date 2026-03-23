package com.zimra.engine.user.services;

import com.zimra.engine.user.dtos.UserDTO;
import com.zimra.engine.user.dtos.UserDtoPost;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

    UserDTO create(UserDtoPost userDtoPost);

    UserDTO update(Long id, UserDtoPost userDtoPost);

    UserDTO getById(Long id);

    @Override
    UserDetails loadUserByUsername(String username);

    List<UserDTO> findAllBySystemId(Long systemId);

    List<UserDTO> getAll();

    UserDTO assignRole(Long userId, Long roleId);

    UserDTO assignManyRoles(Long userId, List<Long> roleIds);



    UserDTO unassignRole(Long userId, Long roleId);

    UserDTO changeStatus(Long id);


}
