#include <iostream>
#include "cat.h"
#include <QDebug>
#include "middleware.h"
#include "dog.h"
#include "dogcomparator.h"
#include "olduser.h"
#include "script_api.h"
using namespace std;

void initDogDatabase(Middleware<Dog> &mw)
{
    mw.DropTheBase();
    Dog a("Rexxar", 3);
    Dog b("Rex", 8);
    Dog c("Zigmund", 5);
    Dog d("Anatoliy" , 3);
    Dog f("Comrad" , 13);
    Dog g("Oyler" , 2);
    Dog h("Git" , 7);
    Dog y("Bob" , 8);
    Dog z("Illidan" , 3);

    mw.addEntity(a);
    mw.addEntity(b);
    mw.addEntity(c);
    mw.addEntity(d);
    mw.addEntity(f);
    mw.addEntity(g);
    mw.addEntity(h);
    mw.addEntity(y);
    mw.addEntity(z);
}

int main(int argc, char *argv[])
{
    Middleware<Dog> mw;
//    initDogDatabase(mw);
//    OldUser ou;
//    ou.createDog();
//    Dog search("Hello");
//    mw.setSearchType(SEARCH_TYPE::Name);
//    QVector<Dog> res = mw.findEquals(search);
//    foreach (Dog d, res) {
//        d.print();
//    }
    getAllDogs();
    return 0;
}


