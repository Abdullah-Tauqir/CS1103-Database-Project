-- Sample data for University Course Enrollment System

-- Department data
INSERT INTO department VALUES ('CS', 'Taylor', 950000.00);
INSERT INTO department VALUES ('BIO', 'Watson', 875000.00);
INSERT INTO department VALUES ('MATH', 'Newton', 780000.00);
INSERT INTO department VALUES ('PHYS', 'Einstein', 810000.00);
INSERT INTO department VALUES ('HIST', 'Sullivan', 680000.00);
INSERT INTO department VALUES ('CHEM', 'Mendeleev', 790000.00);
INSERT INTO department VALUES ('ENG', 'Hawking', 720000.00);
INSERT INTO department VALUES ('ECON', 'Smith', 850000.00);
INSERT INTO department VALUES ('PSYCH', 'Freud', 695000.00);
INSERT INTO department VALUES ('ART', 'Picasso', 550000.00);
INSERT INTO department VALUES ('MUS', 'Mozart', 510000.00);
INSERT INTO department VALUES ('PHIL', 'Aristotle', 480000.00);

-- Student data
INSERT INTO student VALUES ('S1001', 'John Smith', 90, 'CS');
INSERT INTO student VALUES ('S1002', 'Maria Rodriguez', 85, 'CS');
INSERT INTO student VALUES ('S1003', 'James Wilson', 102, 'MATH');
INSERT INTO student VALUES ('S1004', 'Linda Chen', 78, 'BIO');
INSERT INTO student VALUES ('S1005', 'Robert Johnson', 120, 'CS');
INSERT INTO student VALUES ('S1006', 'Susan Miller', 65, 'PHYS');
INSERT INTO student VALUES ('S1007', 'David Brown', 46, 'HIST');
INSERT INTO student VALUES ('S1008', 'Lisa Wang', 92, 'MATH');
INSERT INTO student VALUES ('S1009', 'Omar Hassan', 88, 'CS');
INSERT INTO student VALUES ('S1010', 'Emily Davis', 112, 'BIO');
INSERT INTO student VALUES ('S1011', 'Michael Patel', 76, 'CHEM');
INSERT INTO student VALUES ('S1012', 'Sophia Kim', 94, 'ENG');
INSERT INTO student VALUES ('S1013', 'Daniel Martinez', 88, 'ECON');
INSERT INTO student VALUES ('S1014', 'Emma Thompson', 103, 'PSYCH');
INSERT INTO student VALUES ('S1015', 'Jamal Wilson', 67, 'ART');
INSERT INTO student VALUES ('S1016', 'Olivia Garcia', 114, 'MUS');
INSERT INTO student VALUES ('S1017', 'Alex Nguyen', 83, 'PHIL');
INSERT INTO student VALUES ('S1018', 'Isabella Johnson', 92, 'CS');
INSERT INTO student VALUES ('S1019', 'William Chen', 79, 'MATH');
INSERT INTO student VALUES ('S1020', 'Ava Robinson', 105, 'BIO');
INSERT INTO student VALUES ('S1021', 'Ethan Williams', 88, 'PHYS');
INSERT INTO student VALUES ('S1022', 'Mia Jackson', 76, 'HIST');
INSERT INTO student VALUES ('S1023', 'Noah Davis', 94, 'CHEM');
INSERT INTO student VALUES ('S1024', 'Charlotte Lewis', 108, 'ENG');
INSERT INTO student VALUES ('S1025', 'Benjamin White', 67, 'ECON');

-- Instructor data
INSERT INTO instructor VALUES ('I101', 'Dr. Jane Doe', 85000.00, 'CS');
INSERT INTO instructor VALUES ('I102', 'Prof. Richard Lee', 92000.00, 'MATH');
INSERT INTO instructor VALUES ('I103', 'Dr. Sarah Connor', 79000.00, 'BIO');
INSERT INTO instructor VALUES ('I104', 'Prof. Alan Turing', 95000.00, 'CS');
INSERT INTO instructor VALUES ('I105', 'Dr. Marie Curie', 88000.00, 'PHYS');
INSERT INTO instructor VALUES ('I106', 'Prof. Howard Zinn', 76000.00, 'HIST');
INSERT INTO instructor VALUES ('I107', 'Dr. Rosalind Franklin', 84000.00, 'CHEM');
INSERT INTO instructor VALUES ('I108', 'Prof. Noam Chomsky', 91000.00, 'ENG');
INSERT INTO instructor VALUES ('I109', 'Dr. Paul Krugman', 89500.00, 'ECON');
INSERT INTO instructor VALUES ('I110', 'Prof. Steven Pinker', 87000.00, 'PSYCH');
INSERT INTO instructor VALUES ('I111', 'Dr. Frida Kahlo', 69000.00, 'ART');
INSERT INTO instructor VALUES ('I112', 'Prof. Leonard Bernstein', 72000.00, 'MUS');
INSERT INTO instructor VALUES ('I113', 'Dr. Martha Nussbaum', 78000.00, 'PHIL');
INSERT INTO instructor VALUES ('I114', 'Prof. Grace Hopper', 93000.00, 'CS');
INSERT INTO instructor VALUES ('I115', 'Dr. Stephen Hawking', 97000.00, 'PHYS');
INSERT INTO instructor VALUES ('I116', 'Prof. Rachel Carson', 81000.00, 'BIO');
INSERT INTO instructor VALUES ('I117', 'Dr. Srinivasa Ramanujan', 90000.00, 'MATH');

-- Course data
INSERT INTO course VALUES ('CS101', 'Introduction to Programming', 3, 'CS');
INSERT INTO course VALUES ('CS201', 'Data Structures', 4, 'CS');
INSERT INTO course VALUES ('CS301', 'Database Systems', 4, 'CS');
INSERT INTO course VALUES ('CS401', 'Artificial Intelligence', 4, 'CS');
INSERT INTO course VALUES ('CS501', 'Computer Networks', 3, 'CS');
INSERT INTO course VALUES ('CS601', 'Software Engineering', 4, 'CS');

INSERT INTO course VALUES ('MATH101', 'Calculus I', 4, 'MATH');
INSERT INTO course VALUES ('MATH201', 'Linear Algebra', 3, 'MATH');
INSERT INTO course VALUES ('MATH301', 'Discrete Mathematics', 3, 'MATH');
INSERT INTO course VALUES ('MATH401', 'Differential Equations', 4, 'MATH');
INSERT INTO course VALUES ('MATH501', 'Number Theory', 3, 'MATH');

INSERT INTO course VALUES ('BIO101', 'General Biology', 4, 'BIO');
INSERT INTO course VALUES ('BIO201', 'Molecular Biology', 4, 'BIO');
INSERT INTO course VALUES ('BIO301', 'Genetics', 3, 'BIO');
INSERT INTO course VALUES ('BIO401', 'Ecology', 4, 'BIO');

INSERT INTO course VALUES ('PHYS101', 'Physics Mechanics', 4, 'PHYS');
INSERT INTO course VALUES ('PHYS201', 'Electricity & Magnetism', 4, 'PHYS');
INSERT INTO course VALUES ('PHYS301', 'Quantum Physics', 4, 'PHYS');

INSERT INTO course VALUES ('HIST101', 'World History', 3, 'HIST');
INSERT INTO course VALUES ('HIST201', 'American History', 3, 'HIST');
INSERT INTO course VALUES ('HIST301', 'European History', 3, 'HIST');

INSERT INTO course VALUES ('CHEM101', 'General Chemistry', 4, 'CHEM');
INSERT INTO course VALUES ('CHEM201', 'Organic Chemistry', 4, 'CHEM');

INSERT INTO course VALUES ('ENG101', 'English Composition', 3, 'ENG');
INSERT INTO course VALUES ('ENG201', 'World Literature', 3, 'ENG');

INSERT INTO course VALUES ('ECON101', 'Microeconomics', 3, 'ECON');
INSERT INTO course VALUES ('ECON201', 'Macroeconomics', 3, 'ECON');

INSERT INTO course VALUES ('PSYCH101', 'Introduction to Psychology', 3, 'PSYCH');
INSERT INTO course VALUES ('ART101', 'Art History', 3, 'ART');
INSERT INTO course VALUES ('MUS101', 'Music Appreciation', 3, 'MUS');
INSERT INTO course VALUES ('PHIL101', 'Introduction to Philosophy', 3, 'PHIL');

-- Classroom data
INSERT INTO classroom VALUES ('Taylor', '101', 120);
INSERT INTO classroom VALUES ('Taylor', '201', 60);
INSERT INTO classroom VALUES ('Taylor', '301', 40);
INSERT INTO classroom VALUES ('Watson', '100', 90);
INSERT INTO classroom VALUES ('Watson', '200', 75);
INSERT INTO classroom VALUES ('Watson', '300', 50);
INSERT INTO classroom VALUES ('Newton', '202', 70);
INSERT INTO classroom VALUES ('Newton', '302', 65);
INSERT INTO classroom VALUES ('Einstein', '105', 80);
INSERT INTO classroom VALUES ('Einstein', '205', 60);
INSERT INTO classroom VALUES ('Sullivan', '210', 50);
INSERT INTO classroom VALUES ('Sullivan', '310', 45);
INSERT INTO classroom VALUES ('Mendeleev', '110', 85);
INSERT INTO classroom VALUES ('Hawking', '120', 100);
INSERT INTO classroom VALUES ('Smith', '130', 70);
INSERT INTO classroom VALUES ('Freud', '140', 60);
INSERT INTO classroom VALUES ('Picasso', '150', 40);
INSERT INTO classroom VALUES ('Mozart', '160', 35);
INSERT INTO classroom VALUES ('Aristotle', '170', 45);

-- Section data
INSERT INTO section VALUES ('1', 'Fall', 2024, 'CS101');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'CS101');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'CS201');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'CS301');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'MATH101');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'MATH201');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'BIO101');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'BIO201');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'PHYS101');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'HIST101');
INSERT INTO section VALUES ('2', 'Fall', 2024, 'CS101');
INSERT INTO section VALUES ('2', 'Spring', 2025, 'MATH101');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'CS401');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'CS501');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'CS601');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'MATH301');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'MATH401');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'MATH501');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'BIO301');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'BIO401');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'PHYS201');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'PHYS301');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'HIST201');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'HIST301');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'CHEM101');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'CHEM201');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'ENG101');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'ENG201');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'ECON101');
INSERT INTO section VALUES ('1', 'Spring', 2025, 'ECON201');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'PSYCH101');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'ART101');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'MUS101');
INSERT INTO section VALUES ('1', 'Fall', 2024, 'PHIL101');

-- Time_Slot data
INSERT INTO time_slot VALUES ('A', 'Monday', '09:00:00', '10:30:00');
INSERT INTO time_slot VALUES ('A', 'Wednesday', '09:00:00', '10:30:00');
INSERT INTO time_slot VALUES ('B', 'Tuesday', '11:00:00', '12:30:00');
INSERT INTO time_slot VALUES ('B', 'Thursday', '11:00:00', '12:30:00');
INSERT INTO time_slot VALUES ('C', 'Monday', '14:00:00', '15:30:00');
INSERT INTO time_slot VALUES ('C', 'Wednesday', '14:00:00', '15:30:00');
INSERT INTO time_slot VALUES ('D', 'Tuesday', '15:00:00', '16:30:00');
INSERT INTO time_slot VALUES ('D', 'Thursday', '15:00:00', '16:30:00');
INSERT INTO time_slot VALUES ('E', 'Friday', '10:00:00', '12:00:00');

-- Advisor relationships
INSERT INTO advisor VALUES ('S1001', 'I101');
INSERT INTO advisor VALUES ('S1002', 'I101');
INSERT INTO advisor VALUES ('S1003', 'I102');
INSERT INTO advisor VALUES ('S1004', 'I103');
INSERT INTO advisor VALUES ('S1005', 'I104');
INSERT INTO advisor VALUES ('S1006', 'I105');
INSERT INTO advisor VALUES ('S1007', 'I106');
INSERT INTO advisor VALUES ('S1008', 'I102');
INSERT INTO advisor VALUES ('S1009', 'I104');
INSERT INTO advisor VALUES ('S1010', 'I103');
INSERT INTO advisor VALUES ('S1011', 'I107');
INSERT INTO advisor VALUES ('S1012', 'I108');
INSERT INTO advisor VALUES ('S1013', 'I109');
INSERT INTO advisor VALUES ('S1014', 'I110');
INSERT INTO advisor VALUES ('S1015', 'I111');
INSERT INTO advisor VALUES ('S1016', 'I112');
INSERT INTO advisor VALUES ('S1017', 'I113');
INSERT INTO advisor VALUES ('S1018', 'I114');
INSERT INTO advisor VALUES ('S1019', 'I117');
INSERT INTO advisor VALUES ('S1020', 'I116');
INSERT INTO advisor VALUES ('S1021', 'I115');
INSERT INTO advisor VALUES ('S1022', 'I106');
INSERT INTO advisor VALUES ('S1023', 'I107');
INSERT INTO advisor VALUES ('S1024', 'I108');
INSERT INTO advisor VALUES ('S1025', 'I109');

-- Teaches relationships
INSERT INTO teaches VALUES ('I101', '1', 'Fall', 2024, 'CS101');
INSERT INTO teaches VALUES ('I104', '2', 'Fall', 2024, 'CS101');
INSERT INTO teaches VALUES ('I104', '1', 'Fall', 2024, 'CS201');
INSERT INTO teaches VALUES ('I101', '1', 'Spring', 2025, 'CS101');
INSERT INTO teaches VALUES ('I104', '1', 'Spring', 2025, 'CS301');
INSERT INTO teaches VALUES ('I102', '1', 'Fall', 2024, 'MATH101');
INSERT INTO teaches VALUES ('I102', '1', 'Spring', 2025, 'MATH201');
INSERT INTO teaches VALUES ('I102', '2', 'Spring', 2025, 'MATH101');
INSERT INTO teaches VALUES ('I103', '1', 'Fall', 2024, 'BIO101');
INSERT INTO teaches VALUES ('I103', '1', 'Spring', 2025, 'BIO201');
INSERT INTO teaches VALUES ('I105', '1', 'Fall', 2024, 'PHYS101');
INSERT INTO teaches VALUES ('I106', '1', 'Fall', 2024, 'HIST101');
-- Additional instructor teaching assignments
INSERT INTO teaches VALUES ('I114', '1', 'Fall', 2024, 'CS401');
INSERT INTO teaches VALUES ('I114', '1', 'Spring', 2025, 'CS501');
INSERT INTO teaches VALUES ('I104', '1', 'Fall', 2024, 'CS601');
INSERT INTO teaches VALUES ('I117', '1', 'Fall', 2024, 'MATH301');
INSERT INTO teaches VALUES ('I117', '1', 'Spring', 2025, 'MATH401');
INSERT INTO teaches VALUES ('I102', '1', 'Fall', 2024, 'MATH501');
INSERT INTO teaches VALUES ('I116', '1', 'Fall', 2024, 'BIO301');
INSERT INTO teaches VALUES ('I116', '1', 'Spring', 2025, 'BIO401');
INSERT INTO teaches VALUES ('I115', '1', 'Fall', 2024, 'PHYS201');
INSERT INTO teaches VALUES ('I115', '1', 'Spring', 2025, 'PHYS301');
INSERT INTO teaches VALUES ('I106', '1', 'Fall', 2024, 'HIST201');
INSERT INTO teaches VALUES ('I106', '1', 'Spring', 2025, 'HIST301');
INSERT INTO teaches VALUES ('I107', '1', 'Fall', 2024, 'CHEM101');
INSERT INTO teaches VALUES ('I107', '1', 'Spring', 2025, 'CHEM201');
INSERT INTO teaches VALUES ('I108', '1', 'Fall', 2024, 'ENG101');
INSERT INTO teaches VALUES ('I108', '1', 'Spring', 2025, 'ENG201');
INSERT INTO teaches VALUES ('I109', '1', 'Fall', 2024, 'ECON101');
INSERT INTO teaches VALUES ('I109', '1', 'Spring', 2025, 'ECON201');
INSERT INTO teaches VALUES ('I110', '1', 'Fall', 2024, 'PSYCH101');
INSERT INTO teaches VALUES ('I111', '1', 'Fall', 2024, 'ART101');
INSERT INTO teaches VALUES ('I112', '1', 'Fall', 2024, 'MUS101');
INSERT INTO teaches VALUES ('I113', '1', 'Fall', 2024, 'PHIL101');

-- Takes relationships with grades
INSERT INTO takes VALUES ('S1001', '1', 'Fall', 2024, 'CS101', 'A');
INSERT INTO takes VALUES ('S1001', '1', 'Fall', 2024, 'MATH101', 'B');
INSERT INTO takes VALUES ('S1002', '1', 'Fall', 2024, 'CS101', 'A-');
INSERT INTO takes VALUES ('S1002', '1', 'Fall', 2024, 'CS201', 'B+');
INSERT INTO takes VALUES ('S1003', '1', 'Fall', 2024, 'MATH101', 'A');
INSERT INTO takes VALUES ('S1003', '1', 'Spring', 2025, 'MATH201', 'A');
INSERT INTO takes VALUES ('S1004', '1', 'Fall', 2024, 'BIO101', 'B');
INSERT INTO takes VALUES ('S1004', '1', 'Spring', 2025, 'BIO201', 'B+');
INSERT INTO takes VALUES ('S1005', '1', 'Fall', 2024, 'CS201', 'A');
INSERT INTO takes VALUES ('S1005', '1', 'Spring', 2025, 'CS301', 'A-');
INSERT INTO takes VALUES ('S1006', '1', 'Fall', 2024, 'PHYS101', 'C+');
INSERT INTO takes VALUES ('S1007', '1', 'Fall', 2024, 'HIST101', 'B');
INSERT INTO takes VALUES ('S1008', '2', 'Spring', 2025, 'MATH101', 'A-');
INSERT INTO takes VALUES ('S1009', '2', 'Fall', 2024, 'CS101', 'B+');
INSERT INTO takes VALUES ('S1010', '1', 'Fall', 2024, 'BIO101', 'A');
INSERT INTO takes VALUES ('S1010', '1', 'Spring', 2025, 'BIO201', 'A');
-- Adding more takes entries for variety in semesters
INSERT INTO takes VALUES ('S1001', '1', 'Spring', 2025, 'CS101', 'A+');
INSERT INTO takes VALUES ('S1002', '1', 'Spring', 2025, 'CS301', 'B');
INSERT INTO takes VALUES ('S1003', '2', 'Spring', 2025, 'MATH101', 'A');
INSERT INTO takes VALUES ('S1004', '1', 'Fall', 2024, 'PHYS101', 'C');
INSERT INTO takes VALUES ('S1005', '1', 'Fall', 2024, 'HIST101', 'B+');

-- Enrollment data for new students and courses
INSERT INTO takes VALUES ('S1011', '1', 'Fall', 2024, 'CHEM101', 'B+');
INSERT INTO takes VALUES ('S1011', '1', 'Spring', 2025, 'CHEM201', 'B');
INSERT INTO takes VALUES ('S1012', '1', 'Fall', 2024, 'ENG101', 'A');
INSERT INTO takes VALUES ('S1012', '1', 'Spring', 2025, 'ENG201', 'A-');
INSERT INTO takes VALUES ('S1013', '1', 'Fall', 2024, 'ECON101', 'B+');
INSERT INTO takes VALUES ('S1013', '1', 'Spring', 2025, 'ECON201', 'A-');
INSERT INTO takes VALUES ('S1014', '1', 'Fall', 2024, 'PSYCH101', 'A');
INSERT INTO takes VALUES ('S1015', '1', 'Fall', 2024, 'ART101', 'A-');
INSERT INTO takes VALUES ('S1016', '1', 'Fall', 2024, 'MUS101', 'A+');
INSERT INTO takes VALUES ('S1017', '1', 'Fall', 2024, 'PHIL101', 'B+');
INSERT INTO takes VALUES ('S1018', '1', 'Fall', 2024, 'CS401', 'A-');
INSERT INTO takes VALUES ('S1018', '1', 'Spring', 2025, 'CS501', 'B+');
INSERT INTO takes VALUES ('S1019', '1', 'Fall', 2024, 'MATH301', 'A');
INSERT INTO takes VALUES ('S1019', '1', 'Spring', 2025, 'MATH401', 'B+');
INSERT INTO takes VALUES ('S1020', '1', 'Fall', 2024, 'BIO301', 'A-');
INSERT INTO takes VALUES ('S1020', '1', 'Spring', 2025, 'BIO401', 'A');
INSERT INTO takes VALUES ('S1021', '1', 'Fall', 2024, 'PHYS201', 'B');
INSERT INTO takes VALUES ('S1021', '1', 'Spring', 2025, 'PHYS301', 'B-');
INSERT INTO takes VALUES ('S1022', '1', 'Fall', 2024, 'HIST201', 'B+');
INSERT INTO takes VALUES ('S1022', '1', 'Spring', 2025, 'HIST301', 'A-');
INSERT INTO takes VALUES ('S1023', '1', 'Fall', 2024, 'CHEM101', 'A');
INSERT INTO takes VALUES ('S1024', '1', 'Fall', 2024, 'ENG101', 'A-');
INSERT INTO takes VALUES ('S1025', '1', 'Fall', 2024, 'ECON101', 'B');

-- Some students taking multiple courses in the same semester
INSERT INTO takes VALUES ('S1001', '1', 'Fall', 2024, 'CS401', 'B+');
INSERT INTO takes VALUES ('S1002', '1', 'Fall', 2024, 'CS401', 'A-');
INSERT INTO takes VALUES ('S1005', '1', 'Fall', 2024, 'CS401', 'A');
INSERT INTO takes VALUES ('S1005', '1', 'Fall', 2024, 'CS601', 'A-');
INSERT INTO takes VALUES ('S1009', '1', 'Fall', 2024, 'CS401', 'B');
INSERT INTO takes VALUES ('S1009', '1', 'Fall', 2024, 'CS601', 'B+');
INSERT INTO takes VALUES ('S1018', '1', 'Fall', 2024, 'CS601', 'A');

-- Sec_time_slot relationships
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'CS101', 'A', 'Monday', '09:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'CS101', 'A', 'Wednesday', '09:00:00');
INSERT INTO sec_time_slot VALUES ('2', 'Fall', 2024, 'CS101', 'B', 'Tuesday', '11:00:00');
INSERT INTO sec_time_slot VALUES ('2', 'Fall', 2024, 'CS101', 'B', 'Thursday', '11:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'CS201', 'C', 'Monday', '14:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Spring', 2025, 'CS101', 'A', 'Monday', '09:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'MATH101', 'D', 'Tuesday', '15:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'BIO101', 'E', 'Friday', '10:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'PHYS101', 'C', 'Monday', '14:00:00');
INSERT INTO sec_time_slot VALUES ('1', 'Fall', 2024, 'HIST101', 'B', 'Tuesday', '11:00:00');

-- Sec_class relationships
INSERT INTO sec_class VALUES ('1', 'Fall', 2024, 'CS101', 'Taylor', '101');
INSERT INTO sec_class VALUES ('2', 'Fall', 2024, 'CS101', 'Taylor', '201');
INSERT INTO sec_class VALUES ('1', 'Fall', 2024, 'CS201', 'Taylor', '101');
INSERT INTO sec_class VALUES ('1', 'Spring', 2025, 'CS101', 'Taylor', '101');
INSERT INTO sec_class VALUES ('1', 'Spring', 2025, 'CS301', 'Taylor', '201');
INSERT INTO sec_class VALUES ('1', 'Fall', 2024, 'MATH101', 'Newton', '202');
INSERT INTO sec_class VALUES ('1', 'Spring', 2025, 'MATH201', 'Newton', '202');
INSERT INTO sec_class VALUES ('2', 'Spring', 2025, 'MATH101', 'Newton', '202');
INSERT INTO sec_class VALUES ('1', 'Fall', 2024, 'BIO101', 'Watson', '100');
INSERT INTO sec_class VALUES ('1', 'Spring', 2025, 'BIO201', 'Watson', '100');
INSERT INTO sec_class VALUES ('1', 'Fall', 2024, 'PHYS101', 'Einstein', '105');
INSERT INTO sec_class VALUES ('1', 'Fall', 2024, 'HIST101', 'Sullivan', '210');

-- Prereq relationships
INSERT INTO prereq VALUES ('CS201', 'CS101');
INSERT INTO prereq VALUES ('CS301', 'CS201');
INSERT INTO prereq VALUES ('MATH201', 'MATH101');
INSERT INTO prereq VALUES ('BIO201', 'BIO101');
