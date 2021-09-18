#ifndef CONTACT_SERVICE
#define CONTACT_SERVICE
#include "odbcEnv.h"
#include <string>
#include <iostream>
#include <vector>
using namespace std;


class Contact;
class ContactService 
{
public:
	ContactService(OdbcEnvirioment * odbc = nullptr);
	int addContact(int id_of_man , string info, bool isPhone);
    void updateContact(int id, bool isPhone, string newInfo);
    void removeContact(int id, bool isPhone);
    void removeContactByUserId(int id_of_man);
	vector<Contact> getAllContactsByUserId(int id_of_man);
	vector<Contact> getContactsByUserId(int id_of_man, bool isPhone);

private:
	OdbcEnvirioment * odbc;
};

class Contact 
{
public:
	Contact(int id = 0, const char * info = "" , int man_id = 0);
	void print();
private:
	int id;
	string info;
	int man_id;
};

#endif
