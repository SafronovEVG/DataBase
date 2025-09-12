SELECT s.age, s.name, f.name
FROM student s LEFT JOIN faculty f
    ON f.id = s.faculty.id;

SELECT age, name
FROM students INNER JOIN student_avatar
ON id = student_avatar.id;