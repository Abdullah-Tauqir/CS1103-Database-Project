-- University Course Enrollment System Database Schema

-- Drop tables if they exist to avoid conflicts
DROP TABLE IF EXISTS prereq;
DROP TABLE IF EXISTS takes;
DROP TABLE IF EXISTS teaches;
DROP TABLE IF EXISTS sec_time_slot;
DROP TABLE IF EXISTS sec_class;
DROP TABLE IF EXISTS advisor;
DROP TABLE IF EXISTS time_slot;
DROP TABLE IF EXISTS section;
DROP TABLE IF EXISTS classroom;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS instructor;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS department;

-- Entity Tables

-- Department entity
CREATE TABLE department (
    dept_name VARCHAR(50) PRIMARY KEY,
    building VARCHAR(50),
    budget DECIMAL(12,2) CHECK (budget > 0)
);

-- Student entity
CREATE TABLE student (
    ID VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tot_cred INT DEFAULT 0,
    dept_name VARCHAR(50),
    FOREIGN KEY (dept_name) REFERENCES department(dept_name) ON DELETE SET NULL
);

-- Instructor entity
CREATE TABLE instructor (
    ID VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2) CHECK (salary > 0),
    dept_name VARCHAR(50),
    FOREIGN KEY (dept_name) REFERENCES department(dept_name) ON DELETE SET NULL
);

-- Course entity
CREATE TABLE course (
    course_id VARCHAR(10) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    credits INT CHECK (credits > 0),
    dept_name VARCHAR(50),
    FOREIGN KEY (dept_name) REFERENCES department(dept_name) ON DELETE SET NULL
);

-- Classroom entity
CREATE TABLE classroom (
    building VARCHAR(50),
    room_number VARCHAR(10),
    capacity INT CHECK (capacity > 0),
    PRIMARY KEY (building, room_number)
);

-- Section entity
CREATE TABLE section (
    sec_id VARCHAR(10),
    semester VARCHAR(10),
    year INT,
    course_id VARCHAR(10),
    PRIMARY KEY (sec_id, semester, year, course_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE
);

-- Time_Slot entity
CREATE TABLE time_slot (
    time_slot_id VARCHAR(10),
    day VARCHAR(10),
    start_time TIME,
    end_time TIME,
    PRIMARY KEY (time_slot_id, day, start_time)
);

-- Relationship Tables

-- advisor relationship (Student-Instructor)
CREATE TABLE advisor (
    student_id VARCHAR(10),
    instructor_id VARCHAR(10),
    PRIMARY KEY (student_id),
    FOREIGN KEY (student_id) REFERENCES student(ID) ON DELETE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructor(ID) ON DELETE SET NULL
);

-- teaches relationship (Instructor-Section)
CREATE TABLE teaches (
    instructor_id VARCHAR(10),
    sec_id VARCHAR(10),
    semester VARCHAR(10),
    year INT,
    course_id VARCHAR(10),
    PRIMARY KEY (instructor_id, sec_id, semester, year, course_id),
    FOREIGN KEY (instructor_id) REFERENCES instructor(ID) ON DELETE CASCADE,
    FOREIGN KEY (sec_id, semester, year, course_id) REFERENCES section(sec_id, semester, year, course_id) ON DELETE CASCADE
);

-- takes relationship (Student-Section) with grade field
CREATE TABLE takes (
    student_id VARCHAR(10),
    sec_id VARCHAR(10),
    semester VARCHAR(10),
    year INT,
    course_id VARCHAR(10),
    grade VARCHAR(2),
    PRIMARY KEY (student_id, sec_id, semester, year, course_id),
    FOREIGN KEY (student_id) REFERENCES student(ID) ON DELETE CASCADE,
    FOREIGN KEY (sec_id, semester, year, course_id) REFERENCES section(sec_id, semester, year, course_id) ON DELETE CASCADE
);

-- sec_time_slot relationship (Section-Time_Slot)
CREATE TABLE sec_time_slot (
    sec_id VARCHAR(10),
    semester VARCHAR(10),
    year INT,
    course_id VARCHAR(10),
    time_slot_id VARCHAR(10),
    day VARCHAR(10),
    start_time TIME,
    PRIMARY KEY (sec_id, semester, year, course_id, time_slot_id, day, start_time),
    FOREIGN KEY (sec_id, semester, year, course_id) REFERENCES section(sec_id, semester, year, course_id) ON DELETE CASCADE,
    FOREIGN KEY (time_slot_id, day, start_time) REFERENCES time_slot(time_slot_id, day, start_time) ON DELETE CASCADE
);

-- sec_class relationship (Section-Classroom)
CREATE TABLE sec_class (
    sec_id VARCHAR(10),
    semester VARCHAR(10),
    year INT,
    course_id VARCHAR(10),
    building VARCHAR(50),
    room_number VARCHAR(10),
    PRIMARY KEY (sec_id, semester, year, course_id),
    FOREIGN KEY (sec_id, semester, year, course_id) REFERENCES section(sec_id, semester, year, course_id) ON DELETE CASCADE,
    FOREIGN KEY (building, room_number) REFERENCES classroom(building, room_number) ON DELETE SET NULL
);

-- prereq relationship (Course-Course)
CREATE TABLE prereq (
    course_id VARCHAR(10),
    prereq_id VARCHAR(10),
    PRIMARY KEY (course_id, prereq_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE,
    FOREIGN KEY (prereq_id) REFERENCES course(course_id) ON DELETE CASCADE
);
