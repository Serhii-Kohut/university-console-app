CREATE TABLE lectors (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         degree VARCHAR(50) NOT NULL,
                         salary DOUBLE NOT NULL
);

CREATE TABLE departments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL UNIQUE,
                             head_id BIGINT,
                             CONSTRAINT fk_department_head FOREIGN KEY (head_id) REFERENCES lectors(id)
);

CREATE TABLE department_lectors (
                                    department_id BIGINT NOT NULL,
                                    lector_id BIGINT NOT NULL,
                                    PRIMARY KEY (department_id, lector_id),
                                    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id),
                                    CONSTRAINT fk_lector FOREIGN KEY (lector_id) REFERENCES lectors(id)
);