//
// Created by Silence on 10.02.2018.
//
#include "Dog.h"
Dog::Dog(string name, unsigned short age)
{
    this->name = name;
    this->age = age;
}

void Dog::print()
{
    std::cout << "Name : "<<this->name<<" Age: "<<this->age<<std::endl;
}

ostream& operator <<(ostream &stream, Dog& data)
{
    stream.write(data.name.c_str() , sizeof(char)*MAX_NAME_LENGTH);
    stream.write((char*)&data.age, sizeof(unsigned short));

    return stream;
}

istream& operator >>(istream &stream, Dog& data)
{
    char * buf = new char[MAX_NAME_LENGTH];
    stream.read(buf, sizeof(char)*MAX_NAME_LENGTH);
    data.name = buf;
    delete[] buf;

    buf = new char[sizeof(unsigned short)];
    stream.read(buf, sizeof(unsigned short));
    data.age = *((unsigned short*)buf);
//    data.print();
    return stream;
}

