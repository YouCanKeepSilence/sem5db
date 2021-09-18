#include "odbcEnv.h"
#include <iostream>
using namespace std;

OdbcEnvirioment::OdbcEnvirioment()
{
	this->henv = nullptr;
	this->hdbc = nullptr;
}
void OdbcEnvirioment::disconnect() 
{
    SQLDisconnect(hdbc);
    SQLFreeHandle(SQL_HANDLE_DBC, this->hdbc);
    SQLFreeHandle(SQL_HANDLE_ENV, this->henv);
}
OdbcEnvirioment::~OdbcEnvirioment()
{
	this->disconnect();
}
int OdbcEnvirioment::connect()
{
    if (SQLAllocHandle(SQL_HANDLE_ENV, SQL_NULL_HANDLE, &henv) == SQL_ERROR) {
        cerr << "Error with the first allocation handling" << endl;
        return 1;
    }

    SQLSetEnvAttr(henv, SQL_ATTR_ODBC_VERSION, (void*)SQL_OV_ODBC3, SQL_IS_INTEGER);

    if (SQLAllocHandle(SQL_HANDLE_DBC, henv, &hdbc) == SQL_ERROR) {
        cerr << "Error with the second allocation handling" << endl;
        return 1;
    }

    if (SQLConnect(hdbc, (SQLCHAR*)"database1", SQL_NTS, (SQLCHAR*)"silence", SQL_NTS, (SQLCHAR*)"", SQL_NTS) == SQL_ERROR) {
        cerr << "Error with connecting to DB" << endl;
        return 1;
    }
    cout << "Connected to database1"<<endl;
    return 0;
}

SQLHENV OdbcEnvirioment::getEnv()
{
	return this->henv;
}

SQLHDBC OdbcEnvirioment::getHandler()
{
	return this->hdbc;
}