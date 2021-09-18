#include "dog.h"
#include <iostream>
#include <QDebug>
Dog::Dog(QString name, unsigned short age)
{
    this->name = name;
    this->age = age;
}

void Dog::print()
{
    std::cout << "Name : "<<this->name.toStdString()<<" Age: "<<this->age<<std::endl;
}

QDataStream& operator <<(QDataStream &stream, Dog& data)
{
    stream.writeRawData(data.name.toStdString().data() , sizeof(char)*MAX_NAME_LENGTH);
    stream.writeRawData((char*)&data.age, sizeof(unsigned short));

    return stream;
}

QDataStream& operator >>(QDataStream &stream, Dog& data)
{
    char * buf = new char[MAX_NAME_LENGTH];
    stream.readRawData(buf, sizeof(char)*MAX_NAME_LENGTH);
    data.name = QString(buf).trimmed();
    delete[] buf;

    buf = new char[sizeof(unsigned short)];
    stream.readRawData(buf, sizeof(unsigned short));
    data.age = *((unsigned short*)buf);
//    data.print();
    return stream;
}

