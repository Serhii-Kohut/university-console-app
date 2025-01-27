University Console App

A simple Spring Boot console application for university management. The system consists of departments and lectors. Each lector can work in multiple departments and have one academic degree: assistant, associate professor, or professor.

Features
The application provides the following console commands:

1.	User Input: Who is head of department {department_name}

  	Answer: Head of {department_name} department is {head_of_department_name}

3.	User Input: Show {department_name} statistics.

  	Answer: assistans - {assistams_count}. 
        associate professors - {associate_professors_count}
        professors -{professors_count}

5. User Input: Show the average salary for the department {department_name}.

   Answer: The average salary of {department_name} is {average_salary}

7. User Input: Show count of employee for {department_name}.
	
 Answer: {employee_count}

8. User Input: Global search by {template}.   
         Example: Global search by van
	
 Answer: Ivan Petrenko, Petro Ivanov
