DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Groups;
DROP TABLE IF EXISTS Classroom;
DROP TABLE IF EXISTS Teacher;

CREATE TABLE IF NOT EXISTS Student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    DOCUMENT_TYPE VARCHAR(255),
    DOCUMENT_NUMBER VARCHAR(255),
    name VARCHAR(255),
    guardian_id INT,
    teacher_id INT,
    group_id INT
);

CREATE TABLE IF NOT EXISTS Groups  (
    id INT AUTO_INCREMENT PRIMARY KEY,
    grade VARCHAR(255),
    classRoom INT
);

CREATE TABLE IF NOT EXISTS Classroom  (
    id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE  IF NOT EXISTS Teacher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    DOCUMENT_TYPE VARCHAR(255),
    DOCUMENT_NUMBER VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255),
    cellPhone VARCHAR(20),
    email VARCHAR(255),
    group_id INT
);


INSERT INTO Classroom (location, name) VALUES ('Sede principal', '201');
INSERT INTO Classroom (location, name) VALUES ('Sede auxiliar', '103');


INSERT INTO Groups (grade, classRoom)VALUES ('Grado 5-A', 1);
INSERT INTO Groups (grade, classRoom)VALUES ('Grado 5-B', 2);


INSERT INTO Teacher (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, address, cellPhone, email, group_id)
VALUES ('CC', '123456789', 'John Quintero', 'Pereira', '3105263236', 'john.quintero@example.com', 1);

INSERT INTO Teacher (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, address, cellPhone, email, group_id)
VALUES ('CC', '987654321', 'Adriana Perez', 'Dosquebradas', '3256252695', 'adriana.perez@example.com', 1);



INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '54354354', 'Alice Student',5421, 1, 1);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '3543356', 'Alice Student',5144, 1, 1);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '54354354', 'Alice Student',4584, 1, 1);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '5343554', 'Alice Student',9524, 1, 1);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '65454353', 'Alice Student',5469, 1, 1);


INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '354354543', 'Alice Student',8565, 2, 2);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '3543543543', 'Alice Student',4565, 2, 2);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '53543543', 'Alice Student',9863, 2, 2);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '54354354', 'Alice Student',8426, 2, 2);

INSERT INTO Student (DOCUMENT_TYPE, DOCUMENT_NUMBER, name, guardian_id, teacher_id, group_id)
VALUES ('TI', '865345654', 'Alice Student',1227, 2, 2);

