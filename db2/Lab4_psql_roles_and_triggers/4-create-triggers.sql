CREATE OR REPLACE FUNCTION addTeacher()
RETURNS TRIGGER AS $addTeacher$
    BEGIN
        IF (TG_OP = 'UPDATE' AND NEW.login IS NOT NULL) THEN
            EXECUTE 'REVOKE teacher FROM ' || OLD.login;
            EXECUTE 'GRANT teacher TO ' || NEW.login;
            RETURN NULL;
        ELSIF (TG_OP = 'INSERT' AND NEW.login IS NOT NULL) THEN
            EXECUTE 'GRANT teacher TO ' || NEW.login;
            RETURN NULL;
        ELSIF (TG_OP = 'DELETE') THEN
            EXECUTE 'REVOKE teacher FROM ' || OLD.login;
            RETURN NULL;
        END IF;
        RETURN NULL;
    END;
$addTeacher$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION addStudent()
RETURNS TRIGGER AS $addStudent$
    BEGIN
        IF (TG_OP = 'UPDATE' AND NEW.login IS NOT NULL) THEN
            EXECUTE 'REVOKE student FROM ' || OLD.login;
            EXECUTE 'GRANT student TO ' || NEW.login;
            RETURN NULL;
        ELSIF (TG_OP = 'INSERT' AND NEW.login IS NOT NULL) THEN
            EXECUTE 'GRANT student TO ' || NEW.login;
            RETURN NULL;
        ELSIF (TG_OP = 'DELETE') THEN
            EXECUTE 'REVOKE student FROM ' || OLD.login;
            RETURN NULL;
        END IF;
        RETURN NULL;
    END;
$addStudent$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION addAdmin()
RETURNS TRIGGER AS $addAdmin$
    BEGIN
        IF (TG_OP = 'UPDATE' AND NEW.login IS NOT NULL) THEN
            EXECUTE 'REVOKE admin FROM ' || OLD.login;
            EXECUTE 'ALTER ROLE ' || OLD.login || ' WITH NOCREATEROLE';
            EXECUTE 'GRANT admin TO ' || NEW.login;
            EXECUTE 'ALTER ROLE ' || NEW.login || ' WITH CREATEROLE';
            RETURN NULL;
        ELSIF (TG_OP = 'INSERT' AND NEW.login IS NOT NULL) THEN
            EXECUTE 'GRANT admin TO ' || NEW.login;
            EXECUTE 'ALTER ROLE ' || NEW.login || ' WITH CREATEROLE';
            RETURN NULL;
        ELSIF (TG_OP = 'DELETE') THEN
            EXECUTE 'REVOKE admin FROM ' || OLD.login;
            EXECUTE 'ALTER ROLE ' || OLD.login || ' WITH NOCREATEROLE';
            RETURN NULL;
        END IF;
        RETURN NULL;
    END;
$addAdmin$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS addTeacher ON teachers CASCADE;
DROP TRIGGER IF EXISTS addStudent ON students CASCADE;
DROP TRIGGER IF EXISTS addAdmin ON admins CASCADE;

CREATE TRIGGER addTeacher AFTER INSERT OR UPDATE OR DELETE ON teachers
    FOR EACH ROW EXECUTE PROCEDURE addTeacher();

CREATE TRIGGER addStudent AFTER INSERT OR UPDATE OR DELETE ON students
    FOR EACH ROW EXECUTE PROCEDURE addStudent();

CREATE TRIGGER addAdmin AFTER INSERT OR UPDATE OR DELETE ON admins
    FOR EACH ROW EXECUTE PROCEDURE addAdmin();