-- REVOKE admin FROM public;
-- REVOKE teacher FROM public;
-- REVOKE student FROM public;
DROP POLICY IF EXISTS adminToAdmins ON admins;
DROP POLICY IF EXISTS adminToTeachers ON teachers;
DROP POLICY IF EXISTS adminToStudents ON students;
DROP POLICY IF EXISTS teacherToTeachers ON teachers;
DROP POLICY IF EXISTS studentToStudents ON students;
DROP POLICY IF EXISTS teacherToStudents ON students;


DROP POLICY IF EXISTS marksAdmin ON marks;
DROP POLICY IF EXISTS hometasksAdmin ON hometasks;

DROP POLICY IF EXISTS marksTeacher ON marks;
DROP POLICY IF EXISTS hometasksTeacher ON hometasks;

DROP POLICY IF EXISTS marksStudent ON marks;
DROP POLICY IF EXISTS hometasksStudent ON hometasks;

REVOKE ALL ON admins FROM admin;
REVOKE ALL ON teachers FROM admin;
REVOKE ALL ON students FROM admin;
REVOKE ALL ON marks FROM admin;
REVOKE ALL ON lessons FROM admin;
REVOKE ALL ON classes FROM admin;
REVOKE ALL ON hometasks FROM admin;
REVOKE USAGE ON classes_id_seq FROM admin;
REVOKE USAGE ON hometasks_id_seq FROM admin;
REVOKE USAGE ON marks_id_seq FROM admin;
REVOKE USAGE ON students_id_seq FROM admin;
REVOKE USAGE ON lessons_id_seq FROM admin;
REVOKE USAGE ON teachers_id_seq FROM admin;
REVOKE ALL ON FUNCTION addTeacher FROM admin;
REVOKE ALL ON FUNCTION addStudent FROM admin;

REVOKE ALL ON lessons FROM teacher;
REVOKE ALL ON students FROM teacher;
REVOKE ALL ON marks FROM teacher;
REVOKE ALL ON hometasks FROM teacher;
REVOKE ALL ON classes FROM teacher;
REVOKE USAGE ON marks_id_seq FROM teacher;
REVOKE USAGE ON hometasks_id_seq FROM teacher;

REVOKE ALL ON marks FROM student;
REVOKE ALL ON hometasks FROM student;

DROP ROLE IF EXISTS admin;
DROP ROLE IF EXISTS teacher;
DROP ROLE IF EXISTS student;

CREATE ROLE teacher;
CREATE ROLE student;
CREATE ROLE admin WITH CREATEROLE;
GRANT teacher TO admin WITH ADMIN OPTION;
GRANT student TO admin WITH ADMIN OPTION;

GRANT SELECT ON admins TO admin;
GRANT ALL ON teachers TO admin;
GRANT ALL ON students TO admin;
GRANT ALL ON marks TO admin;
GRANT ALL ON lessons TO admin;
GRANT ALL ON classes TO admin;
GRANT ALL ON hometasks TO admin;
GRANT USAGE ON classes_id_seq TO admin;
GRANT USAGE ON hometasks_id_seq TO admin;
GRANT USAGE ON marks_id_seq TO admin;
GRANT USAGE ON students_id_seq TO admin;
GRANT USAGE ON lessons_id_seq TO admin;
GRANT USAGE ON teachers_id_seq TO admin;
GRANT ALL ON FUNCTION addTeacher TO admin;
GRANT ALL ON FUNCTION addStudent TO admin;

GRANT SELECT ON lessons TO teacher;
GRANT SELECT ON students TO teacher;
GRANT SELECT ON teachers TO teacher;
GRANT SELECT, INSERT, UPDATE, DELETE ON marks TO teacher;
GRANT SELECT, INSERT, UPDATE, DELETE ON hometasks TO teacher;
GRANT SELECT ON classes TO teacher;
GRANT USAGE ON marks_id_seq TO teacher;
GRANT USAGE ON hometasks_id_seq TO teacher;

GRANT SELECT ON students TO student;
GRANT SELECT ON marks TO student;
GRANT SELECT ON hometasks TO student;
GRANT SELECT ON classes TO student;
GRANT SELECT ON lessons TO student;

CREATE POLICY adminToAdmins ON admins TO admin
    USING(login = current_user)
    WITH CHECK(false);

CREATE POLICY adminToTeachers ON teachers TO admin
    USING(true)
    WITH CHECK(true);

CREATE POLICY adminToStudents ON students TO admin
    USING(true)
    WITH CHECK(true);

CREATE POLICY teacherToStudents ON students TO teacher
    USING(true)
    WITH CHECK(false);

CREATE POLICY teacherToTeachers ON teachers TO teacher
    USING(login = current_user)
    WITH CHECK(false);

CREATE POLICY studentToStudents ON students TO student
    USING(login = current_user)
    WITH CHECK(false);

-- Даем все права роли админа
CREATE POLICY marksAdmin ON marks TO admin 
    USING(true) 
    WITH CHECK(true);
CREATE POLICY hometasksAdmin ON hometasks TO admin 
    USING(true) 
    WITH CHECK(true);
-- Ограничиваем видимость учителей только той строкой , где они фигируруют
CREATE POLICY marksTeacher ON marks TO teacher 
    USING((SELECT teachers.login FROM teachers WHERE teachers.id = marks.teacher) = current_user)
    WITH CHECK((SELECT teachers.id FROM teachers WHERE teachers.login = current_user) = marks.teacher);
CREATE POLICY hometasksTeacher ON hometasks TO teacher
    USING((SELECT teachers.login FROM teachers WHERE teachers.id = hometasks.teacher) = current_user)
    WITH CHECK((SELECT teachers.id FROM teachers WHERE teachers.login = current_user) = hometasks.teacher);
-- Разрешаем ученикам только видеть свои оценки без возможности их изменения и задания своего класса (+Это защищено и запретом любых операций кроме select)
CREATE POLICY marksStudent ON marks TO student 
    USING((SELECT students.login FROM students WHERE students.id = marks.student) = current_user)
    WITH CHECK(false);
CREATE POLICY hometasksStudent ON hometasks TO student 
    USING((SELECT students.class FROM students WHERE students.login = current_user) = hometasks.class)
    WITH CHECK(false);