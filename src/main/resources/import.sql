INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10001, '1953-09-02', 'Georgi', 'Facello', '1986-06-26');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10002, '1964-06-02', 'Bezalel', 'Simmel', '1985-11-21');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10003, '1959-12-03', 'Parto', 'Bamford', '1986-08-28');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10004, '1954-05-01', 'Chirstian', 'Koblick', '1986-12-01');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10005, '1955-01-21', 'Kyoichi', 'Maliniak', '1989-09-12');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10006, '1953-04-20', 'Anneke', 'Preusig', '1989-06-02');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10007, '1957-05-23', 'Tzvetan', 'Zielinski', '1989-02-10');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10008, '1958-02-19', 'Saniya', 'Kalloufi', '1994-09-15');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10009, '1952-04-19', 'Sumant', 'Peac', '1985-02-18');
INSERT INTO employee (emp_no, birth_date, first_name, last_name, hire_date)
VALUES (10010, '1963-06-01', 'Duangkaew', 'Piveteau', '1989-08-24');

INSERT INTO security_role (role_id, role_name)
values (1, 'LOGIN');
INSERT INTO security_group (group_id, group_name)
values (1, 'Standard User');
INSERT INTO group_role (fk_group_id, fk_role_id)
values (1, 1);
INSERT INTO security_user (user_id, first_name, last_name, email_address, user_password, fk_group_id)
VALUES (1, 'John', 'Doe', 'jdoe@example.com', MD5('password123'), 1);
