#include "people.h"
using namespace std;
PeopleService::PeopleService(OdbcEnvirioment * odbc)
{
	this->odbc = odbc;
}

void PeopleService::deleteById(int id)
{
	SQLHSTMT hstmt;

	if(!this->odbc)
	{
		cerr << "Не установлено окружение ODBC!";
		return;
	}

	if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
		cerr << "Error with AllocHandler";
		return; 
	}
	if (SQLPrepare(hstmt, (SQLCHAR *)"delete from peoples where id = ?", SQL_NTS) != SQL_SUCCESS) {
		cerr << "Error with prepare";
		return;
	}	
	SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id, 0, NULL);
	if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
		cerr << "Error with execute";
		return;
	}
	SQLCloseCursor(hstmt);
    return;
}

void PeopleService::updateById(int id, string name, string lastName, string patronymic, string comment)
{
    SQLHSTMT hstmt;

    if(!this->odbc)
    {
        cerr << "Не установлено окружение ODBC!";
        return;
    }

    if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
        cerr << "Error with AllocHandler";
        return;
    }

    SQLCHAR* statement = (unsigned char* ) "UPDATE peoples SET firstName = ?, lastName = ?, patronymic = ? , comment = ? where id = ?";

    if (SQLPrepare(hstmt, (SQLCHAR *)statement, SQL_NTS) != SQL_SUCCESS) {
        cerr << "Error with prepare";
        return;
    }
    SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)name.c_str(), name.length(), NULL);
    SQLBindParameter(hstmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)lastName.c_str(), lastName.length(), NULL);
    SQLBindParameter(hstmt, 3, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)patronymic.c_str(), patronymic.length(), NULL);
    SQLBindParameter(hstmt, 4, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 256, 0, (char*)comment.c_str(), comment.length(), NULL);
    SQLBindParameter(hstmt, 5, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id, 0, NULL);
    if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
        cerr << "Error with execute";
        return;
    }
    SQLCloseCursor(hstmt);
    return;

}

vector<PeopleEntity> PeopleService::find(string info)
{
    SQLHSTMT hstmt;
    info.insert(0,1,'%');
    info.push_back('%');
    std::vector<PeopleEntity> answer;
    if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
        cerr << "Error with AllocHandler";
        return answer;
    }

    if (SQLPrepare(hstmt, (SQLCHAR *) "SELECT id, firstName, lastName, patronymic, comment FROM peoples where firstName ilike (?) or lastName ilike (?) or patronymic ilike (?)", SQL_NTS) != SQL_SUCCESS) {
        cerr << "Error with prepare";
        return answer;
    }

    SQLINTEGER sql_id;
    SQLVARCHAR sql_name[64];
    SQLVARCHAR sql_lastName[64];
    SQLVARCHAR sql_patronymic[64];
    SQLVARCHAR sql_comment[256];

    SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)info.c_str(), info.length(), NULL);
    SQLBindParameter(hstmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)info.c_str(), info.length(), NULL);
    SQLBindParameter(hstmt, 3, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)info.c_str(), info.length(), NULL);

    SQLBindCol(hstmt, 1, SQL_C_LONG, &sql_id, sizeof(sql_id), NULL);
    SQLBindCol(hstmt, 2, SQL_C_CHAR, &sql_name, sizeof(sql_name), NULL);
    SQLBindCol(hstmt, 3, SQL_C_CHAR, &sql_lastName, sizeof(sql_lastName), NULL);
    SQLBindCol(hstmt, 4, SQL_C_CHAR, &sql_patronymic, sizeof(sql_patronymic), NULL);
    SQLBindCol(hstmt, 5, SQL_C_CHAR, &sql_comment, sizeof(sql_comment), NULL);

    if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
        cerr << "Error with execute";
        return answer;
    }

    while(1) {
        if(SQLFetch(hstmt) == SQL_NO_DATA) {
            break;
        }
        PeopleEntity man(sql_id, reinterpret_cast<char*>(sql_name), reinterpret_cast<char*>(sql_lastName), reinterpret_cast<char*>(sql_patronymic), reinterpret_cast<char*>(sql_comment));
        answer.push_back(man);
    }

    SQLCloseCursor(hstmt);
    return answer;
}

void PeopleService::add(string name, string lastName, string patronymic, string comment)
{
    SQLHSTMT hstmt;

    if(!this->odbc)
    {
        cerr << "Не установлено окружение ODBC!";
        return;
    }

    if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
        cerr << "Error with AllocHandler";
        return;
    }

    SQLCHAR* statement = nullptr;


    statement = (unsigned char *)"INSERT INTO peoples(firstName, lastName, patronymic, comment) values(?,?,?,?)";
    if (SQLPrepare(hstmt, (SQLCHAR *)statement, SQL_NTS) != SQL_SUCCESS) {
        cerr << "Error with prepare";
        return;
    }
    SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)name.c_str(), name.length(), NULL);
    SQLBindParameter(hstmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)lastName.c_str(), lastName.length(), NULL);
    SQLBindParameter(hstmt, 3, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)patronymic.c_str(), patronymic.length(), NULL);
    SQLBindParameter(hstmt, 4, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 256, 0, (char*)comment.c_str(), comment.length(), NULL);
    if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
        cerr << "Error with execute";
        return;
    }
    SQLCloseCursor(hstmt);
    return;
}
std::vector<PeopleEntity> PeopleService::getAll() 
{
	SQLHSTMT hstmt;
	std::vector<PeopleEntity> answer;
	if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
		cerr << "Error with AllocHandler";
		return answer; 
	}

	if (SQLPrepare(hstmt, (SQLCHAR *) "SELECT id, firstName, lastName, patronymic, comment FROM peoples", SQL_NTS) != SQL_SUCCESS) {
		cerr << "Error with prepare";
		return answer;
	}

	SQLINTEGER sql_id;
	SQLVARCHAR sql_name[64];
	SQLVARCHAR sql_lastName[64];
	SQLVARCHAR sql_patronymic[64];
	SQLVARCHAR sql_comment[256];

	SQLBindCol(hstmt, 1, SQL_C_LONG, &sql_id, sizeof(sql_id), NULL);
	SQLBindCol(hstmt, 2, SQL_C_CHAR, &sql_name, sizeof(sql_name), NULL);
	SQLBindCol(hstmt, 3, SQL_C_CHAR, &sql_lastName, sizeof(sql_lastName), NULL);
	SQLBindCol(hstmt, 4, SQL_C_CHAR, &sql_patronymic, sizeof(sql_patronymic), NULL);
	SQLBindCol(hstmt, 5, SQL_C_CHAR, &sql_comment, sizeof(sql_comment), NULL);

	if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
		cerr << "Error with execute";
		return answer;
	}
	
	while(1) {
		if(SQLFetch(hstmt) == SQL_NO_DATA) {
			break;
		}
		PeopleEntity man(sql_id, reinterpret_cast<char*>(sql_name), reinterpret_cast<char*>(sql_lastName), reinterpret_cast<char*>(sql_patronymic), reinterpret_cast<char*>(sql_comment));
		answer.push_back(man);
	}
	
	SQLCloseCursor(hstmt);
	return answer;
}


PeopleEntity::PeopleEntity(int id, const char * firstName, const char * lastName, const char * patronymic, const char * comment)
{
    this->id = id;
    this->firstName = firstName;
    this->lastName = lastName;
    this->patronymic = patronymic;
    this->comment = comment;
}

void PeopleEntity::print() 
{
	cout << "Идентефикатор: " << this->id;
    cout << "\tИмя " << this->firstName;
    cout << "\tФамилия: " << this->lastName;
    cout << "\tОтчество: " << this->patronymic;
    cout << "\tЗаметка: " << this->comment << endl;
}
