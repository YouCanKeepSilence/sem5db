//
// Created by Silence on 10.02.2018.
//

#ifndef LAB1_DOG_H
#define LAB1_DOG_H

#define MAX_NAME_LENGTH 64

#include <iostream>
using namespace std;

class Dog
{
public:
    Dog(string name = "Bobby", unsigned short age = 5);
    string getName() const { return this->name; }
    unsigned short getAge() const { return this->age; }
    void setName(string name) { this->name = name; }
    void setAge(unsigned short age) { this->age = age; }
    friend ostream &operator << (ostream& stream, Dog &data);
    friend istream &operator >> (istream& stream, Dog &data);
    void print();
private:
    string name;
    unsigned short age;
};


#endif //LAB1_DOG_H
