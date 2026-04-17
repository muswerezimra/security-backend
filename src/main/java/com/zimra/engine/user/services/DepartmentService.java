package com.zimra.engine.user.services;

import com.zimra.engine.user.dtos.DepartmentDTO;
import com.zimra.engine.user.dtos.DepartmentDtoPost;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDtoPost departmentDtoPost);
    DepartmentDTO updateDepartment(Long id, DepartmentDtoPost departmentDtoPost);
    DepartmentDTO getDepartmentById(Long id);
    List<DepartmentDTO> getAllDepartments();
    void deleteDepartment(Long id);
}
