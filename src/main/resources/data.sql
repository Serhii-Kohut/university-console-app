INSERT INTO departments (id, name)
VALUES (1, 'Computer Science');
INSERT INTO departments (id, name)
VALUES (2, 'Mathematics');
INSERT INTO departments (id, name)
VALUES (3, 'Physics');
INSERT INTO departments (id, name)
VALUES (4, 'Chemistry');

INSERT INTO lectors (id, name, degree, salary)
VALUES (1, 'Petro Petrenko', 'PROFESSOR', 25000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (2, 'Ivan Ivanenko', 'ASSISTANT', 15000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (3, 'Olena Shevchenko', 'ASSOCIATE_PROFESSOR', 20000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (4, 'Oksana Dobryi', 'ASSISTANT', 12000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (5, 'Mykola Hryhorenko', 'PROFESSOR', 27000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (6, 'Svitlana Kushnir', 'ASSISTANT', 14000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (7, 'Andrii Melnyk', 'ASSOCIATE_PROFESSOR', 19000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (8, 'Iryna Bondarenko', 'PROFESSOR', 30000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (9, 'Taras Kovalenko', 'ASSISTANT', 11000);
INSERT INTO lectors (id, name, degree, salary)
VALUES (10, 'Nadiya Petrova', 'ASSOCIATE_PROFESSOR', 18000);

UPDATE departments
SET head_id = 1
WHERE id = 1; -- Computer Science -> Petro Petrenko
UPDATE departments
SET head_id = 5
WHERE id = 2; -- Mathematics -> Mykola Hryhorenko
UPDATE departments
SET head_id = 8
WHERE id = 3; -- Physics -> Iryna Bondarenko
UPDATE departments
SET head_id = 3
WHERE id = 4; -- Chemistry -> Olena Shevchenko

INSERT INTO department_lectors (department_id, lector_id)
VALUES (1, 1);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (1, 2);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (1, 3);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (2, 4);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (2, 5);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (3, 6);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (3, 7);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (4, 8);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (4, 9);
INSERT INTO department_lectors (department_id, lector_id)
VALUES (4, 10);