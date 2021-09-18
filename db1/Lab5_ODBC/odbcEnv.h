#ifndef ODBC_ENV
#define ODBC_ENV
#include <sql.h>
#include <sqlext.h>
#include <sqltypes.h>

class OdbcEnvirioment 
{
public:
	OdbcEnvirioment();
	~OdbcEnvirioment();
	int connect();
	void disconnect();
	SQLHENV getEnv();
	SQLHDBC getHandler();
private:
	SQLHENV henv;
	SQLHDBC hdbc;
};

#endif
