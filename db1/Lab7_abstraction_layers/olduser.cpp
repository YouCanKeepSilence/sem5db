#include "olduser.h"
#include "dog.h"
#include <QTextStream>
OldUser::OldUser()
{
    db = new Middleware<Dog>();
}

void OldUser::createDog()
{
    QString name;

    unsigned short age;
    QTextStream input(stdin);

    cout << "Hello, enter name of your dog : ";
    input.skipWhiteSpace();
    name = input.readLine(MAX_NAME_LENGTH);

    cout << endl << "Enter age of your dog : ";
    input >> age;
    cout << age;

    Dog dog(name, age);
    db->addEntity(dog);
    cout << "Successful added "<< endl;
}
