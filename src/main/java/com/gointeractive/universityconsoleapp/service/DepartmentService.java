package com.gointeractive.universityconsoleapp.service;

import com.gointeractive.universityconsoleapp.dto.DepartmentEmployeeCountDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentHeadDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentSalaryDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentStatisticsDTO;

public interface DepartmentService {
    DepartmentHeadDTO getDepartmentHead(String departmentName);

    DepartmentSalaryDTO getAverageSalary(String departmentName);

    DepartmentStatisticsDTO getDepartmentStatistics(String departmentName);

    DepartmentEmployeeCountDTO getEmployeeCount(String departmentName);
}

