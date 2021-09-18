#include "contact.h"

ContactService::ContactService(OdbcEnvirioment * odbc)
{
	this->odbc = odbc;
}

int ContactService::addContact(int id_of_man , string info, bool isPhone)
{
	SQLHSTMT hstmt;

	if(!this->odbc)
	{
		cerr << "Не установлено окружение ODBC!";
        return 1;
	}

	if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
		cerr << "Error with AllocHandler";
        return 1;
	}

	SQLCHAR* statement = nullptr;

	if(isPhone)
	{
		statement = (unsigned char *)"INSERT INTO phones(phone, man_id) values(?,?)";
	}
	else 
	{
		statement = (unsigned char *)"INSERT INTO emails(mail, man_id) values(?,?)";	
	}
	if (SQLPrepare(hstmt, (SQLCHAR *)statement, SQL_NTS) != SQL_SUCCESS) {
		cerr << "Error with prepare";
        return 1;
	}	
    SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 50, 0, (char*)info.c_str(), info.length(), NULL);
	SQLBindParameter(hstmt, 2, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id_of_man, 0, NULL);
	if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
		cerr << "Error with execute";
        return 1;
	}
	SQLCloseCursor(hstmt);
    return 0;
}

void ContactService::updateContact(int id, bool isPhone, string newInfo)
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
    SQLCHAR* statement;
    if(isPhone)
    {
        statement = (unsigned char* ) "UPDATE phones SET phone = ? where id = ?";
    }
    else
    {
        statement = (unsigned char* ) "UPDATE emails SET mail = ? where id = ?";
    }

    if (SQLPrepare(hstmt, statement, SQL_NTS) != SQL_SUCCESS) {
        cerr << "Error with prepare";
        return;
    }
    SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_LONGVARCHAR, 64, 0, (char*)newInfo.c_str(), newInfo.length(), NULL);
    SQLBindParameter(hstmt, 2, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id, 0, NULL);
    if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
        cerr << "Error with execute";
        return;
    }
    SQLCloseCursor(hstmt);
    return;
}

void ContactService::removeContact(int id, bool isPhone)
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
    SQLCHAR* statement;
    if(isPhone)
    {
        statement = (unsigned char*)"delete from phones where id = ?";
    }
    else
    {
        statement = (unsigned char*)"delete from emails where id = ?";
    }
    if (SQLPrepare(hstmt, statement, SQL_NTS) != SQL_SUCCESS) {
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

void ContactService::removeContactByUserId(int id_of_man)
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

    if (SQLPrepare(hstmt, (SQLCHAR *)"delete from phones where man_id = ?; delete from emails where man_id = ?", SQL_NTS) != SQL_SUCCESS) {
        cerr << "Error with prepare";
        return;
    }

    SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id_of_man, 0, NULL);
    SQLBindParameter(hstmt, 2, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id_of_man, 0, NULL);

    if (SQLExecute(hstmt) !=  SQL_SUCCESS) {
        cerr << "Error with execute";
        return;
    }

    SQLCloseCursor(hstmt);
    return;
}

std::vector<Contact> ContactService::getContactsByUserId(int id_of_man, bool isPhone)
{
	SQLHSTMT hstmt;

	if(!this->odbc)
	{
		throw ("Error with ODBC env is null");
	}

	if (SQLAllocHandle(SQL_HANDLE_STMT, odbc->getHandler(), &hstmt) == SQL_ERROR) {
		throw ("Error with AllocHandler");
	}

	SQLCHAR* statement;
	if (isPhone)
	{
		statement = (unsigned char *)"SELECT id, phone, man_id FROM phones WHERE man_id = ?";	
	} 
	else 
	{
		statement = (unsigned char *)"SELECT id, mail, man_id FROM emails WHERE man_id = ?";	
	}

	if (SQLPrepare(hstmt, (SQLCHAR*)statement, SQL_NTS) != SQL_SUCCESS) 
	{
		throw ("Error with prepare");
	}	
	if(SQLBindParameter(hstmt, 1, SQL_PARAM_INPUT, SQL_C_LONG, SQL_INTEGER, 0, 0, &id_of_man, 0, NULL) != SQL_SUCCESS)
	{
		cerr << "error with binding"<< endl;
	}

	SQLINTEGER sql_id;
	SQLVARCHAR sql_info[64];
	SQLINTEGER sql_man_id;

	SQLBindCol(hstmt, 1, SQL_C_LONG, &sql_id, sizeof(sql_id), NULL);
	SQLBindCol(hstmt, 2, SQL_C_CHAR, &sql_info, sizeof(sql_info), NULL);
	SQLBindCol(hstmt, 3, SQL_C_LONG, &sql_man_id, sizeof(sql_man_id), NULL);

	if (SQLExecute(hstmt) !=  SQL_SUCCESS)
	{
		cerr << statement;
		throw ("Error with execute statement");
	}
	std::vector<Contact> answer;
	while(1)
	{
		if(SQLFetch(hstmt) == SQL_NO_DATA)
		{
			break;
		}
		answer.push_back(Contact(sql_id, (char*)sql_info, sql_man_id));
	}
	SQLCloseCursor(hstmt);
	return answer;
}

std::vector<Contact> ContactService::getAllContactsByUserId(int id_of_man)
{
	std::vector<Contact> answer;
	try
	{
		answer = this->getContactsByUserId(id_of_man, true);
		std::vector<Contact> buf = this->getContactsByUserId(id_of_man, false);
		answer.insert(answer.end(), buf.begin(), buf.end());
		return answer;
	} catch (const char * err) {
		throw err;
	}
}
Contact::Contact(int id, const char * info, int man_id)
{
	this->id = id;
	this->info = info;
	this->man_id = man_id;
}

void Contact::print()
{
	cout << "Идентефикатор: " << this->id <<" Инфо: " << this->info << " Пользователь: " << this->man_id << endl;
}
