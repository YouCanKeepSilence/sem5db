#ifndef DOG_H
#define DOG_H
#include <QString>
#include <QDataStream>
#define MAX_NAME_LENGTH 64

class Dog
{
public:
    Dog(QString name = "Bobby", unsigned short age = 5);
    QString getName() const { return this->name; }
    unsigned short getAge() const { return this->age; }
    void setName(QString name) { this->name = name; }
    void setAge(unsigned short age) { this->age = age; }
    friend QDataStream &operator << (QDataStream& stream, Dog &data);
    friend QDataStream &operator >> (QDataStream& stream, Dog &data);
    void print();
private:
    QString name;
    unsigned short age;
};

#endif // DOG_H
