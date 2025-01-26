package com.gointeractive.universityconsoleapp.repository;

import com.gointeractive.universityconsoleapp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE LOWER(TRIM(d.name)) = LOWER(:departmentName)")
    Optional<Department> findByName(String departmentName);


    Long countByLectors_Departments_Name(String departmentName);

    @Query("SELECT COALESCE(AVG(l.salary), 0.0) " +
            "FROM Lector l JOIN l.departments d WHERE LOWER(TRIM(d.name)) = LOWER(:departmentName)")
    Double findAverageSalaryByDepartment(@Param("departmentName") String departmentName);


    @Query("SELECT " +
            "SUM(CASE WHEN l.degree = 'ASSISTANT' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.degree = 'ASSOCIATE_PROFESSOR' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.degree = 'PROFESSOR' THEN 1 ELSE 0 END) " +
            "FROM Lector l JOIN l.departments d WHERE LOWER(TRIM(d.name)) = LOWER(:departmentName)")
    Object[] findDepartmentStatistics(@Param("departmentName") String departmentName);


}
