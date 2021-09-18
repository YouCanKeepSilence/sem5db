#ifndef PEOPLE_SERVICE
#define PEOPLE_SERVICE

#include <string>
#include <iostream>
#include <vector>
#include "odbcEnv.h"

using namespace std;
class PeopleEntity;

class PeopleService {
public:
    PeopleService(OdbcEnvirioment * odbc = nullptr);
    vector<PeopleEntity> getAll();
    void deleteById(int id);
    void updateById(int id, string name, string lastName, string patronymic, string comment);
    vector<PeopleEntity> find(string info);
    void add(string name, string lastName, string patronymic, string comment);
private:
	OdbcEnvirioment * odbc;
};

class PeopleEntity {
public:
    PeopleEntity(int id = 0, const char * firstName = "", const char * lastName = "", const char * patronymic = "", const char * comment = "");
    void print();

private:
    int id;
    string firstName;
    string lastName;
    string patronymic;
    string comment;
};

#endif
