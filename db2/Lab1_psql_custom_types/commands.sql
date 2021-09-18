-- Создание типа собака (нужен в сете)
CREATE TYPE _dogs AS (name varchar(64),age smallint);

-- Создание функции возвращающей сет собак
CREATE OR REPLACE FUNCTION allDogs()
    RETURNS SETOF _dogs
    AS '/Users/silence/Workbench/archive/Workbench_mbp13/ProcessedStudy/sem5db/db2/Lab1/doglib.so', 'get_all_dogs'
    LANGUAGE C IMMUTABLE STRICT;