DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS admins CASCADE;
DROP TABLE IF EXISTS classes CASCADE;
DROP TABLE IF EXISTS lessons CASCADE;
DROP TABLE IF EXISTS marks CASCADE;
DROP TABLE IF EXISTS hometasks CASCADE;

CREATE TABLE teachers(
    id serial primary key, 
    login varchar(64) NOT NULL,
    firstName varchar(64) NOT NULL, 
    lastName varchar(64) NOT NULL, 
    patronymic varchar(64)
    );
CREATE TABLE classes(
    id serial primary key, 
    name varchar(32) NOT NULL
    );
CREATE TABLE students(
    id serial primary key, 
    class integer references classes(id) NOT NULL, 
    login varchar(64) NOT NULL, 
    firstName varchar(64) NOT NULL, 
    lastName varchar(64) NOT NULL, 
    patronymic varchar(64)
    );
CREATE TABLE admins(
    id serial primary key, 
    login varchar(64) NOT NULL, 
    firstName varchar(64) NOT NULL,
    lastName varchar(64) NOT NULL, 
    patronymic varchar(64)
    );
CREATE TABLE lessons(
    id serial primary key,
    name varchar(256) NOT NULL
    );
CREATE TABLE marks(
    id serial primary key,
    mark integer check(mark > 0 AND mark <= 5) NOT NULL, 
    teacher integer references teachers(id) NOT NULL,
    student integer references students(id) NOT NULL,
    lesson integer references lessons(id) NOT NULL,
    markDate date NOT NULL
    );
CREATE TABLE hometasks(
    id serial primary key,
    teacher integer references teachers(id) NOT NULL,
    class integer references classes(id) NOT NULL,
    tasks text,
    homataskDate date NOT NULL 
    );

ALTER TABLE marks ENABLE ROW LEVEL SECURITY;
ALTER TABLE admins ENABLE ROW LEVEL SECURITY;
ALTER TABLE teachers ENABLE ROW LEVEL SECURITY;
ALTER TABLE students ENABLE ROW LEVEL SECURITY;
ALTER TABLE hometasksc, ENABLE ROW LEVEL SECURITY;