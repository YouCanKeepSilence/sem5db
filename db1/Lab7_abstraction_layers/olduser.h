#ifndef OLDUSER_H
#define OLDUSER_H
#include <iostream>
#include "middleware.h"
using namespace std;
class OldUser
{
public:
    OldUser();
    void createDog();
private:
    Middleware<Dog>* db;
};

#endif // OLDUSER_H
