package com.zimra.engine.user.dtos.mappers;

import com.zimra.engine.user.dtos.DepartmentDTO;
import com.zimra.engine.user.dtos.DepartmentDtoPost;
import com.zimra.engine.user.models.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {


    public DepartmentDTO toDto(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setCreatedDate(department.getCreatedDate());
        dto.setCode(department.getCode());
        return dto;
    }

    public Department toEntity(DepartmentDtoPost dtoPost) {
        Department department = new Department();
        department.setCode(dtoPost.getCode());
        department.setName(dtoPost.getName());
        department.setDescription(dtoPost.getDescription());
        return department;
    }

}
