package com.gointeractive.universityconsoleapp.runner;

import com.gointeractive.universityconsoleapp.dto.DepartmentEmployeeCountDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentHeadDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentSalaryDTO;
import com.gointeractive.universityconsoleapp.dto.DepartmentStatisticsDTO;
import com.gointeractive.universityconsoleapp.dto.LectorSearchDTO;
import com.gointeractive.universityconsoleapp.service.DepartmentService;
import com.gointeractive.universityconsoleapp.service.LectorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsoleInterface implements CommandLineRunner {

    DepartmentService departmentService;
    LectorService lectorService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to University Console App!");

        while (true) {
            System.out.println("Enter command or type 'exit' to quit:");
            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Goodbye!");
                break;
            }

            handleCommand(input);
        }
    }

    private void handleCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid command.");
            return;
        }

        if (input.startsWith("Who is head of department")) {
            String departmentName = input.replace("Who is head of department", "").trim();
            if (departmentName.isEmpty()) {
                System.out.println("Please provide a department name.");
                return;
            }
            try {
                DepartmentHeadDTO head = departmentService.getDepartmentHead(departmentName);
                System.out.println("Head of " + departmentName + " department is " + head.getHeadOfDepartmentName());
            } catch (EntityNotFoundException e) {
                System.out.println("Department not found: " + departmentName);
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }

        } else if (input.startsWith("Show") && input.contains("statistics")) {
            String departmentName = input.replace("Show", "").replace("statistics", "").trim().toLowerCase();
            if (departmentName.isEmpty()) {
                System.out.println("Please provide a department name.");
                return;
            }
            try {
                DepartmentStatisticsDTO stats = departmentService.getDepartmentStatistics(departmentName);
                System.out.println("Assistants - " + stats.getAssistantsCount());
                System.out.println("Associate Professors - " + stats.getAssociateProfessorsCount());
                System.out.println("Professors - " + stats.getProfessorsCount());
            } catch (EntityNotFoundException e) {
                System.out.println("Department not found: " + departmentName);
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }

        } else if (input.startsWith("Show the average salary for the department")) {
            String departmentName = input.replace("Show the average salary for the department", "").trim();
            if (departmentName.isEmpty()) {
                System.out.println("Please provide a department name.");
                return;
            }
            try {
                DepartmentSalaryDTO salary = departmentService.getAverageSalary(departmentName);
                System.out.println("The average salary of " + departmentName + " is " + salary.getAverageSalary());
            } catch (EntityNotFoundException e) {
                System.out.println("Department not found: " + departmentName);
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }


        } else if (input.startsWith("Show count of employee for")) {
            String departmentName = input.replace("Show count of employee for", "").trim();
            if (departmentName.isEmpty()) {
                System.out.println("Please provide a department name.");
                return;
            }
            try {
                DepartmentEmployeeCountDTO count = departmentService.getEmployeeCount(departmentName);
                System.out.println("Number of employees in " + departmentName + ": " + count.getEmployeeCount());
            } catch (EntityNotFoundException e) {
                System.out.println("Department not found: " + departmentName);
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }

        } else if (input.startsWith("Global search by")) {
            String template = input.replace("Global search by", "").trim();
            if (template.isEmpty()) {
                System.out.println("Please provide a search template.");
                return;
            }
            try {
                List<LectorSearchDTO> lectors = lectorService.searchLectorsByTemplate(template);
                if (lectors.isEmpty()) {
                    System.out.println("No lectors found for the given search.");
                } else {
                    lectors.forEach(lector -> System.out.println(lector.getName()));
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }

        } else {
            System.out.println("Unknown command. Please try again.");
        }
    }
}