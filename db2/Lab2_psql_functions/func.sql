-- Информация о поставке в дату на кол-во денег 
CREATE TABLE IF NOT EXISTS
    supply_data(
        provider_id INTEGER NOT NULL,
        firstname VARCHAR(64) NOT NULL,
        lastname VARCHAR(64) NOT NULL,
        patronymic VARCHAR(64) NOT NULL,
        date DATE NOT NULL,
        total INTEGER NOT NULL
    );
-- сумма продаж на покупателя в конкретный день
CREATE TABLE IF NOT EXISTS
    sale_data(
        buyer_id INTEGER NOT NULL,
        firstname VARCHAR(64) NOT NULL,
        lastname VARCHAR(64) NOT NULL,
        patronymic VARCHAR(64),
        date DATE NOT NULL,
        total INTEGER NOT NULL
    );
-- Продано в день
CREATE TABLE IF NOT EXISTS
    income_data(
        date DATE NOT NULL,
        total INTEGER NOT NULL
    );
-- Затраты в день
CREATE TABLE IF NOT EXISTS
    lesion_data(
        date DATE NOT NULL,
        total INTEGER NOT NULL
    );
-- Кол-во поставок в стуки для каждого продукта
CREATE TABLE IF NOT EXISTS
    supply_quantity_data(
        product_id INTEGER NOT NULL,
        name VARCHAR(64) NOT NULL,
        date DATE NOT NULL,
        quantity INTEGER NOT NULL
    );
-- Кол-во продаж товара в день
CREATE TABLE IF NOT EXISTS
    sale_quantity_data(
        product_id INTEGER NOT NULL,
        name VARCHAR(64) NOT NULL,
        date DATE NOT NULL,
        quantity INTEGER NOT NULL
    );
-- Средний чек в день
CREATE TABLE IF NOT EXISTS
    avg_sale_data(
        date DATE NOT NULL,
        total INTEGER NOT NULL
    );

CREATE OR REPLACE FUNCTION createAvgSaleDataTable() RETURNS VOID AS
$$
DECLARE
    rec RECORD;
    cur CURSOR FOR
        SELECT date_trunc('day', sale_date) as date, (sum(price * quantity)/count(id)) as total
        FROM sale
        GROUP BY date
        ORDER BY date;
BEGIN
    TRUNCATE TABLE avg_sale_data;

    OPEN cur;
    LOOP
        FETCH cur INTO rec;

        IF NOT FOUND
        THEN
            EXIT;
        END IF;

        INSERT INTO avg_sale_data VALUES(rec.date, rec.total);
    END LOOP;
    CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createSupplyDataTable() RETURNS VOID AS
$$
DECLARE
	rec RECORD;
	cur CURSOR FOR 
        SELECT provider_id, firstname, lastname, patronymic, date_trunc('day', supply_date) AS date, sum(price * quantity) AS total
        FROM supply LEFT JOIN provider ON (supply.provider_id = provider.id) 
        GROUP BY (provider_id, firstname, lastname, patronymic, date_trunc('day', supply_date))
        ORDER BY (provider_id, date_trunc('day', supply_date));
BEGIN
    TRUNCATE TABLE supply_data;

	OPEN cur;
	LOOP
		FETCH cur INTO rec;
		
        IF NOT FOUND
		THEN
			EXIT;
		END IF;
        
        INSERT INTO supply_data VALUES(rec.provider_id, rec.firstname, rec.lastname, rec.patronymic, rec.date, rec.total);
	END LOOP;
	CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createSaleDataTable() RETURNS VOID AS
$$
DECLARE
	rec RECORD;
	cur CURSOR FOR 
        SELECT buyer_id, firstname, lastname, patronymic, date_trunc('day', sale_date) AS date, sum(price * quantity) AS total
        FROM sale LEFT JOIN buyer ON (sale.buyer_id = buyer.id) 
        GROUP BY (buyer_id, firstname, lastname, patronymic, date_trunc('day', sale_date))
        ORDER BY (buyer_id, date_trunc('day', sale_date));
BEGIN
    TRUNCATE TABLE sale_data;

	OPEN cur;
	LOOP
		FETCH cur INTO rec;
		
        IF NOT FOUND
		THEN
			EXIT;
		END IF;
        
        INSERT INTO sale_data VALUES(rec.buyer_id, rec.firstname, rec.lastname, rec.patronymic, rec.date, rec.total);
	END LOOP;
	CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createIncomeDataTable() RETURNS VOID AS
$$
DECLARE
	rec RECORD;
	cur CURSOR FOR 
        SELECT date, sum(total) AS total FROM sale_data GROUP BY date ORDER BY date;
BEGIN
    TRUNCATE TABLE income_data;

	OPEN cur;
	LOOP
		FETCH cur INTO rec;
		
        IF NOT FOUND
		THEN
			EXIT;
		END IF;
        
        INSERT INTO income_data VALUES(rec.date, rec.total);
	END LOOP;
	CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createLesionDataTable() RETURNS VOID AS
$$
DECLARE
	rec RECORD;
	cur CURSOR FOR 
        SELECT date, sum(total) AS total FROM supply_data GROUP BY date ORDER BY date;
BEGIN
    TRUNCATE TABLE lesion_data;

	OPEN cur;
	LOOP
		FETCH cur INTO rec;
		
        IF NOT FOUND
		THEN
			EXIT;
		END IF;
        
        INSERT INTO lesion_data VALUES(rec.date, rec.total);
	END LOOP;
	CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createSupplyQuantityDataTable() RETURNS VOID AS
$$
DECLARE
    rec RECORD;
	cur CURSOR FOR 
        SELECT product_id, name, date_trunc('day', supply_date) AS date, sum(quantity) AS quantity
        FROM supply LEFT JOIN product ON (supply.product_id = product.id)
        GROUP BY (product_id, name, date_trunc('day', supply_date))
        ORDER BY (product_id, date_trunc('day', supply_date));
BEGIN
    TRUNCATE TABLE supply_quantity_data;
    
    OPEN cur;
	LOOP
		FETCH cur INTO rec;
		
        IF NOT FOUND
		THEN
			EXIT;
		END IF;
        
        INSERT INTO supply_quantity_data VALUES(rec.product_id, rec.name, rec.date, rec.quantity);
	END LOOP;
	CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createSaleQuantityDataTable() RETURNS VOID AS
$$
DECLARE
    rec RECORD;
	cur CURSOR FOR 
        SELECT product_id, name, date_trunc('day', sale_date) AS date, sum(quantity) AS quantity
        FROM sale LEFT JOIN product ON (sale.product_id = product.id)
        GROUP BY (product_id, name, date_trunc('day', sale_date))
        ORDER BY (product_id, date_trunc('day', sale_date));
BEGIN
    TRUNCATE TABLE sale_quantity_data;
    
    OPEN cur;
	LOOP
		FETCH cur INTO rec;
		
        IF NOT FOUND
		THEN
			EXIT;
		END IF;
        
        INSERT INTO sale_quantity_data VALUES(rec.product_id, rec.name, rec.date, rec.quantity);
	END LOOP;
	CLOSE cur;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION createDataIntoTables() RETURNS VOID AS
$$
BEGIN
    PERFORM createSupplyDataTable();
    PERFORM createSaleDataTable();
    PERFORM createLesionDataTable();
    PERFORM createIncomeDataTable();
    PERFORM createSupplyQuantityDataTable();
    PERFORM createSaleQuantityDataTable();
    PERFORM createAvgSaleDataTable();
END;
$$
LANGUAGE PLPGSQL;