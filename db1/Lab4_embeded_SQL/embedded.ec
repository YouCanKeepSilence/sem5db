#include "embedded.h"
#include <string.h>

#define SQLCHECK if(sqlca.sqlcode) { \
	printf("SQLERROR(%d): '%s' '%.5s' %ld\n", __LINE__, sqlca.sqlerrm.sqlerrmc, sqlca.sqlstate, sqlca.sqlcode);\
	commit(); \
	return 1;\
	}

void commit() {
    EXEC SQL COMMIT;
}

int connect(const char * pass) {
    
    EXEC SQL CONNECT TO silence USER silence;
    SQLCHECK;
    commit();
    return 0;
}
EXEC SQL BEGIN DECLARE SECTION;
    struct ansA {
        char date[128];
        int number;
        char title[128];
    };
EXEC SQL END DECLARE SECTION;
int taskA(int sum, const char * profname) {
    EXEC SQL BEGIN DECLARE SECTION;
        int sqlreq_sum = sum;
        char sqlreq_profname[128];
        struct ansA sqlres;
    EXEC SQL END DECLARE SECTION;
    strcpy(sqlreq_profname, profname);

    EXEC SQL DECLARE cursorA CURSOR FOR
        SELECT agreement.date , agreement.number , employer.title FROM agreement  
        INNER JOIN profession ON agreement.id_of_profession = profession.id
        INNER JOIN employer ON agreement.id_of_employer = employer.id
        WHERE agreement.pay >= :sqlreq_sum OR profession.title = :sqlreq_profname;
    SQLCHECK;
	EXEC SQL OPEN cursorA;
	SQLCHECK;

	while(1) {
		EXEC SQL FETCH cursorA INTO :sqlres;
		if(sqlca.sqlcode) 
		{
			break;
		}
		printf("Название предприятия: %s , Дата договора: %s, Номер договора: %d\r\n", sqlres.title, sqlres.date, sqlres.number);
	}

	EXEC SQL CLOSE cursorA;
	SQLCHECK;
	commit();
	return 0;
}

EXEC SQL BEGIN DECLARE SECTION;
    struct ansB {
        int number;
        char location[128];
    };
EXEC SQL END DECLARE SECTION;

int taskB(int benefit, const char * date) {
    EXEC SQL BEGIN DECLARE SECTION;
        int sqlreq_benefit = benefit;
        char sqlreq_date[128];
        struct ansB sqlres;
    EXEC SQL END DECLARE SECTION;
    strcpy(sqlreq_date , date);

    EXEC SQL DECLARE cursorB CURSOR FOR
        SELECT DISTINCT bureau.number , bureau.location FROM agreement
        INNER JOIN bureau ON agreement.id_of_bureau = bureau.id
        INNER JOIN employer ON agreement.id_of_employer = employer.id
        WHERE employer.benefit < :sqlreq_benefit AND agreement.date >= :sqlreq_date;
    SQLCHECK;
	EXEC SQL OPEN cursorB;
	SQLCHECK;

	while(1) {
		EXEC SQL FETCH cursorB INTO :sqlres;
		if(sqlca.sqlcode) 
		{
			break;
		}
		printf("Номер: %d , Адрес: %s \r\n", sqlres.number, sqlres.location);
	}

	EXEC SQL CLOSE cursorB;
	SQLCHECK;
	commit();
	return 0;
}

EXEC SQL BEGIN DECLARE SECTION;
    struct ansC {
        char title[128];
    };
EXEC SQL END DECLARE SECTION;

int taskC(int fee, const char * location) {
    EXEC SQL BEGIN DECLARE SECTION;
        int sqlreq_fee = fee;
        char sqlreq_location[128];
        struct ansC sqlres;
    EXEC SQL END DECLARE SECTION;
    strcpy(sqlreq_location , location);

    EXEC SQL DECLARE cursorC CURSOR FOR
        select distinct employer.title from employer
        inner join agreement ON (agreement.id_of_employer = employer.id)
        inner join bureau ON (agreement.id_of_bureau = bureau.id)
        where employer.location != :sqlreq_location and bureau.service_fee > :sqlreq_fee;
    SQLCHECK;
	EXEC SQL OPEN cursorC;
	SQLCHECK;

	while(1) {
		EXEC SQL FETCH cursorC INTO :sqlres;
		if(sqlca.sqlcode) 
		{
			break;
		}
		printf("Название: %s \r\n", sqlres.title);
	}

	EXEC SQL CLOSE cursorC;
	SQLCHECK;
	commit();
	return 0;
}

EXEC SQL BEGIN DECLARE SECTION;
    struct ansD {
        int number;
        char date[128];
        int pay;
    };
EXEC SQL END DECLARE SECTION;

int taskD() {
    EXEC SQL BEGIN DECLARE SECTION;
        struct ansD sqlres;
    EXEC SQL END DECLARE SECTION;

    EXEC SQL DECLARE cursorD CURSOR FOR
        SELECT agreement.number , agreement.date , agreement.pay FROM agreement
        INNER JOIN profession ON agreement.id_of_profession = profession.id
        INNER JOIN employer ON agreement.id_of_employer = employer.id
        WHERE profession.previous_location = employer.location
        ORDER BY agreement.pay ASC;
    SQLCHECK;
	EXEC SQL OPEN cursorD;
	SQLCHECK;

	while(1) {
		EXEC SQL FETCH cursorD INTO :sqlres;
		if(sqlca.sqlcode) 
		{
			break;
		}
		printf("Номер: %d, Дата: %s, Стоимость: %d \r\n", sqlres.number, sqlres.date, sqlres.pay);
	}

	EXEC SQL CLOSE cursorD;
	SQLCHECK;
	commit();
	return 0;
}