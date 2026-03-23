package com.zimra.engine.user.dtos.mappers;

import com.zimra.engine.user.dtos.UserDTO;
import com.zimra.engine.user.dtos.UserDtoPost;
import com.zimra.engine.user.models.Department;
import com.zimra.engine.user.models.User;
import com.zimra.engine.user.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;


    public User dtoToEntity(UserDtoPost dtoPost) {
        Department department = departmentRepository.findById(dtoPost.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        User user = new User();
        user.setEmail(dtoPost.getEmail());
        user.setUsername(dtoPost.getUsername());
        user.setPhone(dtoPost.getPhone());
        user.setDepartment(department);
        user.setFirstName(dtoPost.getFName());
        user.setLastName(dtoPost.getLName());
        return user;
    }

    public UserDTO entityToDto(User user) {
        UserDTO dtoPost = new UserDTO();
        dtoPost.setId(user.getId());
        dtoPost.setFirstName(user.getFirstName());
        dtoPost.setLastName(user.getLastName());
        dtoPost.setEmail(user.getEmail());
        dtoPost.setUsername(user.getUsername());
        dtoPost.setPhone(user.getPhone());
        dtoPost.setActive(user.isActive());
        dtoPost.setDepartmentDto(departmentMapper.toDto(user.getDepartment()));
        return dtoPost;
    }
}
