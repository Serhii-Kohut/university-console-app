package com.gointeractive.universityconsoleapp.service.impl;

import com.gointeractive.universityconsoleapp.dto.DepartmentEmployeeCountDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentHeadDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentSalaryDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentStatisticsDTO;
import com.gointeractive.universityconsoleapp.entity.Department;
import com.gointeractive.universityconsoleapp.repository.DepartmentRepository;
import com.gointeractive.universityconsoleapp.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {

    DepartmentRepository departmentRepository;

    @Override
    public DepartmentHeadDTO getDepartmentHead(String departmentName) {
        log.info("Fetching head of department for {}", departmentName);

        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentName));

        if (department == null || department.getHeadOfDepartment() == null) {
            log.warn("Department {} not found or head is missing", departmentName);
            throw new EntityNotFoundException("Department or head not found");
        }

        return DepartmentHeadDTO.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .headOfDepartmentName(department.getHeadOfDepartment().getName())
                .build();
    }

    @Override
    public DepartmentSalaryDTO getAverageSalary(String departmentName) {
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty.");
        }

        log.info("Calculating average salary for department {}", departmentName);

        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentName));

        Double avgSalary = departmentRepository.findAverageSalaryByDepartment(departmentName);

        if (avgSalary == null) {
            avgSalary = 0.0;
        }

        return DepartmentSalaryDTO.builder()
                .departmentId(department.getId())
                .departmentName(departmentName)
                .averageSalary(avgSalary)
                .build();
    }


    @Override
    public DepartmentStatisticsDTO getDepartmentStatistics(String departmentName) {
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty.");
        }

        log.info("Fetching statistics for department {}", departmentName);

        Object[] statsResult = departmentRepository.findDepartmentStatistics(departmentName);
        log.info("Raw statistics data: {}", statsResult != null ? Arrays.deepToString(statsResult) : "NULL");

        if (statsResult == null || statsResult.length == 0) {
            throw new EntityNotFoundException("Department not found: " + departmentName);
        }

        Object[] stats = (Object[]) statsResult[0];

        return DepartmentStatisticsDTO.builder()
                .departmentId(departmentRepository.findByName(departmentName)
                        .orElseThrow(() -> new EntityNotFoundException("Department with name " + departmentName + " not found"))
                        .getId())
                .departmentName(departmentName)
                .assistantsCount(convertToLong(stats[0]))
                .associateProfessorsCount(convertToLong(stats[1]))
                .professorsCount(convertToLong(stats[2]))
                .build();
    }


    @Override
    public DepartmentEmployeeCountDTO getEmployeeCount(String departmentName) {
        log.info("Counting employees for department {}", departmentName);

        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty.");
        }

        Long count = departmentRepository.countByLectors_Departments_Name(departmentName);

        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentName));

        return DepartmentEmployeeCountDTO.builder()
                .departmentId(department.getId())
                .departmentName(departmentName)
                .employeeCount(count)
                .build();
    }


    private Long convertToLong(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        } else {
            throw new IllegalArgumentException("Unexpected type: " + value.getClass());
        }
    }

}