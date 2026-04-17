package com.zimra.engine.user.services.serviceImpl;

import com.zimra.engine.common.exceptions.NotFoundException;
import com.zimra.engine.user.dtos.DepartmentDTO;
import com.zimra.engine.user.dtos.DepartmentDtoPost;
import com.zimra.engine.user.dtos.mappers.DepartmentMapper;
import com.zimra.engine.user.models.Department;
import com.zimra.engine.user.repositories.DepartmentRepository;
import com.zimra.engine.user.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDTO createDepartment(DepartmentDtoPost departmentDtoPost) {
        if (departmentRepository.existsByCode(departmentDtoPost.getCode())) {
            throw new RuntimeException("Department with code " + departmentDtoPost.getCode() + " already exists");
        }
        Department department = departmentMapper.toEntity(departmentDtoPost);
        return departmentMapper.toDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDtoPost departmentDtoPost) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + id));

        department.setName(departmentDtoPost.getName());
        department.setDescription(departmentDtoPost.getDescription());
        department.setCode(departmentDtoPost.getCode());

        return departmentMapper.toDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + id));
        return departmentMapper.toDto(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
