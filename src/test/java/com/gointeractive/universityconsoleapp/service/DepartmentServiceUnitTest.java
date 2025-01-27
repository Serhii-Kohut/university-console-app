package com.gointeractive.universityconsoleapp.service;

import com.gointeractive.universityconsoleapp.dto.DepartmentEmployeeCountDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentHeadDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentSalaryDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentStatisticsDTO;
import com.gointeractive.universityconsoleapp.entity.Department;
import com.gointeractive.universityconsoleapp.entity.Lector;
import com.gointeractive.universityconsoleapp.repository.DepartmentRepository;
import com.gointeractive.universityconsoleapp.service.impl.DepartmentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentServiceUnitTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Test
    public void testGetDepartmentHead_Success() {
        String departmentName = "Physics";
        Department department = new Department();
        department.setId(1L);
        department.setName(departmentName);

        Lector head = new Lector();
        head.setId(100L);
        head.setName("Olena Shevchenko");

        department.setHeadOfDepartment(head);

        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));

        DepartmentHeadDTO result = departmentService.getDepartmentHead(departmentName);

        assertNotNull(result);
        assertEquals(1L, result.getDepartmentId());
        assertEquals("Physics", result.getDepartmentName());
        assertEquals("Olena Shevchenko", result.getHeadOfDepartmentName());

        verify(departmentRepository, times(1)).findByName(departmentName);
    }

    @Test
    public void testGetDepartmentHead_DepartmentNotFound_ShouldThrowException() {
        String departmentName = "Unknown";
        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.getDepartmentHead(departmentName));

        assertEquals("Department not found: Unknown", exception.getMessage());

        verify(departmentRepository, times(1)).findByName(departmentName);
    }

    @Test
    public void testGetDepartmentHead_HeadNotFound_ShouldThrowException() {
        String departmentName = "Mathematics";
        Department department = new Department();
        department.setId(2L);
        department.setName(departmentName);
        department.setHeadOfDepartment(null);

        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.getDepartmentHead(departmentName));

        assertEquals("Department or head not found", exception.getMessage());

        verify(departmentRepository, times(1)).findByName(departmentName);
    }

    @Test
    public void testGetAverageSalary_Success() {
        String departmentName = "Physics";
        Department department = new Department();
        department.setId(1L);
        department.setName(departmentName);

        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));
        when(departmentRepository.findAverageSalaryByDepartment(departmentName)).thenReturn(20000.0);

        DepartmentSalaryDTO result = departmentService.getAverageSalary(departmentName);

        assertNotNull(result);
        assertEquals(1L, result.getDepartmentId());
        assertEquals("Physics", result.getDepartmentName());
        assertEquals(20000.0, result.getAverageSalary());

        verify(departmentRepository, times(1)).findByName(departmentName);
        verify(departmentRepository, times(1)).findAverageSalaryByDepartment(departmentName);
    }

    @Test
    public void testGetAverageSalary_DepartmentNotFound_ShouldThrowException() {
        String departmentName = "Unknown";
        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.getAverageSalary(departmentName));

        assertEquals("Department not found: Unknown", exception.getMessage());

        verify(departmentRepository, times(1)).findByName(departmentName);
        verify(departmentRepository, never()).findAverageSalaryByDepartment(anyString());
    }

    @Test
    public void testGetAverageSalary_WhenNullSalary_ShouldReturnZero() {
        String departmentName = "Mathematics";
        Department department = new Department();
        department.setId(2L);
        department.setName(departmentName);

        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));
        when(departmentRepository.findAverageSalaryByDepartment(departmentName)).thenReturn(null);

        DepartmentSalaryDTO result = departmentService.getAverageSalary(departmentName);

        assertNotNull(result);
        assertEquals(2L, result.getDepartmentId());
        assertEquals("Mathematics", result.getDepartmentName());
        assertEquals(0.0, result.getAverageSalary());

        verify(departmentRepository, times(1)).findByName(departmentName);
        verify(departmentRepository, times(1)).findAverageSalaryByDepartment(departmentName);
    }

    @Test
    public void testGetAverageSalary_NullDepartmentName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.getAverageSalary(null));

        assertEquals("Department name cannot be null or empty.", exception.getMessage());
        verifyNoInteractions(departmentRepository);
    }

    @Test
    public void testGetAverageSalary_EmptyDepartmentName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.getAverageSalary("  "));

        assertEquals("Department name cannot be null or empty.", exception.getMessage());
        verifyNoInteractions(departmentRepository);
    }

    @Test
    public void testGetDepartmentStatistics_Success() {
        String departmentName = "Physics";
        Department department = new Department();
        department.setId(1L);
        department.setName(departmentName);

        Object[] statsArray = {new Object[]{3L, 2L, 1L}};

        when(departmentRepository.findDepartmentStatistics(departmentName)).thenReturn(statsArray);
        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));

        DepartmentStatisticsDTO result = departmentService.getDepartmentStatistics(departmentName);

        assertNotNull(result);
        assertEquals(1L, result.getDepartmentId());
        assertEquals("Physics", result.getDepartmentName());
        assertEquals(3L, result.getAssistantsCount());
        assertEquals(2L, result.getAssociateProfessorsCount());
        assertEquals(1L, result.getProfessorsCount());

        verify(departmentRepository, times(1)).findDepartmentStatistics(departmentName);
        verify(departmentRepository, times(1)).findByName(departmentName);
    }

    @Test
    public void testGetDepartmentStatistics_DepartmentNotFound_ShouldThrowException() {
        String departmentName = "Unknown";
        when(departmentRepository.findDepartmentStatistics(departmentName)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.getDepartmentStatistics(departmentName));

        assertEquals("Department not found: Unknown", exception.getMessage());

        verify(departmentRepository, times(1)).findDepartmentStatistics(departmentName);
        verify(departmentRepository, never()).findByName(anyString());
    }

    @Test
    public void testGetDepartmentStatistics_WhenNullStats_ShouldThrowException() {
        String departmentName = "Mathematics";
        when(departmentRepository.findDepartmentStatistics(departmentName)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.getDepartmentStatistics(departmentName));

        assertEquals("Department not found: Mathematics", exception.getMessage());

        verify(departmentRepository, times(1)).findDepartmentStatistics(departmentName);
    }

    @Test
    public void testGetDepartmentStatistics_WhenEmptyStats_ShouldThrowException() {
        String departmentName = "Mathematics";
        when(departmentRepository.findDepartmentStatistics(departmentName)).thenReturn(new Object[]{});

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.getDepartmentStatistics(departmentName));

        assertEquals("Department not found: Mathematics", exception.getMessage());

        verify(departmentRepository, times(1)).findDepartmentStatistics(departmentName);
    }

    @Test
    public void testGetDepartmentStatistics_NullDepartmentName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.getDepartmentStatistics(null));

        assertEquals("Department name cannot be null or empty.", exception.getMessage());

        verifyNoInteractions(departmentRepository);
    }

    @Test
    public void testGetDepartmentStatistics_EmptyDepartmentName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.getDepartmentStatistics("  "));

        assertEquals("Department name cannot be null or empty.", exception.getMessage());

        verifyNoInteractions(departmentRepository);
    }

    @Test
    public void testGetEmployeeCount_Success() {
        String departmentName = "Physics";
        Department department = new Department();
        department.setId(1L);
        department.setName(departmentName);

        when(departmentRepository.countByLectors_Departments_Name(departmentName)).thenReturn(10L);
        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));

        DepartmentEmployeeCountDTO result = departmentService.getEmployeeCount(departmentName);

        assertNotNull(result);
        assertEquals(1L, result.getDepartmentId());
        assertEquals("Physics", result.getDepartmentName());
        assertEquals(10L, result.getEmployeeCount());

        verify(departmentRepository, times(1)).countByLectors_Departments_Name(departmentName);
        verify(departmentRepository, times(1)).findByName(departmentName);
    }

    @Test
    public void testGetEmployeeCount_NullDepartmentName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.getEmployeeCount(null));

        assertEquals("Department name cannot be null or empty.", exception.getMessage());

        verifyNoInteractions(departmentRepository);
    }

    @Test
    public void testGetEmployeeCount_EmptyDepartmentName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.getEmployeeCount("   "));

        assertEquals("Department name cannot be null or empty.", exception.getMessage());

        verifyNoInteractions(departmentRepository);
    }

    @Test
    public void testGetEmployeeCount_ZeroEmployees() {
        String departmentName = "Mathematics";
        Department department = new Department();
        department.setId(2L);
        department.setName(departmentName);

        when(departmentRepository.countByLectors_Departments_Name(departmentName)).thenReturn(0L);
        when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(department));

        DepartmentEmployeeCountDTO result = departmentService.getEmployeeCount(departmentName);

        assertNotNull(result);
        assertEquals(2L, result.getDepartmentId());
        assertEquals("Mathematics", result.getDepartmentName());
        assertEquals(0L, result.getEmployeeCount());

        verify(departmentRepository, times(1)).countByLectors_Departments_Name(departmentName);
        verify(departmentRepository, times(1)).findByName(departmentName);
    }


}
