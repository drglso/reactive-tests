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